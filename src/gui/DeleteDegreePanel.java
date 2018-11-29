package gui;

import classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteDegreePanel extends JPanel {
    public DeleteDegreePanel(User user){

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
        add(degreeList);
        add(deleteDegButton);
    }
}
