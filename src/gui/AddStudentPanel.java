package gui;
import classes.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudentPanel extends JPanel {
	public AddStudentPanel(User user) {
		JPanel addStudentPanel = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("New Student:"));
		
		add(new JLabel("Username:"));
		
		add(new JLabel("Title:"));
		String[] titles = {"Mr", "Mrs", "Ms"};
		JComboBox titleList = new JComboBox(titles);
		
		add(new JLabel("Surname:"));
		
		add(new JLabel("Other names:"));
		
		add(new JLabel("Email:"));
		
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
