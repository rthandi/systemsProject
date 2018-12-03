package gui;

import javax.swing.*;
import classes.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LevelPanel extends JPanel{
    public LevelPanel(User user, char level){
        setBorder(BorderFactory.createTitledBorder("Level "+level));
        int grade;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            grade = user.calculateMeanGrade(con, level);
            add(new JLabel("Grade achieved "+grade+"%"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
