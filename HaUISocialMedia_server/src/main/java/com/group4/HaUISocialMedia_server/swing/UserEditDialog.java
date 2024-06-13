/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group4.HaUISocialMedia_server.swing;

import com.toedter.calendar.JDateChooser;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author dater
 */
public class UserEditDialog extends JDialog {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> statusComboBox;
    private JDateChooser birthDateChooser;
    private boolean saved;

    public UserEditDialog(Frame parent, String id, String code, String username, String email,
                          String firstName, String lastName, String address, Date birthDate,
                          String gender, String phone, String status) {
        super(parent, "Edit User", true);
        setLayout(new GridLayout(0, 2));

        add(new JLabel("ID:"));
        add(new JLabel(id));

        add(new JLabel("Code:"));
        add(new JLabel(code));

        add(new JLabel("Username:"));
        usernameField = new JTextField(username);
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(email);
        add(emailField);

        add(new JLabel("First Name:"));
        firstNameField = new JTextField(firstName);
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField(lastName);
        add(lastNameField);

        add(new JLabel("Address:"));
        addressField = new JTextField(address);
        add(addressField);

        add(new JLabel("Birth Date:"));
        birthDateChooser = new JDateChooser(birthDate);
        add(birthDateChooser);

        add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setSelectedItem(gender);
        add(genderComboBox);

        add(new JLabel("Phone:"));
        phoneField = new JTextField(phone);
        add(phoneField);

        add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Active", "Disabled"});
        statusComboBox.setSelectedItem(status);
        add(statusComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = true;
                setVisible(false);
            }
        });
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = false;
                setVisible(false);
            }
        });
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() {
        return saved;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public Date getBirthDate() {
        return birthDateChooser.getDate();
    }

    public String getGender() {
        return (String) genderComboBox.getSelectedItem();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getStatus() {
        return (String) statusComboBox.getSelectedItem();
    }
}
