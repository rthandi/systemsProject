package gui;
import classes.*;
import javax.swing.*;
import java.sql.*;

public class TeacherPanel extends JTabbedPane {
	public TeacherPanel(AppFrame appFrame, User user) throws SQLException {
		setBorder(BorderFactory.createTitledBorder("Welcome Teacher"));
		// THESE MAY CHANGE
		add("Update Grades", new UpdateGradesPanel(user));
	}

}
