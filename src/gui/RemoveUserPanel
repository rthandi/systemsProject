package gui;

import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

public class RemoveUserPanel extends JPanel {
	public RemoveUserPanel(User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Remove Student:"));
		
		add(new JLabel("Username:"));
		JTextField usernameField = new JTextField(20);
		add(usernameField);
		
		JButton submitButton = new JButton("");
		submitButton.setActionCommand("remove user");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("remove user")) {
					Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
						
						String username = usernameField.getText();
						
						PreparedStatement stmt = con.prepareStatement("DELETE FROM User WHERE Username = ?");
						stmt.setString(1, username);
						stmt.executeUpdate();
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
