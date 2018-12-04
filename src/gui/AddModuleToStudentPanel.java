package gui;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;


public class AddModuleToStudentPanel extends JPanel {
	public AddModuleToStudentPanel(User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Add module"));

		add(new JLabel("Username:"));
		JTextField usernameField = new JTextField(8);
		add(usernameField);

		add(new JLabel("Module ID:"));
		JTextField modIdField = new JTextField(6);
		add(modIdField);

		JButton xButton = new JButton("Submit");
		xButton.setActionCommand("add module");
		xButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				Statement stmt = null;
				ResultSet rs = null;
				if (command.equals("add module")) {
					Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
						String username = usernameField.getText();

						stmt = con.createStatement();
						String query = "SELECT Hash FROM User " +
								"WHERE Username = '" + username + "'";
						rs = stmt.executeQuery(query);
						if(rs.next()){
							String userHash = rs.getString("Hash");
                            String moduleId = modIdField.getText();

                            User student = SystemsOperations.getUser(username, userHash, con);
                            SystemsOperations.addOptionalModuleToUser(user, student, moduleId, con);
						} else {
                            System.out.println("No such user");
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
		add(xButton);
	}
}
