package gui;
import classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminPanel extends JTabbedPane{
    public AdminPanel(AppFrame appFrame, User user) throws SQLException {


    //DELETE DEPARTMENT PANEL
    JPanel deleteDeptPanel = new JPanel();
        ArrayList<Department> depts = null;
        try{
            depts = SystemsOperations.getDept();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JComboBox deptList = new JComboBox();

        for(Department d:depts){
            deptList.addItem(d);
        }
        JButton deleteDeptButton = new JButton("Delete");
        deleteDeptButton.setActionCommand("delete dept");
        //Deletes the selected Department
        deleteDeptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("delete dept")){
                    Department chosen = (Department) deptList.getSelectedItem();
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        SystemsOperations.deleteDepartment(user, chosen.getDepartmentCode(), con);
                        deptList.remove(deptList.getSelectedIndex()); //ewwwwwww
                        con.close();
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


        deleteDeptPanel.add(deptList);
        deleteDeptPanel.add(deleteDeptButton);

    //DELETE DEGREE PANEL
    JPanel deleteDegreePanel = new JPanel();
        ArrayList<Degree> degrees = null;
        try {
            degrees = SystemsOperations.getDegrees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JComboBox degreeList = new JComboBox();

        for(Degree d:degrees) {
            degreeList.addItem(d);
        }

        JButton deleteDegButton = new JButton("Delete");
        //Deletes the selected Degree
        deleteDegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Degree chosen = (Degree) degreeList.getSelectedItem();
                Connection con = null;
                try {
                    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                    SystemsOperations.deleteDegree(user, chosen.getDegreeId(), con);
                    degreeList.remove(degreeList.getSelectedIndex());
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    if(con != null){
                        try {
                            con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });


        deleteDegreePanel.add(degreeList);
        deleteDegreePanel.add(deleteDegButton);

    //Add a department
    JPanel addDeptPanel = new JPanel();
        addDeptPanel.setLayout(new BoxLayout(addDeptPanel, BoxLayout.Y_AXIS));

        addDeptPanel.add(new JLabel("Department Code:"));
        JTextField deptCodeField = new JTextField(3);
        addDeptPanel.add(deptCodeField);

        addDeptPanel.add(new JLabel("Department Name:"));
        JTextField deptNameField = new JTextField(20);
        addDeptPanel.add(deptNameField);

        JButton addDeptButton = new JButton("Submit");
        addDeptButton.setActionCommand("add Dept");
        addDeptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("add Dept")){
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String deptCode = deptCodeField.getText();
                        String deptName = deptNameField.getText();

                        SystemsOperations.addDepartment(user, deptCode, deptName, con);


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        if(con != null){
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
        addDeptPanel.add(addDeptButton);

    //Add Degree
    JPanel addDegreePanel = new JPanel();
        addDegreePanel.setLayout(new BoxLayout(addDegreePanel, BoxLayout.Y_AXIS));
        addDegreePanel.setBorder(BorderFactory.createTitledBorder("New degree:"));

        addDegreePanel.add(new JLabel("Degree ID:"));
        JTextField degreeIdField = new JTextField(6);
        addDegreePanel.add(degreeIdField);

        addDegreePanel.add(new JLabel("Degree name:"));
        JTextField degreeNameField = new JTextField(6);
        addDegreePanel.add(degreeNameField);

        addDegreePanel.add(new JLabel("Lead department"));
        addDegreePanel.add(deptList);

        JButton addDegButton = new JButton("Submit");
        addDegButton.setActionCommand("add degree");
        addDegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("add degree")){
                    Connection con = null;
                    try{
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");

                        String degID = degreeIdField.getText();
                        String degName = degreeNameField.getText();
                        Department leadDept = (Department) deptList.getSelectedItem();
                        String deptCode = leadDept.getDepartmentCode();

                        SystemsOperations.addDegree(user, degID, degName, deptCode, con);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        try{con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });





    add("Add Department", addDeptPanel);
    add("Add Degree", addDegreePanel);
    addTab("Delete Department", deleteDeptPanel);
    add("Delete Degree", deleteDegreePanel);
    }

}
