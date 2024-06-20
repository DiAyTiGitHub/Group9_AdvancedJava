package com.group4.HaUISocialMedia_server.swing;

import com.group4.HaUISocialMedia_server.dto.UserDto;
import com.group4.HaUISocialMedia_server.service.UserService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class UserEditDialog extends JDialog {

    private UserService userService;
    private UserDto userDto;
    private DefaultTableModel tableModel;
    private int rowIndex;

    private JTextField txtCode;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtAddress;
    private JDateChooser dateChooser;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JComboBox<String> cbStatus;
    private JLabel lblAvatar;

    public UserEditDialog(UserService userService, UserDto userDto, DefaultTableModel tableModel, int rowIndex) {
        this.userService = userService;
        this.userDto = userDto;
        this.tableModel = tableModel;
        this.rowIndex = rowIndex;

        setTitle("Chỉnh sửa thông tin tài khoản");
        setSize(700, 575);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Panel bên trái (1/3 chiều rộng)
        JPanel leftPanel = new JPanel(null); 
        initLeftPanel(leftPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        contentPanel.add(leftPanel, gbc);

        // Panel bên phải (2/3 chiều rộng)
        JPanel rightPanel = new JPanel(null); 
        initRightPanel(rightPanel);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        contentPanel.add(rightPanel, gbc);

        add(contentPanel);
    }

    private void initLeftPanel(JPanel panel) {
        lblAvatar = new JLabel("Avatar", SwingConstants.CENTER);
        lblAvatar.setBounds(30, 10, 150, 150);
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        if (userDto.getAvatar()!= null && !userDto.getAvatar().isEmpty()) {
            try {
                ImageIcon avatarIcon = new ImageIcon(new URL(userDto.getAvatar()));
                Image image = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(image));
                lblAvatar.setText(""); 
            } catch (Exception e) {
                e.printStackTrace();
                lblAvatar.setText("Lỗi load ảnh");
            }
        }
        panel.add(lblAvatar);

        JPanel codePanel = new JPanel();
        codePanel.setBounds(10, 170, 200, 35);
        codePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel codeLabel = new JLabel("Code:");
        txtCode = new JTextField(userDto.getCode(), 10);
        txtCode.setPreferredSize(new Dimension(150, 30));
        codePanel.add(codeLabel);
        codePanel.add(txtCode);
        panel.add(codePanel);

        JButton btnUpdate = new JButton("Cập nhật");
        btnUpdate.setBounds(10, 250, 190, 35);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserDetails();
            }
        });
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(10, 350, 190, 35);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        panel.add(btnDelete);

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBounds(10, 450, 190, 35);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel);
    }

    private void initRightPanel(JPanel panel) {
        JLabel titleLabel = new JLabel("THÔNG TIN TÀI KHOẢN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBounds(30, 10, 300, 30);
        panel.add(titleLabel);

        int y = 50; // Vị trí y bắt đầu của các component

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, y, 100, 30);
        panel.add(usernameLabel);
        txtUsername = new JTextField(userDto.getUsername());
        txtUsername.setBounds(150, y, 250, 30);
        panel.add(txtUsername);
        y += 50;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, y, 100, 30);
        panel.add(emailLabel);
        txtEmail = new JTextField(userDto.getEmail());
        txtEmail.setBounds(150, y, 250, 30);
        panel.add(txtEmail);
        y += 50;

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, y, 100, 30);
        panel.add(firstNameLabel);
        txtFirstName = new JTextField(userDto.getFirstName());
        txtFirstName.setBounds(150, y, 250, 30);
        panel.add(txtFirstName);
        y += 50;

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(30, y, 100, 30);
        panel.add(lastNameLabel);
        txtLastName = new JTextField(userDto.getLastName());
        txtLastName.setBounds(150, y, 250, 30);
        panel.add(txtLastName);
        y += 50;

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, y, 100, 30);
        panel.add(addressLabel);
        txtAddress = new JTextField(userDto.getAddress());
        txtAddress.setBounds(150, y, 250, 30);
        panel.add(txtAddress);
        y += 50;

        JLabel birthDateLabel = new JLabel("Birth Date:");
        birthDateLabel.setBounds(30, y, 100, 30);
        panel.add(birthDateLabel);
        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, y, 250, 30);
        if (userDto.getBirthDate() != null) {
            dateChooser.setDate(userDto.getBirthDate());
        }
        panel.add(dateChooser);
        y += 50;

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, y, 100, 30);
        panel.add(genderLabel);
        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbGender.setBounds(150, y, 250, 30);
        cbGender.setSelectedItem(userDto.isGender() ? "Male" : "Female");
        panel.add(cbGender);
        y += 50;

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, y, 100, 30);
        panel.add(phoneLabel);
        txtPhone = new JTextField(userDto.getPhoneNumber());
        txtPhone.setBounds(150, y, 250, 30);
        panel.add(txtPhone);
        y += 50;

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(30, y, 100, 30);
        panel.add(statusLabel);
        cbStatus = new JComboBox<>(new String[]{"Active", "Disabled"});
        cbStatus.setBounds(150, y, 250, 30);
        cbStatus.setSelectedItem(userDto.getDisable() ? "Disabled" : "Active");
        panel.add(cbStatus);
    }

    private void updateUserDetails() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();

        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các thông tin bắt buộc (Username, Email)", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            userDto.setCode(txtCode.getText());
            userDto.setUsername(username);
            userDto.setEmail(email);
            userDto.setFirstName(txtFirstName.getText());
            userDto.setLastName(txtLastName.getText());
            userDto.setAddress(txtAddress.getText());
            userDto.setBirthDate(dateChooser.getDate());
            userDto.setGender(cbGender.getSelectedItem().equals("Male"));
            userDto.setPhoneNumber(txtPhone.getText());
            userDto.setDisable(cbStatus.getSelectedItem().equals("Disabled"));

            UserDto updatedUser = userService.updateUserV2(userDto);

            if (updatedUser != null) {
                tableModel.setValueAt(updatedUser.getCode(), rowIndex, 1);
                tableModel.setValueAt(updatedUser.getUsername(), rowIndex, 2);
                tableModel.setValueAt(updatedUser.getEmail(), rowIndex, 3);
                tableModel.setValueAt(updatedUser.getFirstName(), rowIndex, 4);
                tableModel.setValueAt(updatedUser.getLastName(), rowIndex, 5);
                tableModel.setValueAt(updatedUser.getAddress(), rowIndex, 6);
                tableModel.setValueAt(updatedUser.getBirthDate().toString(), rowIndex, 7);
                tableModel.setValueAt(updatedUser.isGender() ? "Male" : "Female", rowIndex, 8);
                tableModel.setValueAt(updatedUser.getPhoneNumber(), rowIndex, 9);
                tableModel.setValueAt(updatedUser.getDisable() ? "Disabled" : "Active", rowIndex, 10);

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật thông tin người dùng", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin người dùng", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa người dùng này?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                UserDto voidedUser = userService.deleteUserByVoided(userDto);
                if (voidedUser != null) {
                    tableModel.removeRow(rowIndex);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa thông tin người dùng", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa thông tin người dùng", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

//    public static void main(String[] args) {
//        // Sample usage to test the dialog
//        UserService userService = new UserService();
//        UserDto userDto = new UserDto();
//        DefaultTableModel tableModel = new DefaultTableModel();
//        UserEditDialog dialog = new UserEditDialog(userService, userDto, tableModel, 0);
//        dialog.setVisible(true);
//    }
}
