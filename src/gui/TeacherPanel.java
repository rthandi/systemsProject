package gui;
import classes.*;
import javax.swing.*;
import java.sql.*;

public class TeacherPanel extends JTabbedPane {
	public TeacherPanel(AppFrame appFrame, User user) throws SQLException {
		// THESE MAY CHANGE
		addTab("Update Grades", new(UpdateGradesPanel));
	}

}
