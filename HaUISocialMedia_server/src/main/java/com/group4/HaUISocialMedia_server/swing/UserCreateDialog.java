package com.group4.HaUISocialMedia_server.swing;

import com.group4.HaUISocialMedia_server.dto.UserDto;
import com.group4.HaUISocialMedia_server.service.AuthService;
import com.group4.HaUISocialMedia_server.service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class UserCreateDialog extends JDialog {
    private JTextField usernameField, codeField, firstNameField, lastNameField, emailField, addressField, birthDateField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JRadioButton maleRadio, femaleRadio;
    private JCheckBox disableCheckBox;
    private JButton saveButton, cancelButton;
    private UserService userService;
    private DefaultTableModel tableModel;

    public UserCreateDialog(UserService userService, DefaultTableModel tableModel) {
        this.userService = userService;
        this.tableModel = tableModel;
        initUI();
    }

    private void initUI() {
        setTitle("Add New User");
        setLayout(new GridLayout(13, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Code:"));
        codeField = new JTextField();
        add(codeField);

        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Birth Date (yyyy-MM-dd):"));
        birthDateField = new JTextField();
        add(birthDateField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Gender:"));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        add(maleRadio);
        add(femaleRadio);

        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"ADMIN", "USER"}); // Example roles
        add(roleComboBox);

        add(new JLabel("Disabled:"));
        disableCheckBox = new JCheckBox();
        add(disableCheckBox);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
        add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        setSize(400, 400);
        setLocationRelativeTo(null);
    }

    private void saveUser() {
        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(usernameField.getText());
            userDto.setPassword(new String(passwordField.getPassword())); 
            userDto.setCode(codeField.getText());
            userDto.setFirstName(firstNameField.getText());
            userDto.setLastName(lastNameField.getText());
            userDto.setEmail(emailField.getText());
            userDto.setAddress(addressField.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            userDto.setBirthDate(dateFormat.parse(birthDateField.getText()));
            userDto.setPhoneNumber(phoneField.getText());
            userDto.setGender(maleRadio.isSelected());
            userDto.setRole((String) roleComboBox.getSelectedItem()); // Role field
            userDto.setDisable(disableCheckBox.isSelected());

            UserDto createdUser = userService.createUser(userDto);
            if (createdUser != null) {
                String[] rowData = {
                        createdUser.getId().toString(),
                        createdUser.getCode(),
                        createdUser.getUsername(),
                        createdUser.getEmail(),
                        createdUser.getFirstName(),
                        createdUser.getLastName(),
                        createdUser.getAddress(),
                        createdUser.getBirthDate().toString(),
                        createdUser.isGender() ? "Male" : "Female",
                        createdUser.getPhoneNumber(),
                        createdUser.getDisable() ? "Disabled" : "Active",
                        createdUser.getRole() 
                };
                tableModel.addRow(rowData);
                JOptionPane.showMessageDialog(this, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
