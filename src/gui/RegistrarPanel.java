package gui;
import classes.*;
import javax.swing.*;
import java.sql.*;

public class RegistrarPanel extends JTabbedPane {
	public RegistrarPanel(AppFrame appFrame, User user) throws SQLException {
		setBorder(BorderFactory.createTitledBorder("Welcome Registrar"));
		//THESE MAY CHANGE 
		addTab("Add Student", new AddStudentPanel(user));
		addTab("Remove Student", new RemoveStudentPanel(user));
		addTab("Add Module To Student", new AddModuleToStudentPanel(user, appFrame));
		addTab("Drop Module From Student", new DropModuleFromStudentPanel(user));
		addTab("Check Student Registrations", new  CheckRegistrationsPanel(appFrame, user));
	}

}
