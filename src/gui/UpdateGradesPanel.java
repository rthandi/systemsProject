package gui;
import classes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.text.NumberFormat;

import javax.swing.*;

public class UpdateGradesPanel extends JPanel implements PropertyChangeListener {
    JFormattedTextField gradeField;
    private int rate = 0;

	public UpdateGradesPanel(User user) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Student:"));
		
		add(new JLabel("Registration number:"));
		JTextField registrationNumberField = new JTextField(20);
		add(registrationNumberField);

        add(new JLabel("Module ID:"));
        JTextField moduleField = new JTextField(20);
        add(moduleField);
		
		add(new JLabel("Grade:"));
        NumberFormat intFormat = NumberFormat.getNumberInstance();
		gradeField = new JFormattedTextField(intFormat);
		gradeField.setValue(0);
        gradeField.setColumns(10);
        gradeField.addPropertyChangeListener("value",  this);
		add(gradeField);

        add(new JLabel("Resit:"));
        JCheckBox resitBox = new JCheckBox();
        add(resitBox);

		JButton submitButton = new JButton("Submit");
		submitButton.setActionCommand("update grades");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals("update grades")) {
					Connection con = null;
					Statement stmt = null;
					ResultSet rs = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        String username = registrationNumberField.getText();

						stmt = con.createStatement();
						String query = "SELECT Hash FROM User " +
								"WHERE Username = '" + username + "'";
						rs = stmt.executeQuery(query);
						if(rs.next()){
							String userHash = rs.getString("Hash");
							String moduleId = moduleField.getText();
							boolean resit = resitBox.isSelected();
							int grade = Integer.valueOf(gradeField.getText());

							User student = SystemsOperations.getUser(username, userHash, con);
							SystemsOperations.updateGrades(user, student, moduleId, resit, grade, con);
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
		add(submitButton);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if(source == gradeField){
            rate = ((Number)gradeField.getValue()).intValue();
        }
    }
}

		
		
	
