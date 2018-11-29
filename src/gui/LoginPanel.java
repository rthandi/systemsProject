package gui;

import classes.*;
import javax.swing.*;
import javax.swing.border.Border;
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
        setBorder(BorderFactory.createTitledBorder("Login"));
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
                    String password = Sha.getSHA(passwordField.getText());
                    try {
                        User user = SystemsOperations.getUser(username, password);
                        if(user.getRole().equals("Student")){
                            theFrame.toStudentPanel(user);
                        }else if(user.getRole().equals("Administrator")){
                            theFrame.toAdminPanel(user);
                        }else if(user.getRole().equals("Registrar")){
                        	theFrame.toRegistrarPanel(user);
                        }else if (user.getRole().equals("Teacher")){
                        	theFrame.toTeacherPanel(user);
                        }else {
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
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
