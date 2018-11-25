package classes;

import java.sql.*;

public class Test2 {
	
	/**
	 * 
	 * @param currentUser
	 * @param newUser
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean addUser (User currentUser, User newUser, Connection con) throws SQLException {
		// Check user privilege
    	if (SystemsOperations.permissionCheck(currentUser) <= 2) {
    		System.out.println("Permission level not high enough to perform this operation");
    		return false;
    	}
    	Statement stmt = null;
    	Statement stmt2 = null;
    	ResultSet users = null;
    	ResultSet modules = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT Username " +
                    "FROM User " +
					"WHERE Username = " + newUser.getRegistrationNumber();
            users = stmt.executeQuery(query);

			//Check to see if the inputted username already exists
			if (users.next()){
				System.out.println("User already exists");
				return false;
			}

			query = " SELECT Module_id FROM Degree_Module_Approved " +
					" WHERE Compulsory = '1' AND Degree_id = " + newUser.getDegreeId();
			modules = stmt.executeQuery(query);

            // Insert new user into User table
            query = "INSERT INTO User " +
  		              "VALUES ( " + newUser.getRegistrationNumber() + ", " + newUser.getHash() + ", " + newUser.getTitle() + ", " + newUser.getSurname() +
  		              ", " + newUser.getOtherNames() + ", " + newUser.getRole() + ", " + newUser.getDegreeId() + ", " + newUser.getEmail() +
  		              ", " + newUser.getTutorName() + ")";
            stmt2 = con.createStatement();
            stmt2.executeUpdate(query);
            
            // Enrol student on all compulsory modules
            String moduleName = null;
            while (modules.next()) {
            	System.out.println(modules.getString(1));
            	moduleName = "'" + modules.getString(1) + "'";
            	query = "INSERT INTO Student_Module " +
            			"VALUES ( " + newUser.getRegistrationNumber() + ", " + moduleName + ", '0')";
            	stmt2.execute(query);
            }
            return true;
        } catch (SQLException e){
			e.printStackTrace(System.err);
			return false;
		} finally {
			// Close all open resources
			try { if (users != null) users.close(); } catch (Exception e) {}
			try { if (modules != null) modules.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (stmt2 != null) stmt2.close(); } catch (Exception e) {}
		}
    } 
	
	public static void main (String[] args) throws SQLException {
		
		// Create test users
		User current = new User("aca17ab", null, null, null, null, "Administrator", null, null, null);
		User newUser = new User("'aca17be'", "'hash'", "'title'", "'surname'", "'other'", "'Student'", "'COMU01'", "'email'", "'tutor'");

		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f")) {
			
			try {
				addUser(current , newUser, con);
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			finally {
			      try{
			      	con.close();
			      }catch(SQLException se){
			         se.printStackTrace(System.err);
			      }
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		
	}

}
