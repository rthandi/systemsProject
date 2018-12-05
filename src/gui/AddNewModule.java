package gui;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AddNewModule extends JPanel {
    public AddNewModule(User user, AppFrame appFrame){
        ArrayList<Degree> degrees = null;
        try {
            degrees = SystemsOperations.getDegrees();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("New Module:"));

        add(new JLabel("Module Name:"));
        JTextField modNameField = new JTextField(20);
        add(modNameField);

        add(new JLabel("Module number:"));
        JTextField modNumField = new JTextField(7);
        add(modNumField);

        add(new JLabel("Degree"));
        JComboBox degreeList = new JComboBox();
        for (Degree d : degrees) {
            degreeList.addItem(d);
        }
        add(degreeList);

        add(new JLabel("Module Credits:"));
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(100);
        formatter.setAllowsInvalid(false);
        JFormattedTextField modCredField = new JFormattedTextField(formatter);
        add(modCredField);

        add(new JLabel("Module Level:"));
        String[] levels = new String[]{"Certificate", "Diploma", "Bachelors", "Masters", "Placement"};
        JComboBox levelSelect = new JComboBox();
        for (String l : levels) { levelSelect.addItem(l); }
        add(levelSelect);

        JCheckBox compButton = new JCheckBox("Compulsory");
        compButton.setSelected(true);
        add(compButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("add mod");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("add mod")) {
                    Connection con = null;
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        Degree deg = (Degree) degreeList.getSelectedItem();
                        String degreeId = deg.getDegreeId();
                        String moduleId = modNumField.getText();
                        String moduleName = modNameField.getText();
                        int credits = (int) modCredField.getValue();
                        boolean compulsory = compButton.isSelected();

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
                        try {
                            SystemsOperations.addModule(user, moduleId, moduleName, credits, level, compulsory, degreeId, con);
                            JOptionPane.showMessageDialog(appFrame, "The degree is added!");
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } catch (SQLException es) {
                        es.printStackTrace();
                    } finally {
                        try { con.close(); } catch (SQLException e1) { e1.printStackTrace(); }
                    }
                }
            }
        });
        add(submitButton);
    }
}
