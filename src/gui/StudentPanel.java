package gui;

import javax.swing.*;
import java.awt.*;
import classes.*;

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

            JPanel levelPanel = new JPanel();
                levelPanel.add(new JLabel("Current Level:"));
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
                        modules.add(new ModulePanel(mod));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
            JPanel misc = new JPanel();
                misc.setLayout(new BoxLayout(misc, BoxLayout.Y_AXIS));
                JPanel tutorPanel = new JPanel();
                    tutorPanel.setLayout(new FlowLayout());
                    tutorPanel.add(new JLabel("Tutor:"));
                    tutorPanel.add(new JLabel(student.getTutorName()));

                JPanel emailPanel = new JPanel();
                    emailPanel.setLayout(new FlowLayout());
                    emailPanel.setBorder(BorderFactory.createTitledBorder("Your email:"));
                    emailPanel.add(new JLabel(student.getEmail()));

                misc.add(tutorPanel);
                misc.add(emailPanel);

                //TODO output what the student achieved at every level
                char[] levels = {'1','2','3','4'};
                int temp = 0;
                while (levels[temp] != student.getLevel()) {
                    misc.add(new LevelPanel(student, levels[temp]));
                    temp +=1;
                }
        body.add(modules);
        body.add(misc);

        add(body, BorderLayout.CENTER);

    }

}
