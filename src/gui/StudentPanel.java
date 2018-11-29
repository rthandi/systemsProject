package gui;

import javax.sound.sampled.BooleanControl;
import javax.swing.*;
import java.awt.*;
import classes.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentPanel extends JPanel{
    public StudentPanel(User student){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(student.getFullName()));
        setSize(screenSize.width/3, screenSize.height/3);

        JPanel heading = new JPanel();
        heading.setLayout(new FlowLayout());
            JLabel degree = new JLabel(student.getDegreeId());
            heading.add(degree);

            heading.add(new JLabel(student.getTutorName()));

            JPanel levelPanel = new JPanel();
                levelPanel.add(new JLabel("Level:"));
                levelPanel.add(new JLabel("" + student.getLevel()));
            heading.add(levelPanel);

        add(heading, BorderLayout.NORTH);

        JPanel body = new JPanel();
            body.setLayout(new GridLayout(1,2));

            JPanel modules = new JPanel();
                modules.setLayout(new BoxLayout(modules, BoxLayout.Y_AXIS));
                Connection con = null;

                try {
                    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                    ArrayList<StudentModsGrades> grades = student.getGrades(con);
                    for (StudentModsGrades mod : grades){

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
                }


            JPanel misc = new JPanel();
                misc.setLayout(new BoxLayout(misc, BoxLayout.Y_AXIS));
                //TODO get the level of study, and a way of registering?

        body.add(modules);
        body.add(misc);

        add(body, BorderLayout.CENTER);

    }

}
