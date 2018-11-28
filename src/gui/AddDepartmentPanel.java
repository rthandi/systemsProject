package gui;

import classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddDepartmentPanel extends JPanel{
    public AddDepartmentPanel(User user){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Department Code:"));
        JTextField codeField = new JTextField(3);
        add(codeField);

        add(new JLabel("Department Name:"));
        JTextField nameField = new JTextField(20);
        add(nameField);

        JButton submit = new JButton("Submit");
        submit.setActionCommand("add Dept");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("add Dept")) {
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String deptCode = codeField.getText();
                        String deptName = nameField.getText();
                        SystemsOperations.addDepartment(user, deptCode, deptName, con);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        add(submit);
    }
}
