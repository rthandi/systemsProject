package gui;
import classes.*;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdminPanel extends JTabbedPane{
    public AdminPanel(AppFrame appFrame, User user) throws SQLException {

        //DELETE DEPARTMENT PANEL
        JPanel deleteDeptPanel = new JPanel();
        ArrayList<Department> depts = null;
        try {
            depts = SystemsOperations.getDept();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JComboBox deptList = new JComboBox();

        for (Department d : depts) {
            deptList.addItem(d);
        }
        JButton deleteDeptButton = new JButton("Delete");
        deleteDeptButton.setActionCommand("delete dept");
        //Deletes the selected Department
        deleteDeptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("delete dept")) {
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
        for (Degree d : degrees) {
            degreeList.addItem(d);
        }
        JButton deleteDegButton = new JButton("Delete");
        //Deletes the selected Degree
        deleteDegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    if (con != null) {
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
                if (command.equals("add Dept")) {
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String deptCode = deptCodeField.getText();
                        String deptName = deptNameField.getText();
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
        addDeptPanel.add(addDeptButton);

        //Add Degree
        JPanel addDegreePanel = new JPanel();
        addDegreePanel.setLayout(new BoxLayout(addDegreePanel, BoxLayout.Y_AXIS));
        addDegreePanel.setBorder(BorderFactory.createTitledBorder("New degree:"));

        addDegreePanel.add(new JLabel("Degree ID:"));
        JTextField degreeIdField = new JTextField(6);
        addDegreePanel.add(degreeIdField);

        addDegreePanel.add(new JLabel("Degree name:"));
        JTextField degreeNameField = new JTextField(20);
        addDegreePanel.add(degreeNameField);

        addDegreePanel.add(new JLabel("Lead department")); //Can't reuse deptList for some reason
        JComboBox leadDeptList = new JComboBox();
        for (Department d : depts) {
            leadDeptList.addItem(d);
        }
        addDegreePanel.add(leadDeptList);

        JButton addDegButton = new JButton("Submit");
        addDegButton.setActionCommand("add degree");
        addDegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("add degree")) {
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String degID = degreeIdField.getText();
                        String degName = degreeNameField.getText();
                        Department leadDept = (Department) deptList.getSelectedItem();
                        String deptCode = leadDept.getDepartmentCode();

                        SystemsOperations.addDegree(user, degID, degName, deptCode, con);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        //Add module
        JPanel addModulePanel = new JPanel();
        addModulePanel.setLayout(new BoxLayout(addModulePanel, BoxLayout.Y_AXIS));
        addModulePanel.setBorder(BorderFactory.createTitledBorder("New Module:"));

        addModulePanel.add(new JLabel("Module Name:"));
        JTextField modNameField = new JTextField(20);
        addModulePanel.add(modNameField);

        addModulePanel.add(new JLabel("Degree"));
        JComboBox leadDegreeList = new JComboBox();
        for (Degree d : degrees) {
            leadDegreeList.addItem(d);
        }
        addModulePanel.add(leadDegreeList);

        addModulePanel.add(new JLabel("Module Credits:"));
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        JFormattedTextField modCredField = new JFormattedTextField(formatter);
        addModulePanel.add(modCredField);

        addModulePanel.add(new JLabel("Module Level:"));
        String[] levels = new String[]{"Certificate", "Diploma", "Bachelors", "Masters", "Placement"};
        JComboBox levelBox = new JComboBox();
        for (String l : levels) { levelBox.addItem(l); }
        addModulePanel.add(levelBox);

        JCheckBox compButton = new JCheckBox("Compulsory");
        compButton.setSelected(true);
        addModulePanel.add(compButton);

        JButton addModButton = new JButton("Submit");
        addModButton.setActionCommand("add mod");
        addModButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("add mod")) {
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String moduleId = modNameField.getText();
                        Degree deg = (Degree) leadDegreeList.getSelectedItem();
                        String degreeId = deg.getDegreeId();
                        String moduleName = modNameField.getText();
                        int credits = (int) modCredField.getValue();
                        boolean compulsory = compButton.isSelected();

                        String levelSelected = levelBox.getSelectedItem().toString();
                        char level = ' ';
                        if (levelSelected.equals("Certificate")) {
                            level = '1';
                        } else if (levelSelected.equals("Diploma")) {
                            level = '2';
                        } else if (levelSelected.equals("Bachelors")) {
                            level = '3';
                        } else if (levelSelected.equals("Masters")) {
                            level = '4';
                        }
                        try {
                            SystemsOperations.addModule(user, moduleId, moduleName, credits, level, compulsory, degreeId, con);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        addModulePanel.add(addModButton);

        //Add module to Degree

        addTab("Add Department", addDeptPanel);
        addTab("Delete Department", deleteDeptPanel);
        addTab("Add Degree", addDegreePanel);
        addTab("Delete Degree", deleteDegreePanel);
        addTab("Add new Module", addModulePanel);
    }
}
