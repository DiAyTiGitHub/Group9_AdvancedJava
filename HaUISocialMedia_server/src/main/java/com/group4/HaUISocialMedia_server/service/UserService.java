package com.group4.HaUISocialMedia_server.service;

import com.group4.HaUISocialMedia_server.dto.SearchObject;
import com.group4.HaUISocialMedia_server.dto.UserDto;
import com.group4.HaUISocialMedia_server.entity.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto getById(UUID userId);

    public UserDto getByUserName(String name);

    public void deleteById(UUID id);

    public UserDto updateUser(UserDto dto);

    public List<UserDto> searchByUsername(SearchObject searchObject);

    public List<UserDto> pagingUser(SearchObject searchObject);

    public User getCurrentLoginUserEntity();

    public UserDto getCurrentLoginUser();

    public User getUserEntityById(UUID userId);

    public UserDto isDisable(UUID userId);

    public UserDto updateStatus(UUID userId);

    public List<UserDto> pagingByKeyword(SearchObject searchObject);





    //FUNCTION NEWLY WRITTEN FOR ADMIN SWING
    public List<UserDto> searchByUsernameNew(SearchObject searchObject);
    
    public UserDto getByIdNew(UUID userId);
    
    public UserDto createUser(UserDto userDto);

    public UserDto updateUserV2(UserDto userDto);

    public UserDto deleteUserByVoided(UserDto userDto);

    public List<UserDto> getUsersNotVoided();

    public UserDto updateAccountStatus(UserDto userDto);




}
