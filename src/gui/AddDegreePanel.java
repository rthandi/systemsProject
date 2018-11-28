package gui;

import javax.swing.*;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddDegreePanel extends JPanel {
    public AddDegreePanel(User user){
        ArrayList<Department> depts = null;
        try {
            depts = SystemsOperations.getDept();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel addDegreePanel = new JPanel();
        setLayout(new BoxLayout(addDegreePanel, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("New degree:"));

        add(new JLabel("Degree ID:"));
        JTextField degreeIdField = new JTextField(6);
        add(degreeIdField);

        add(new JLabel("Degree name:"));
        JTextField degreeNameField = new JTextField(20);
        add(degreeNameField);

        add(new JLabel("Lead department")); //Can't reuse deptList for some reason
        JComboBox leadDeptList = new JComboBox();
        for (Department d : depts) { leadDeptList.addItem(d); }
        add(leadDeptList);

        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("add degree");
        submitButton.addActionListener(new ActionListener()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("add degree")){
                    Connection con = null;
                    try{
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String degID = degreeIdField.getText();
                        String degName = degreeNameField.getText();
                        Department leadDept = (Department) leadDeptList.getSelectedItem();
                        String deptCode = leadDept.getDepartmentCode();

                        SystemsOperations.addDegree(user, degID, degName, deptCode, con);
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
