package gui;

import classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModuleDegreePanel extends JPanel {
    public ModuleDegreePanel(User user) {

        ArrayList<Degree> degrees = null;
        try {
            degrees = SystemsOperations.getDegrees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JComboBox degreeList = new JComboBox();
        for (Degree d : degrees) {
            degreeList.addItem(d);
        }
        add(degreeList);

        add(new JLabel("Module ID:"));
        JTextField modToConnect = new JTextField(20);
        add(modToConnect);

        add(new JLabel("Module Level:"));
        JComboBox levelSelect = new JComboBox();
        String[] levels = new String[]{"Certificate", "Diploma", "Bachelors", "Masters", "Placement"};
        for (String l : levels) {
            levelSelect.addItem(l);
        }
        add(levelSelect);

        JCheckBox compulsoryCheck = new JCheckBox("Compulsory");
        compulsoryCheck.setSelected(true);
        add(compulsoryCheck);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try{
                    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                    Degree deg = (Degree) degreeList.getSelectedItem();
                    String degreeId = deg.getDegreeId();
                    String moduleId = modToConnect.getText();
                    boolean compulsory = compulsoryCheck.isSelected();
                    String levelSelected = levelSelect.getSelectedItem().toString();
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
                    SystemsOperations.addModuleToDegree(user,degreeId, level, moduleId,compulsory,con);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    try { con.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                }
            }
        });
    }
}
