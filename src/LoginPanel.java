import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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
                    ////////////////////password needs to be hashed etc
                    System.out.println(username);
                    if(username.equals("student")){
                        appFrame.toStudentPanel(username);
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
