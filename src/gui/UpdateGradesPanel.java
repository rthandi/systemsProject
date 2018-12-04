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
		setBorder(BorderFactory.createTitledBorder("Update Grades:"));
		
		add(new JLabel("Username:"));
		JTextField usernameField = new JTextField(20);
		add(usernameField);
		
		add(new JLabel("Module ID:"));
		JTextField moduleIdField = new JTextField(20);
		add(moduleIdField);
		
		add(new JLabel("Resit:"));
		JComboBox resitList = new JComboBox();
		resitList.addItem(true);
		resitList.addItem(false);
		
		
		add(new JLabel("Grade:"));
		JTextField gradeField = new JTextField(20);
		add(gradeField);
		
		JButton submitButton = new JButton("Update");
		submitButton.setActionCommand("update grade");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("update grade")) {
					Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
						String username = usernameField.getText();
						String moduleId = moduleIdField.getText();
						Boolean resit = (Boolean) resitList.getSelectedItem();
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
	}
}
