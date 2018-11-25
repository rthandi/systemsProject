package gui;

import classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class LoginPanel extends JPanel{
    JTextField usernameField;
    JPasswordField passwordField;
    AppFrame theFrame;

    public LoginPanel(AppFrame appFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        theFrame = appFrame;
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Submit")){
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    //TODO check if the give username is in the database and if so the User is returned
                    User user = null;
                    try {
                        user = SystemsOperations.getUser(username, password);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if(user.getRole().equals("Student")){
                        theFrame.toStudentPanel(user);
                    }else if(user.getRole().equals("Admin")){
                        theFrame.toAdminPanel(user);
                    }
                }
            }
        });

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(submitButton);
    }

}
