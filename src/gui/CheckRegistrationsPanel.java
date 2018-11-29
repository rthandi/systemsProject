package gui;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class CheckRegistrationsPanel extends JPanel {
	public CheckRegistrationsPanel(User user) {
		JButton xButton = new JButton("");
		xButton.setActionCommand("");
		xButton.addActionListener(new ActionListener() {
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
