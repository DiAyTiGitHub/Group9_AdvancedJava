package com.group4.HaUISocialMedia_server.service.impl;

import com.group4.HaUISocialMedia_server.dto.RelationshipDto;
import com.group4.HaUISocialMedia_server.dto.SearchObject;
import com.group4.HaUISocialMedia_server.dto.UserDto;
import com.group4.HaUISocialMedia_server.entity.Relationship;
import com.group4.HaUISocialMedia_server.entity.User;
import com.group4.HaUISocialMedia_server.repository.RelationshipRepository;
import com.group4.HaUISocialMedia_server.repository.UserRepository;
import com.group4.HaUISocialMedia_server.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipServiceImpl relationshipService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public List<UserDto> getAllUsers() {
        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        List<UserDto> res = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserDto person = new UserDto(user);

            //set mutual friends of current user and viewing user
            person.setMutualFriends(relationshipService.getMutualFriends(person.getId(), currentUser.getId()));

            res.add(person);
        }

        return res;
    }

    @Override
    public UserDto getById(UUID userId) {
        if (userId == null) return null;
        User currentUser = userService.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        UserDto res = new UserDto(user);

        if (!currentUser.getId().equals(userId)) {
            List<Relationship> relationships = relationshipRepository.getRelationshipBetweenCurrentUserAndViewingUser(currentUser.getId(), userId);

            Relationship relationship = null;
            if (relationships != null && relationships.size() > 0) {
                relationship = relationships.get(0);

                if (relationships.size() >= 2)
                    System.out.println("There's duplicate record which is must be have 1 in database, error but handled");
            }

            if (relationship != null) {
                RelationshipDto relationshipDto = new RelationshipDto(relationship);
                res.setRelationshipDto(relationshipDto);
            }
        }

        //set mutual friends of current user and viewing user
        res.setMutualFriends(relationshipService.getMutualFriends(res.getId(), currentUser.getId()));

        return res;
    }

    @Override
    public UserDto getByUserName(String name) {
        if (name == null) return null;
        UserDto res = new UserDto(userRepository.findByUsername(name));
        if (res == null) return null;

        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        //set mutual friends of current user and viewing user
        res.setMutualFriends(relationshipService.getMutualFriends(currentUser.getId(), res.getId()));

        return res;
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


    @Override
    @Transactional
    public UserDto updateUser(UserDto dto) {
        User entity = userService.getCurrentLoginUserEntity();
        if (entity == null)
            return null;

        entity.setCode(dto.getCode());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setAddress(dto.getAddress());
        entity.setGender(dto.isGender());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAvatar(dto.getAvatar());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBackground(dto.getBackground());

        userRepository.saveAndFlush(entity);

        return dto;
    }

    @Override
    public List<UserDto> searchByUsername(SearchObject searchObject) {
        if (searchObject == null) return null;

        User currentUser = userService.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        List<UserDto> res = new ArrayList<>();
        List<User> validUsers = userRepository.getByUserName(searchObject.getKeyWord(), searchObject.getPageSize(), searchObject.getPageIndex() - 1);
        for (User user : validUsers) {
            UserDto person = new UserDto(user);
            //set mutual friends of current user and viewing user
            person.setMutualFriends(relationshipService.getMutualFriends(person.getId(), currentUser.getId()));
        }
        return res;
    }

    @Override
    public List<UserDto> pagingUser(SearchObject searchObject) {
        return pagingByKeyword(searchObject);
    }

    @Override
    public User getCurrentLoginUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String currentUserName = auth.getName();
        if (currentUserName == null) return null;
        User currentUser = userRepository.findByUsername(currentUserName);

        return currentUser;
    }

    @Override
    public User getUserEntityById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }


    @Override
    public UserDto getCurrentLoginUser() {
        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        return new UserDto(currentUser);
    }

    @Override
    public UserDto isDisable(UUID userId) {
        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;
        if (!currentUser.getRole().equals("ADMIN")) return null;
        User entity = userRepository.findById(userId).orElse(null);
        entity.setDisable(true);
        userRepository.save(entity);
        return new UserDto(entity);
    }

    @Override
    public UserDto updateStatus(UUID userId) {
        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;
        if (!currentUser.getRole().equals("ADMIN")) return null;
        User entity = userRepository.findById(userId).orElse(null);
        entity.setDisable(false);
        userRepository.save(entity);
        return new UserDto(entity);
    }

    @Override
    public List<UserDto> pagingByKeyword(SearchObject searchObject) {
        if (searchObject == null) return null;
        String keyword = insertPercent(searchObject.getKeyWord());

        User currentUser = userService.getCurrentLoginUserEntity();

        if (currentUser == null || searchObject == null) return null;

        List<User> validUsers = userRepository.pagingUsers(keyword,
                PageRequest.of(searchObject.getPageIndex() - 1, searchObject.getPageSize()));

        List<UserDto> res = new ArrayList<>();
        for (User user : validUsers) {
            UserDto person = new UserDto(user);

            //set relationship between current user and viewing user
            if (!currentUser.getId().equals(person.getId())) {
                List<Relationship> relationships = relationshipRepository.getRelationshipBetweenCurrentUserAndViewingUser(currentUser.getId(), person.getId());

                Relationship relationship = null;
                if (relationships != null && relationships.size() > 0) {
                    relationship = relationships.get(0);

                    if (relationships.size() >= 2)
                        System.out.println("There's duplicate record which is must be have 1 in database, error but handled");
                }


                if (relationship != null) {
                    RelationshipDto relationshipDto = new RelationshipDto(relationship);
                    person.setRelationshipDto(relationshipDto);
                }
            }

            //set mutual friends of current user and viewing user
            person.setMutualFriends(relationshipService.getMutualFriends(person.getId(), currentUser.getId()));

            res.add(person);
        }

        return res;
    }

    public String insertPercent(String word) {
        if (word == null || word.length() == 0) return "";
        StringBuilder result = new StringBuilder();

        result.append('%');

        for (int i = 0; i < word.length(); i++) {
            result.append(word.charAt(i));
            result.append('%');
        }

        return result.toString();
    }


    //FUNCTION NEWLY WRITTEN FOR ADMIN SWING
    
    // new func
    @Override
    public UserDto getByIdNew(UUID userId) {
        if (userId == null) return null;

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        return new UserDto(user);
    }

    
    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto == null) return null;

        User user = new User();

        if (userDto.getUsername() != null)
            user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getCode() != null)
            user.setCode(userDto.getCode());
        if (userDto.getFirstName() != null)
            user.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null)
            user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if (userDto.getAddress() != null)
            user.setAddress(userDto.getAddress());

        user.setGender(userDto.isGender());

        if (userDto.getBirthDate() != null)
            user.setBirthDate(userDto.getBirthDate());

        if (userDto.getRole() != null)
            user.setRole(userDto.getRole());
        if (userDto.getPhoneNumber() != null)
            user.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getDisable() != null)
            user.setDisable(userDto.getDisable());
        

        // Save the user entity to the repository
        user = userRepository.save(user);

        // Convert the saved user entity back to UserDto
        return new UserDto(user);
    }

    @Override
    @Modifying
    @Transactional
    public UserDto updateUserV2(UserDto userDto) {
        if (userDto == null || userDto.getId() == null) {
            return null;
        }

        // Find the existing user entity by ID
        User existingUser = userRepository.findById(userDto.getId()).orElse(null);
        if (existingUser == null) {
            return null;
        }

        // Update the fields of the existing user with the values from the DTO
        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }
        if (userDto.getCode() != null) {
            existingUser.setCode(userDto.getCode());
        }
        if (userDto.getFirstName() != null) {
            existingUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            existingUser.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        if (userDto.getAddress() != null) {
            existingUser.setAddress(userDto.getAddress());
        }
        existingUser.setGender(userDto.isGender());
        if (userDto.getBirthDate() != null) {
            existingUser.setBirthDate(userDto.getBirthDate());
        }

        if (userDto.getRole() != null) {
            existingUser.setRole(userDto.getRole());
        }
        if (userDto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getDisable() != null) {
            existingUser.setDisable(userDto.getDisable());
        }

        // Save the updated user entity
        User updatedUser = userRepository.save(existingUser);

        // Convert the updated user entity back to UserDto and return
        return new UserDto(updatedUser);
    }

    @Override
    @Modifying
    @Transactional
    public UserDto deleteUserByVoided(UserDto userDto) {
        if (userDto == null || userDto.getId() == null) {
            return null;
        }

        // Find the existing user entity by ID
        User existingUser = userRepository.findById(userDto.getId()).orElse(null);
        if (existingUser == null) {
            return null;
        }

        existingUser.setVoided(true);

        // Save the updated user entity
        User updatedUser = userRepository.save(existingUser);

        // Convert the updated user entity back to UserDto and return
        return new UserDto(updatedUser);
    }

    @Override
    public List<UserDto> getUsersNotVoided() {
        User currentUser = this.getCurrentLoginUserEntity();
        if (currentUser == null) return null;

        List<UserDto> res = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (!user.isVoided()) {
                UserDto person = new UserDto(user);

                res.add(person);
            }
        }

        return res;
    }

    @Override
    public UserDto updateAccountStatus(UserDto userDto) {
        return null;
    }
}
