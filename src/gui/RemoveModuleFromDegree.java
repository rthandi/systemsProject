package gui;

import javax.swing.*;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveModuleFromDegree extends JPanel {
    public RemoveModuleFromDegree(User user, AppFrame appFrame){
        Connection con = null;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Remove Module approval from degree"));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            ArrayList<String> modIds = SystemsOperations.getModIds(user, con);
            ArrayList<Degree> degrees = SystemsOperations.getDegrees();
            ArrayList<String> degIds = new ArrayList<>();
            for (Degree d : degrees) {
                degIds.add(d.getDegreeId());
            }

            add(new JLabel("Degree ID"));
            JTextField degreeField = new JTextField(8);
            add(degreeField);

            add(new JLabel("Module ID"));
            JTextField moduleField = new JTextField(7);
            add(moduleField);

            JButton submitButton = new JButton("Submit");
            Connection finalCon = con;
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String chosenModule = moduleField.getText();
                    String chosenDegree = degreeField.getText();
                    if ((modIds.contains(chosenModule)) && (degIds.contains(chosenDegree))) {
                        try {
                            SystemsOperations.deleteDegModAprov(user, chosenModule, chosenDegree, finalCon);
                            JOptionPane.showMessageDialog(appFrame, "Module removed from degree");
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {if (finalCon != null) finalCon.close(); } catch (Exception es) {es.printStackTrace(System.err);}
                        }
                    } else{
                        System.out.println("This link could not be found");
                    }
                }
            });
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(System.err); }
        }
    }
}
