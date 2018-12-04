package gui;

import javax.swing.*;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddUserPanel extends JPanel {
    public AddUserPanel(User currentUser){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("New user"));

        add(new JLabel("Dsername"));
        JTextField usernameField = new JTextField(8);
        add(usernameField);

        add(new JLabel("Password"));
        JTextField passwordField = new JTextField(20);
        add(passwordField);

        add(new JLabel("Title"));
        JTextField titleField = new JTextField(3);
        add(titleField);

        add(new JLabel("Surname"));
        JTextField surnameField = new JTextField(20);
        add(surnameField);

        add(new JLabel("Othernames"));
        JTextField othernamesField = new JTextField(20);
        add(othernamesField);

        add(new JLabel("Role"));
        String[] roles = {"Student", "Teacher", "Registrar", "Administrator"};
        JComboBox roleList = new JComboBox(roles);
        add(roleList);

        add(new JLabel("Email"));
        JTextField emailField = new JTextField(20);
        add(emailField);

        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("add user");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("add user")){
                    Connection con = null;
                    try{
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        //String usernameInput, String hashInput, String titleInput, String surnameInput, String otherNamesInput, String roleInput, String emailInput
                        String username = usernameField.getText();
                        String hash = Sha.getSHA(passwordField.getText());
                        String title = titleField.getText();
                        String surname = surnameField.getText();
                        String othernames = othernamesField.getText();
                        String role = (String) roleList.getSelectedItem();
                        String email = emailField.getText();
                        User newUser = new User(username,hash,title,surname,othernames,role,email);
                        SystemsOperations.addUser(currentUser, newUser, con);
                    }catch(SQLException e1) {
                        e1.printStackTrace();
                    }finally{
                        try { con.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                    }
                }
            }
        });
        add(submitButton);
    }
}