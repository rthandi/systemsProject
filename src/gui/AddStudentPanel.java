package gui;
import classes.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudentPanel extends JPanel {
	public AddStudentPanel(User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("New Student:"));
		
		add(new JLabel("Registration number:"));
		JTextField registrationNumberField = new JTextField(20);
		add(registrationNumberField);
		
		add(new JLabel("Password:"));
		JTextField passwordField = new JTextField(20);
		add(passwordField);
		
		add(new JLabel("Title:"));
		String[] titles = {"Mr", "Mrs", "Ms"};
		JComboBox titleList = new JComboBox(titles);
		
		add(new JLabel("Surname:"));
		JTextField surnameField = new JTextField(20);
		add(surnameField);
		
		add(new JLabel("Other names:"));
		JTextField otherNamesField = new JTextField(20);
		add(otherNamesField);
		
		add(new JLabel("Email:"));
		JTextField degreeIdField = new JTextField(20);
		add(degreeIdField);
		
		add(new JLabel("Email:"));
		JTextField emailField = new JTextField(20);
		add(emailField);
		
		add(new JLabel("Tutor name:"));
		JTextField tutorNameField = new JTextField(20);
		add(tutorNameField);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setActionCommand("add student");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("add student")) {
					Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
						
						// get the entered information
						String registrationNumber =  registrationNumberField.getText();
						String password = Sha.getSHA(passwordField.getText());
						String title = (String) titleList.getSelectedItem();
						String surname = surnameField.getText();
						String otherNames = otherNamesField.getText();
						String degreeId = degreeIdField.getText();
						String email = emailField.getText();
						String tutorName = tutorNameField.getText();
						
						// create a new user with this info
						User newUser = new User(registrationNumber, password, title, surname, otherNames, "Student", degreeId, email, tutorName, '1');
						
						// add the student
						SystemsOperations.addStudent(user, newUser, con);
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
			}
		});
		add(submitButton);
	}
}
