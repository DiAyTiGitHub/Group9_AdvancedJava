package com.group4.HaUISocialMedia_server.swing;

import com.group4.HaUISocialMedia_server.dto.UserDto;
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
        setTitle("Thêm người dùng");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Thông tin người dùng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addLabelAndField(mainPanel, gbc, "Username:", usernameField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Password:", passwordField = new JPasswordField());
        addLabelAndField(mainPanel, gbc, "Code:", codeField = new JTextField());
        addLabelAndField(mainPanel, gbc, "First Name:", firstNameField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Last Name:", lastNameField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Email:", emailField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Address:", addressField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Birth Date (yyyy-MM-dd):", birthDateField = new JTextField());
        addLabelAndField(mainPanel, gbc, "Phone:", phoneField = new JTextField());

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Gender:"), gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        gbc.gridx = 1;
        mainPanel.add(genderPanel, gbc);

        addLabelAndField(mainPanel, gbc, "Role:", roleComboBox = new JComboBox<>(new String[]{"ADMIN", "USER"}));
        addLabelAndField(mainPanel, gbc, "Disabled:", disableCheckBox = new JCheckBox());

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 600);
        setLocationRelativeTo(null);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
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
            userDto.setRole((String) roleComboBox.getSelectedItem()); 
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
                JOptionPane.showMessageDialog(this, "Tạo người dùng thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi tạo người dùng.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
