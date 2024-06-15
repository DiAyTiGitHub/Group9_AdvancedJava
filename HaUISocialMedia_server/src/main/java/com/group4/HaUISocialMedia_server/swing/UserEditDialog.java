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
        setSize(700, 575); // Increase size to accommodate larger avatar
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.3);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        initLeftPanel(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        initRightPanel(rightPanel);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane);
    }

    private void initLeftPanel(JPanel panel) {
        // Avatar panel (assuming it spans the entire width as before)
        JPanel avatarPanel = new JPanel(new BorderLayout());
        lblAvatar = new JLabel("Avatar", SwingConstants.CENTER);  // Placeholder for avatar
        lblAvatar.setPreferredSize(new Dimension(150, 150)); // Increase size for avatar
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        avatarPanel.add(lblAvatar, BorderLayout.CENTER);
        panel.add(avatarPanel);

        // Code label and text field on the same line
        JPanel codePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codePanel.add(new JLabel("Code:"));
        txtCode = new JTextField(userDto.getCode(), 15); // Set column size to 10 for single line
        txtCode.setPreferredSize(new Dimension(150, 35)); // Adjust size as needed
        codePanel.add(txtCode);
        panel.add(codePanel);

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(separator);

        panel.add(Box.createVerticalStrut(35)); // Add some vertical spacing

        // Update button on its own line
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserDetails();
            }
        });
        panel.add(btnUpdate);
        panel.add(Box.createVerticalStrut(35)); // Fixed spacing between buttons

        // Delete button on its own line
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        panel.add(btnDelete);
        panel.add(Box.createVerticalStrut(35)); // Fixed spacing between buttons

        // Cancel button on its own line
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel);
        panel.add(Box.createVerticalStrut(35)); // Fixed spacing between buttons
    }

    private void initRightPanel(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Default insets
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // THÔNG TIN TÀI KHOẢN label
        JLabel titleLabel = new JLabel("THÔNG TIN TÀI KHOẢN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_START; // Align title to the top
        gbc.insets = new Insets(10, 10, 20, 10); // Top margin of 10px, bottom margin of 20px
        panel.add(titleLabel, gbc);

        // Username
        addLabelAndTextField(panel, "Username:", txtUsername = new JTextField(userDto.getUsername()), gbc, 1);

        // Email
        addLabelAndTextField(panel, "Email:", txtEmail = new JTextField(userDto.getEmail()), gbc, 2);

        // First Name
        addLabelAndTextField(panel, "First Name:", txtFirstName = new JTextField(userDto.getFirstName()), gbc, 3);

        // Last Name
        addLabelAndTextField(panel, "Last Name:", txtLastName = new JTextField(userDto.getLastName()), gbc, 4);

        // Address
        addLabelAndTextField(panel, "Address:", txtAddress = new JTextField(userDto.getAddress()), gbc, 5);

        // Birth Date (using JDateChooser)
        addLabelAndDateChooser(panel, "Birth Date:", dateChooser = new JDateChooser(), gbc, 6);
        if (userDto.getBirthDate() != null) {
            dateChooser.setDate(userDto.getBirthDate());
        }

        // Gender (using JComboBox)
        addLabelAndComboBox(panel, "Gender:", cbGender = new JComboBox<>(new String[]{"Male", "Female"}), gbc, 7);
        cbGender.setSelectedItem(userDto.isGender() ? "Male" : "Female");

        // Phone
        addLabelAndTextField(panel, "Phone:", txtPhone = new JTextField(userDto.getPhoneNumber()), gbc, 8);

        // Status (using JComboBox)
        addLabelAndComboBox(panel, "Status:", cbStatus = new JComboBox<>(new String[]{"Active", "Disabled"}), gbc, 9);
        cbStatus.setSelectedItem(userDto.getDisable() ? "Disabled" : "Active");

        // Reset insets for the last component
        gbc.insets = new Insets(10, 10, 10, 10); // Default insets

        // Fill remaining space
        gbc.weighty = 1.0;
        panel.add(new JPanel(), gbc);
    }

    private void addLabelAndTextField(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(textField, gbc);
    }

    private void addLabelAndDateChooser(JPanel panel, String labelText, JDateChooser dateChooser, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(dateChooser, gbc);
    }

    private void addLabelAndComboBox(JPanel panel, String labelText, JComboBox<String> comboBox, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(comboBox, gbc);
    }

    private void updateUserDetails() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String firstName = txtFirstName.getText().trim();

        // Kiểm tra các trường thông tin bắt buộc
        if (username.isEmpty() || email.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các thông tin bắt buộc (Username, Email, First Name)", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            userDto.setCode(txtCode.getText());
            userDto.setUsername(username);
            userDto.setEmail(email);
            userDto.setFirstName(firstName);
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
}
