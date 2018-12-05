package gui;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class CheckRegistrationsPanel extends JPanel {
	public CheckRegistrationsPanel(AppFrame appFrame, User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Check Registrations:"));
		
		add(new JLabel("Username:"));
		JTextField usernameField = new JTextField(20);
		add(usernameField);
		
		
		JButton submitButton = new JButton("Submit");
		submitButton.setActionCommand("check registrations");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("check registrations")) {
					Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
						String username = usernameField.getText();
						
						PreparedStatement stmt = con.prepareStatement("SELECT Hash FROM User WHERE Username = ?");
						stmt.setString(1, username);

						ResultSet rs = stmt.executeQuery();
						while(rs.next()){
							String userHash = rs.getString("Hash");
							User student = SystemsOperations.getUser(username, userHash, con);
							JOptionPane.showMessageDialog(appFrame, "User:" + username + "DegreeID: " + student.getDegreeId());
						}
					
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
