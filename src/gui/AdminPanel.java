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
    public AdminPanel(AppFrame appFrame, User user){
        JPanel deleteDeptPanel = new JPanel();
        deleteDeptPanel.add(new JButton("Kill me"));

        JPanel deleteDegreePanel = new JPanel();
        ArrayList<Degree> degrees = null;
        try {
            degrees = SystemsOperations.getDegrees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JComboBox degreeList = new JComboBox();

        for(Degree x:degrees) {
            degreeList.addItem(x);
        }

        JButton deleteButton = new JButton("Delete");
        //Deletes the selected Degree
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Degree chosen = (Degree) degreeList.getSelectedItem();
                try {
                    SystemsOperations.deleteDegree(user, chosen.getDegreeId());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleteDegreePanel.add(degreeList);
        deleteDegreePanel.add(deleteButton);


        addTab("Delete Department", deleteDeptPanel);
        add("Delete Degree", deleteDegreePanel);
    }
}
