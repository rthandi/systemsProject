package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class SystemsOperations {
    /**
     * @param user The currently user that you want to check the total credits for
     * @param con The current connection to the sql database
     * @return true if they have the right amount of credits and false if they don't
     * @throws SQLException If error with database then will print the error and return false
     */
    public static boolean checkTotalCredits(User user, Connection con) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT Credits " +
                    "FROM Student_Module " +
                    "WHERE Username = " + user.getRegistrationNumber() +
                    " INNER JOIN Modules " +
                    " ON Student_Module.Module_id = Modules.Module_id";
            //Insert sql query to get the modules that the user is doing
            ResultSet rs = stmt.executeQuery(query);
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
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 4) { 
	            Statement stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Department " +
	                    "WHERE Department_Code = " + departmentToDelete;
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
     * @param degreeIdToDelete The ID of the degree that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteDegree (User currentUser, String degreeIdToDelete, Connection con) throws SQLException{
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 4) {
	            Statement stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Degree " +
	                    "WHERE Degree_id = " + degreeIdToDelete;
	            stmt.executeUpdate(query);
    		} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * @param currentUser The currently logged in user
     * @param moduleId The ID of the module that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteModule (User currentUser, String moduleId, Connection con) throws SQLException {
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 4) {
	            Statement stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Modules " +
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
     * 
     * @param currentUser The currently logged in user
     * @param username The ID of the user that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteUser (User currentUser, String username, Connection con) throws SQLException {
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 3
        	if (permissionCheck(currentUser) >= 3) {
	            Statement stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM User " +
	                    "WHERE Username = " + username;
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
     * @param username The ID of the user that is to be deleted
     * @param moduleId The ID of the module that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteStudentModule (User currentUser, String username, String moduleId, Connection con) throws SQLException {
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 3) {
	            Statement stmt = con.createStatement();
	            //If database is setup correctly this should cascade and delete any mentions of this department
	            String query = "DELETE FROM Student_Module " +
	                    "WHERE Module_id = " + moduleId + " AND Username = " + username;
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
        	if (permissionCheck(currentUser) >= 3) {
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
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 4) {
	            Statement stmt = con.createStatement();
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
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4 
        	if (permissionCheck(currentUser) >= 4) {
	            Statement stmt = con.createStatement();
	            String query = "SELECT  Department_Name " +
	                    "FROM Department " +
	                    "WHERE Department_Code = " + departmentCode;
	            ResultSet rs = stmt.executeQuery(query);
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
        }
    }

    public static boolean addModule  (User currentUser, String moduleId, String moduleName, int credits, char level, boolean compulsory, String degreeId, Connection con) throws SQLException {
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 4
        	if (permissionCheck(currentUser) >= 4) {
	            Statement stmt = con.createStatement();
	            String query = "SELECT Degree_Name " +
	                    "FROM Degree " +
	                    "WHERE Degree_id = " + degreeId;
	            ResultSet rs = stmt.executeQuery(query);
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
        }
    }
    
    /** Commented out as other things need to be created first
     * 
     * @param currentUser The currently logged in user
     * @param username For the new student
     * @param hash Password hash for user
     * @param surname Last name of user
     * @param other names
     * @param role Student/Teacher/Registrar/Administrator
     * @param degreeId The ID of the degree to enrol the user onto
     * @param email Users email
     * @param tutor Student tutor
     * @param con The current connection to the database
     * @return boolean if user addition was a success or not
     * @throws SQLException
     */
     
    public static boolean addUser (User currentUser, String username, String hash, String surname, String other, 
    		String role, String degreeId, String email, String tutor, Connection con) throws SQLException {
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 3
        	if (permissionCheck(currentUser) >= 3) {
	            Statement stmt = con.createStatement();
	            String query = "SELECT Username " +
	                    "FROM User ";
	            ResultSet rs = stmt.executeQuery(query);
	            //Check to see if the inputed username already exists
	            if (rs.next()) {
	            	System.out.println("A user with that username already exists");
	                return false;
	            } 
	            else {
	            	query = "INSERT INTO User " +
	  		              "VALUES ( " + username + ", " + hash + ", " + surname + ", " + other + ", " + role + ", " + degreeId+ ", " + email + ", " + tutor + ")";
	                stmt.executeUpdate(query);
	                
	                query = " SELECT Module_id FROM Degree_Module_Approved " +
	                		" WHERE Compulsory = 'True' AND Degree_id = " + degreeId;
	                rs = stmt.executeQuery(query);
	                ResultSetMetaData rsmd = rs.getMetaData();
	                int numModules = rsmd.getColumnCount();
	                
	                for (int i=0; i<=numModules; i++) {
	                	query = "INSERT INTO Student_Module " +
	                			"VALUES ( " + username + ", " + rs.getString(i) + ")";
	                	stmt.execute(query);
	                }
	            	return true;
	            }
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        		return false;
        	}
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return false;
        }
    } 

    private static int boolToInt(Boolean bool){
        if (bool){
            return 1;
        } else {
            return 0;
        }
    }
    
    /**
     * 
     * @param user
     * @return permission level of the user as an int
     */
    public static int permissionCheck (User currentUser) {
    	String permission = currentUser.getRole();
    	int level = 0;
    	if (permission == "Student")
    		level = 1;
    	else if (permission == "Teacher")
    		level = 2;
    	else if (permission == "Registrar")
    		level = 3;
    	else if (permission == "Administrator")
    		level = 4;
    	return level;
    }
    
    /**
	 * Method to take a table for it to be output ot the screen
	 * @param con Current connection to the database
	 * @param table To be output
	 * @param currentUser the User that is currently logged in
	 * @return dynamically outputs data from the table
	 */
	public static void retrieveTable (Connection con, User currentUser, String table) {
		try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet - 3
        	if (permissionCheck(currentUser) >= 3) { 
	            Statement stmt = con.createStatement();
	            String query = "SELECT * FROM " + table;
	            
	            
	            ResultSet res = stmt.executeQuery(query);
	            ResultSetMetaData rsmd  = res.getMetaData();
	    		int numCols = rsmd.getColumnCount();
	    		
	    		while (res.next()) {
	    			for (int i=1; i<=numCols; i++) {
	    				System.out.print(rsmd.getColumnName(i) + ": ");
	    				System.out.print(res.getString(i) + "  ");
	    			}
	    			System.out.println("");
	    		}
        	} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
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
