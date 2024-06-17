package com.group4.HaUISocialMedia_server.swing;

import com.group4.HaUISocialMedia_server.dto.UserDto;
import com.group4.HaUISocialMedia_server.service.UserService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class UserCreateDialog extends JDialog {
    private UserService userService;
    private DefaultTableModel tableModel;

    private JTextField txtCode;
    private JTextField txtUsername;
    private JPasswordField txtPassword; 
    private JTextField txtEmail;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtAddress;
    private JDateChooser dateChooser;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbRole; 
    private JRadioButton maleRadio, femaleRadio; 
    private JCheckBox disableCheckBox; 

    public UserCreateDialog(UserService userService, DefaultTableModel tableModel) {
        this.userService = userService;
        this.tableModel = tableModel;
        initUI();
    }

    private void initUI() {
        setTitle("Thêm người dùng");
        setSize(800, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JLabel lblTitle = new JLabel("Thêm thông tin người dùng");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTitle.setBounds(275, 30, 300, 50);
        add(lblTitle);
        
        JLabel lblCode = new JLabel("Code:");
        lblCode.setBounds(50, 100, 100, 30);
        add(lblCode);

        txtCode = new JTextField();
        txtCode.setBounds(150, 100, 200, 30);
        add(txtCode);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 150, 100, 30);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 150, 200, 30);
        add(txtPassword);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 200, 100, 30);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 200, 200, 30);
        add(txtEmail);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(50, 250, 100, 30);
        add(lblFirstName);

        txtFirstName = new JTextField();
        txtFirstName.setBounds(150, 250, 200, 30);
        add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(50, 300, 100, 30);
        add(lblLastName);

        txtLastName = new JTextField();
        txtLastName.setBounds(150, 300, 200, 30);
        add(txtLastName);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(50, 350, 100, 30);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(150, 350, 200, 30);
        add(txtAddress);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(400, 100, 100, 30);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(500, 100, 200, 30);
        add(txtUsername);

        JLabel lblBirthDate = new JLabel("Birth Date:");
        lblBirthDate.setBounds(400, 150, 100, 30);
        add(lblBirthDate);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(500, 150, 200, 30);
        add(dateChooser);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(400, 200, 100, 30);
        add(lblGender);

        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(500, 200, 80, 30);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(600, 200, 80, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        add(maleRadio);
        add(femaleRadio);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(400, 250, 100, 30);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(500, 250, 200, 30);
        add(txtPhone);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(400, 300, 100, 30);
        add(lblStatus);

        cbStatus = new JComboBox<>(new String[]{"Active", "Disabled"});
        cbStatus.setBounds(500, 300, 200, 30);
        add(cbStatus);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(400, 350, 100, 30);
        add(lblRole);

        cbRole = new JComboBox<>(new String[]{"ADMIN", "USER"}); 
        cbRole.setBounds(500, 350, 200, 30);
        add(cbRole);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(275, 450, 100, 30);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
        add(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(425, 450, 100, 30);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnCancel);
    }

    private void saveUser() {
        try {
            UserDto userDto = new UserDto();
            userDto.setCode(txtCode.getText());
            userDto.setUsername(txtUsername.getText());
            userDto.setPassword(new String(txtPassword.getPassword()));
            userDto.setEmail(txtEmail.getText());
            userDto.setFirstName(txtFirstName.getText());
            userDto.setLastName(txtLastName.getText());
            userDto.setAddress(txtAddress.getText());
            userDto.setBirthDate(dateChooser.getDate());
            userDto.setGender(maleRadio.isSelected());
            userDto.setPhoneNumber(txtPhone.getText());
            userDto.setDisable(cbStatus.getSelectedItem().equals("Disabled"));
            userDto.setRole((String) cbRole.getSelectedItem());

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
                        createdUser.getDisable() ? "Disabled" : "Active"
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
