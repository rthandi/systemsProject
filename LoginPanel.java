import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginPanel extends JPanel implements ActionListener{
    JTextField usernameField;
    JPasswordField passwordField;
    AppFrame theFrame;

    public User LoginPanel(AppFrame appFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        theFrame = appFrame;
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(submitButton);


    }

    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();
        if (command.equals("Submit")){
            String username = usernameField.getText();
            String password = passwordField.getText();
            ////////////////////password needs to be hashed etc
            System.out.println(username);
            if(username.equals("student")){

            }
        }
    }
}
