package classes;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SystemsOperations {
    /**
     * @param user The currently user that you want to check the total credits for
     * @param con The current connection to the sql database
     * @return true if they have the right amount of credits and false if they don't
     * @throws SQLException If error with database then will print the error and return false
     */
    public static boolean checkTotalCredits(User user, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT Credits " +
                    "FROM Student_Module " +
                    "WHERE Username = " + user.getRegistrationNumber() +
                    " INNER JOIN Modules " +
                    " ON Student_Module.Module_id = Modules.Module_id";
            //Insert sql query to get the modules that the user is doing
            rs = stmt.executeQuery(query);
            // Iterate over the ResultSet to total up the credits
            int counter = 0;
            while (rs.next()) {
                counter += rs.getInt("Credits");
            }
            String level = user.getDegreeId().substring(3, 4);
            //If U then it is undergrad
            if (Objects.equals(level, "U")) {
                return counter == 120;
            } else {
                return counter == 180;
            }
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
            return false;
        } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		}
    }
    /**
     * @param currentUser The user that is currently logged in
     * @param departmentToDelete The department code of the department that is to be deleted
     * @param con Current connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    //DELETION OPERATIONS
    public static void deleteDepartment (User currentUser, String departmentToDelete, Connection con) throws SQLException {
		Statement stmt = null;
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (currentUser.permissionCheck() >= 4) { 
	            stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Department " +
	                    "WHERE Department_Code = " + departmentToDelete;
	            stmt.executeUpdate(query);
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
        } finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		}
    }

    /**
     * @param currentUser The currently logged in user
     * @param degreeIdToDelete the ID of the degree that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteDegree (User currentUser, String degreeIdToDelete) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            try {
                //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
                if (permissionCheck(currentUser) >= 4) {
                    stmt = con.createStatement();
                    //If database is setup correctly this should cascade and delete any mentions of this department
                    String query = "DELETE FROM Degree " +
                            "WHERE Degree_id = '" + degreeIdToDelete + "'";
                    stmt.executeUpdate(query);
                } else {
                    System.out.println("Permission level not high enough to perform this operation");
                }
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (Exception e) {
                }
            }
        } finally {
            if(con != null) con.close();
        }
    }

    /**
     * @param currentUser The currently logged in user
     * @param moduleId the ID of the module that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteModule (User currentUser, String moduleId, Connection con) throws SQLException {
		Statement stmt = null;

        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Modules " +
	                    "WHERE Module_id = " + moduleId;
	            stmt.executeUpdate(query);
    		} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        } finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		}
    }
   	
    /**	
     * 	
     * @param currentUser The currently logged in user	
     * @param delUser The user that is to be deleted
     * @param con The open connection to the database	
     * @throws SQLException Will print out the error with the database if one is encountered	
     */	
    public static void deleteUser (User currentUser, User delUser, Connection con) throws SQLException {	
        try {	
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 3	
        	if (currentUser.permissionCheck() >= 3) {	
	            Statement stmt = con.createStatement();	
	            //If database is setup correctly this should cascade and delete any mentions of this department	
	            String query = "DELETE FROM User " +	
	                    "WHERE Username = " + delUser.getRegistrationNumber();	
	            stmt.executeUpdate(query);	
    		} else {	
        		System.out.println("Permission level not high enough to perform this operation");	
        	}	
        } catch (SQLException e) {	
            e.printStackTrace(System.err);	
        }	
    }	
    	
    /**	
     * 	
     * @param currentUser The currently logged in user	
     * @param userToRemove The user that is to be deleted
     * @param moduleId The ID of the module that is to be deleted	
     * @param con The open connection to the database	
     * @throws SQLException Will print out the error with the database if one is encountered	
     */	
    public static void deleteStudentModule (User currentUser, User userToRemove, String moduleId, Connection con) throws SQLException {	
        try {	
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4	
        	if (currentUser.permissionCheck() >= 3) {	
	            Statement stmt = con.createStatement();	
	            //If database is setup correctly this should cascade and delete any mentions of this department	
	            String query = "DELETE FROM Student_Module " +	
	                    "WHERE Module_id = " + moduleId + " AND Username = " + userToRemove.getRegistrationNumber();	
	            stmt.executeUpdate(query);	
    		} else {	
        		System.out.println("Permission level not high enough to perform this operation");	
        	}	
        } catch (SQLException e) {	
            e.printStackTrace(System.err);	
        }	
    }	
    	
    /**	
     * 	
     * @param currentUser The currently logged in user	
     * @param moduleId The ID of the module that is to be deleted	
     * @param con The open connection to the database	
     * @throws SQLException Will print out the error with the database if one is encountered	
     */	
    public static void deleteDegModAprov (User currentUser, String moduleId, Connection con) throws SQLException {	
        try {	
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4	
        	if (currentUser.permissionCheck() >= 3) {	
	            Statement stmt = con.createStatement();	
	            //If database is setup correctly this should cascade and delete any mentions of this department	
	            String query = "DELETE FROM Degree_Module_Approved " +	
	                    "WHERE Module_id = " + moduleId;	
	            stmt.executeUpdate(query);	
    		} else {	
        		System.out.println("Permission level not high enough to perform this operation");	
        	}	
        } catch (SQLException e) {	
            e.printStackTrace(System.err);	
        }	
    }

    /**
     * @param currentUser The user that is currently logged in
     * @param departmentCode The code of the department that is being added
     * @param departmentName The name of the department that is being added
     * @param con Current connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    //ADDING OPERATIONS
    public static void addDepartment (User currentUser, String departmentCode, String departmentName, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.createStatement();
	            String query = "INSERT INTO Department " +
	                    "VALUES (" + departmentCode + ", " + departmentName + ")";
	            stmt.executeUpdate(query);
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * @param currentUser The currently logged in user
     * @param degreeId The ID of the degree to add
     * @param degreeName The name of the degree to add
     * @param departmentCode The department code that the degree belongs too
     * @param con The current connection to the database
     * @return True if successful and false if not successful
     * @throws SQLException Throws and prints the error if there is an issue with the database
     */
    public static boolean addDegree (User currentUser, String degreeId, String degreeName, String departmentCode, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4 
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.createStatement();
	            String query = "SELECT  Department_Name " +
	                    "FROM Department " +
	                    "WHERE Department_Code = " + departmentCode;
	            rs = stmt.executeQuery(query);
	            //Check if the department that was inputted exists
	            if (rs.next()) {
	                query = "INSERT INTO Degree " +
	                        "VALUES (" + degreeId + ", " + degreeName + ", " + departmentCode + ")";
	                stmt.executeUpdate(query);
	                return true;
	            } else {
	                return false;
	            }
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        		return false;
        	}
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		}
    }

    /**
     * 
     * @param currentUser The currently logged in user
     * @param moduleId The ID of the Module to add
     * @param moduleName The name of the Module to add
     * @param credits The amount of Credits the Module will have
     * @param level The Level of study required to take the Module
     * @param compulsory Whether the module is compulsory to the degree or not
     * @param degreeId The ID of the degree to add
     * @param con The current connection to the database
     * @return True if successful and false if not successful
     * @throws SQLException Throws and prints the error if there is an issue with the database
     */
    public static boolean addModule  (User currentUser, String moduleId, String moduleName, int credits, char level, boolean compulsory, String degreeId, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.createStatement();
	            String query = "SELECT Degree_Name " +
	                    "FROM Degree " +
	                    "WHERE Degree_id = " + degreeId;
	            rs = stmt.executeQuery(query);
	            //Check to see if the inputted department exists
	            if (rs.next()) {
	                //First insert into Modules
	                query = "INSERT INTO Modules " +
	                        "VALUES ( " + moduleId + ", " + moduleName + ", " + credits + ")";
	                stmt.executeUpdate(query);
	                query = "INSERT INTO Degree_Module_Approved " +
	                        "VALUES ( " + degreeId + ", " + level + ", " + moduleId + ", " + boolToInt(compulsory) + ")";
	                stmt.executeUpdate(query);
	                return true;
	            } else {
	                return false;
	            }
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        		return false;
        	}
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return false;
        } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		}
    }

    private static int boolToInt(Boolean bool){
        if (bool){
            return 1;
        } else {
            return 0;
        }
    }

    //GETTING
    /**
     *
     * @param usernameInput
     * @param hashInput
     * @return user from given
     * @return null if no such user
     * @throws SQLException if error with the database, should still return null
     */
    public static User getUser(String usernameInput, String hashInput) throws SQLException { //will have password hash if that gets done
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = null;

                String query = "SELECT * FROM User " +
                        "WHERE Username = '" + usernameInput +
                        "' AND Hash = '" + hashInput +"'";
                //String query = "SELECT * FROM User WHERE Username = 'aca17ab' AND Hash = 'hashsash'";

                rs = stmt.executeQuery(query);
                rs.first();

                String username = rs.getString("Username");
                String hash = rs.getString("Hash");
                String title = rs.getString("Title");
                String surname = rs.getString("Surname");
                String otherNames = rs.getString("Other_names");
                String role = rs.getString("Role");
                String email = rs.getString("Email");
                con.close();
                return new User(username, hash, title, surname, otherNames, role, email);
            } catch (SQLException e) {
                e.printStackTrace(System.err);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) con.close();
        }
    }

    public static ArrayList<Degree> getDegrees() throws SQLException{
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            try {
                ArrayList<Degree> degrees = new ArrayList<>();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Degree");
                while (rs.next()) {
                    String id = rs.getString("Degree_id");
                    String name = rs.getString("Degree_Name");
                    String code = rs.getString("Department_Code");
                    degrees.add(new Degree(id, name, code));
                }
                con.close();
                return degrees;
            } catch (SQLException e) {
                e.printStackTrace(System.err);
                return null;
            }
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return null;
        }finally {
            if(con != null) con.close();
        }
    }

    //MISC

    /**
	 * 
	 * @param currentUser The currently logged in user
	 * @param newUser The user that is to be added
	 * @param con The current connection to the database
	 * @return True if successful and false if not successful
	 * @throws SQLException Throws and prints the error if there is an issue with the database
	 */
	public static boolean addStudent (User currentUser, User newUser, Connection con) throws SQLException {
		// Check user privilege
    	if (currentUser.permissionCheck() <= 2) {
    		System.out.println("Permission level not high enough to perform this operation");
    		return false;
    	}
    	Statement stmt = null;
    	Statement stmt2 = null;
    	ResultSet users = null;
    	ResultSet modules = null;
        try {
        	//Check to see if the inputed username already exists, if they do, return false
            stmt = con.createStatement();
            String query = "SELECT Username " +
                    "FROM User " +
					"WHERE Username = " + newUser.getRegistrationNumber();
            users = stmt.executeQuery(query);
			if (users.next()){
				System.out.println("User already exists");
				return false;
			}
			
			// Find all compulsory modules for student at their level and degree
 			query = " SELECT Module_id FROM Degree_Module_Approved " +
					" WHERE Compulsory = '1' AND Degree_id = " + newUser.getDegreeId() + " AND Level = " + newUser.getLevel();
			modules = stmt.executeQuery(query);
            
			// Insert new Student into User and Student tables
            query = "INSERT INTO User " +
  		              "VALUES ( " + newUser.getRegistrationNumber() + ", " + newUser.getHash() + ", " + newUser.getTitle() + ", " + newUser.getSurname() +
  		              ", " + newUser.getOtherNames() + ", " + newUser.getRole() + ", " + newUser.getEmail() + ")";
            stmt2 = con.createStatement();
            stmt2.executeUpdate(query);
            query = "INSERT INTO Student " +
            		"VALUES (" + newUser.getRegistrationNumber() + ", " + newUser.getDegreeId() + ", " + newUser.getTutorName() + ", " + newUser.getLevel() +")" ;
            stmt2.executeUpdate(query);
            
            // Enrol student on all compulsory modules
            String moduleName = null;
            while (modules.next()) {
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

    private static int boolToInt(Boolean bool){
        if (bool){
            return 1;
        } else {
            return 0;
        }
    }

// Not currently using but leaving it in if we find it useful later for debugging purposes. Following is taken from oracle docs (I think it should be built into jdbc under JDBCTutorialUtilities however this is not working correctly on mine so someone else should check) - Robbie
//    public static void printSQLException(SQLException ex) {
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                if (ignoreSQLException(
//                        ((SQLException)e).
//                                getSQLState()) == false) {
//
//                    e.printStackTrace(System.err);
//                    System.err.println("SQLState: " +
//                            ((SQLException)e).getSQLState());
//
//                    System.err.println("Error Code: " +
//                            ((SQLException)e).getErrorCode());
//
//                    System.err.println("Message: " + e.getMessage());
//
//                    Throwable t = ex.getCause();
//                    while(t != null) {
//                        System.out.println("Cause: " + t);
//                        t = t.getCause();
//                    }
//                }
//            }
//        }
//    }
}
