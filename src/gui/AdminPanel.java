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
        //Deletes the selected Department
        deleteDeptButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Department chosen = (Department) deptList.getSelectedItem();
                Connection con = null;
                try {
                    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                    SystemsOperations.deleteDepartment(user, chosen.getDepartmentCode(),con);
                    deptList.remove(deptList.getSelectedIndex()); //ewwwwwww
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }finally{
                    if(con != null) {
                        try {
                            con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
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
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                    SystemsOperations.deleteDegree(user, chosen.getDegreeId(), con);
                    degreeList.remove(degreeList.getSelectedIndex());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleteDegreePanel.add(degreeList);
        deleteDegreePanel.add(deleteDegButton);


        addTab("Delete Department", deleteDeptPanel);
        add("Delete Degree", deleteDegreePanel);
    }

}
