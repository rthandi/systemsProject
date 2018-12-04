package gui;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class UpdateGradesPanel extends JPanel {
	public UpdateGradesPanel(User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Student:"));
		
		add(new JLabel("Registration number:"));
		JTextField registrationNumberField = new JTextField(20);
		add(registrationNumberField);
		
		add(new JLabel("Password:"));
		JTextField passwordField = new JTextField(20);
		add(passwordField);
		
		add(new JLabel("Title:"));
		String[] titles = {"Mr", "Ms"};
		JComboBox titleList = new JComboBox(titles);
		
		add(new JLabel("Surname:"));
		JTextField surnameField = new JTextField(20);
		add(surnameField);
		
		add(new JLabel("Other names:"));
		JTextField otherNamesField = new JTextField(20);
		add(otherNamesField);
		
		add(new JLabel("Degree ID:"));
		JTextField degreeIdField = new JTextField(20);
		add(degreeIdField);
		
		add(new JLabel("Email:"));
		JTextField emailField = new JTextField(20);
		add(emailField);
		
		add(new JLabel("Tutor name:"));
		JTextField tutorNameField = new JTextField(20);
		add(tutorNameField);
		
		
		add(new JLabel("Module ID:"));
		JTextField moduleIdField = new JTextField(20);
		add(moduleIdField);
		
		//add(new JLabel("Resit:"));
		//JComboBox resitList = new JComboBox();
		//resitList.addItem(true);
		//resitList.addItem(false);
		
		add(new JLabel("Grade:"));
		JTextField gradeField = new JTextField(20);
		add(gradeField);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setActionCommand("update grades");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("update grades")) {
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
						String moduleId = moduleIdField.getText();
						String grade = gradeField.getText();
						
					
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

		
		
	
