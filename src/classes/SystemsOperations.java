package classes;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SystemsOperations {
	
    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\DELETING OPERATIONS\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */
	
    /**
     * @param currentUser The user that is currently logged in
     * @param departmentToDelete The department code of the department that is to be deleted
     * @param con Current connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteDepartment (User currentUser, String departmentToDelete, Connection con) throws SQLException {
	// use prepared statements to prevent sql injection
	PreparedStatement stmt = null;
        if (currentUser.permissionCheck() >= 4) {
            //Database is set up so that it will cascade and delete any data that relies on this
            stmt = con.prepareStatement("DELETE FROM Department WHERE Deparment_Code = ?");
	    stmt.setString(1, departmentToDelete);
	    stmt.executeUpdate();
        } else {
            System.out.println("Permission level not high enough to perform this operation");
        }

    }

    /**
     * @param currentUser The currently logged in user
     * @param degreeIdToDelete the ID of the degree that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteDegree (User currentUser, String degreeIdToDelete, Connection con) throws SQLException {
        PreparedStatement stmt = null;
        if (currentUser.permissionCheck() >= 4) {
            //Database is set up so that it will cascade and delete any data that relies on this
            stmt = con.prepareStatement("DELETE FROM Degree WHERE Degree_id = ?");
	    stmt.setString(1, degreeIdToDelete);
	    stmt.executeUpdate();
        } else {
            System.out.println("Permission level not high enough to perform this operation");
        }
    }

    /**
     * @param currentUser The currently logged in user
     * @param moduleId the ID of the module that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteModule (User currentUser, String moduleId, Connection con) throws SQLException {
	PreparedStatement stmt = null;
        try {
        	if (currentUser.permissionCheck() >= 4) {
	            //Database is set up so that it will cascade and delete any data that relies on this
            	    stmt = con.prepareStatement("DELETE FROM Modules WHERE Module_id = ?");
	            stmt.setString(1, moduleId);
	            stmt.executeUpdate();
    		} else {
        		System.out.println("Permission level not high enough to perform this operation");
        	}
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        } finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}		}
    }
   	
    /**	
     * 	
     * @param currentUser The currently logged in user	
     * @param delUser The user that is to be deleted
     * @param con The open connection to the database	
     * @throws SQLException Will print out the error with the database if one is encountered	
     */	
    public static void deleteUser (User currentUser, User delUser, Connection con) throws SQLException {
	PreparedStatement stmt = null;
        try {
        	if (currentUser.permissionCheck() >= 3) {
		    //Database is set up so that it will cascade and delete any data that relies on this
                    stmt = con.prepareStatement("DELETE FROM User WHERE Username = ?");
	            stmt.setString(1, delUser.getRegistrationNumber());
	            stmt.executeUpdate();
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
	PreparedStatement stmt = null;
        try {	
        	if (currentUser.permissionCheck() >= 3) {	
	            //Database is set up so that it will cascade and delete any data that relies on this
                    stmt = con.prepareStatement("DELETE FROM Student_Module WHERE Module_id = ? AND Username = ?");
	            stmt.setString(1, moduleId);
	            stmt.setString(2, userToRemove.getRegistrationNumber());
	            stmt.executeUpdate();
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
	PreparedStatement stmt = null;
        try {
        	if (currentUser.permissionCheck() >= 3) {	
	            //Database is set up so that it will cascade and delete any data that relies on this
                    stmt = con.prepareStatement("DELETE FROM Degree_Module_Approved WHERE Module_id = ?");
	            stmt.setString(1, moduleId);
	            stmt.executeUpdate();
    		} else {	
        		System.out.println("Permission level not high enough to perform this operation");	
        	}	
        } catch (SQLException e) {	
            e.printStackTrace(System.err);	
        }	
    }

    /**
     * @param currentUser The currently logged in user
     * @param userToDropFrom The user that we want to drop the module from
     * @param moduleId The id of the module we want to drop from the user
     * @param con The current connection to the database
     * @return True if it was dropped correctly, false if not
     * @throws SQLException Will throw an SQLException and return false if it encounters an error
     */
    public static boolean dropOptionalModuleFromUser(User currentUser, User userToDropFrom, String moduleId, Connection con) throws SQLException {
        //Check permissions of logged in user
        if (currentUser.permissionCheck() >= 3){
            try {
                return userToDropFrom.dropOptionalModule(moduleId, con);
            } catch (SQLException e){
                e.printStackTrace(System.err);
                return false;
            }
        }
        return false;
    }


    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\ADDING OPERATIONS\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */

    /**
     * @param currentUser The user that is currently logged in
     * @param departmentCode The code of the department that is being added
     * @param departmentName The name of the department that is being added
     * @param con Current connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void addDepartment (User currentUser, String departmentCode, String departmentName, Connection con) throws SQLException {
	PreparedStatement stmt = null;
        try {
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.prepareStatement("INSERT INTO Department VALUES (?, ?)");
	            stmt.setString(1, departmentCode);
		    stmt.setString(2, departmentName);
		    stmt.executeUpdate();
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
	PreparedStatement stmt = null;
	ResultSet rs = null;
        try {
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.prepareStatement("SELECT Department_Name FROM Deparment WHERE Deparment_Code = ?");
		    stmt.setString(1, departmentCode);
	            rs = stmt.executeQuery();
	            //Check if the department that was inputted exists
	            if (rs.next()) {
			stmt = con.prepareStatement("INSERT INTO Degree VALUES (?, ?, ?)");
			stmt.setString(1, degreeId);
			stmt.setString(2, degreeName);
			stmt.setString(3, departmentCode);
	                stmt.executeUpdate();
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
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}			try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}		}
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
	PreparedStatement stmt = null;
	ResultSet rs = null;
        try {
        	if (currentUser.permissionCheck() >= 4) {
	            stmt = con.prepareStatement("SELECT Degree_Name FROM Degree WHERE Degree_id = ?");
	            stmt.setString(1, degreeId);
	            rs = stmt.executeQuery();
	            //Check to see if the inputted department exists
	            if (rs.next()) {
	                //First insert into Modules
			stmt = con.prepareStatement("INSERT INTO Modules VALUES (?, ?, ?)");
			stmt.setString(1, moduleId);
			stmt.setString(2, moduleName);
			stmt.setInt(3, credits);
	                stmt.executeUpdate();
			
			stmt = con.prepareStatement("INSERT INTO Degree_Module_Approved VALUES (?, ?, ?, ?)");
			stmt.setString(1, degreeId);
			stmt.setString(2, Character.toString(level));
			stmt.setString(3, moduleId);
			stmt.setInt(4, boolToInt(compulsory));
	                stmt.executeUpdate();
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
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}}
    }

    public static boolean addModuleToDegree (User currentUser, String degreeId, char level, String moduleId, boolean compulsory, Connection con) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        try {
            if (currentUser.permissionCheck() >= 4) {
                stmt = con.createStatement();
                String query = "SELECT Degree_Name " +
                        "FROM Degree " +
                        "WHERE Degree_id = '" + degreeId + "'";
                rs = stmt.executeQuery(query);
                //Check to see if the inputted department exists
                if (rs.next()) {
                    stmt.executeUpdate(query);
                    query = "INSERT INTO Degree_Module_Approved " +
                            "VALUES ( '" + degreeId + "', '" + level + "', '" + moduleId + "', '" + boolToInt(compulsory) + "')";
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
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}}
        }
    /**
     * @param currentUser The currently logged in user
     * @param userToAddTo The user we want to add the module to
     * @param moduleId The module we want to add to the specified user
     * @param con The currently open connection to the database
     * @return True if it was successful, false if not
     * @throws SQLException Will throw and return false if there is an issue with the database
     */
    public static boolean addOptionalModuleToUser(User currentUser, User userToAddTo, String moduleId, Connection con) throws SQLException {
        //Check permissions of logged in user
        if (currentUser.permissionCheck() >= 3){
            try {
                return userToAddTo.addOptionalModule(moduleId, con);
            } catch (SQLException e){
                e.printStackTrace(System.err);
                return false;
            }
        }
        return false;
    }

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
            //Check to see if the inputted username already exists, if they do, return false
            stmt = con.createStatement();
            String query = "SELECT Username " +
                    "FROM User " +
                    "WHERE Username = '" + newUser.getRegistrationNumber() + "'";
            users = stmt.executeQuery(query);
            if (users.next()){
                System.out.println("User already exists");
                return false;
            }

            // Find all compulsory modules for student at their level and degree
            query = " SELECT Module_id FROM Degree_Module_Approved " +
                    " WHERE Compulsory = '1' AND Degree_id = '" + newUser.getDegreeId() + "' AND Level = '" + newUser.getLevel() + "'";
            modules = stmt.executeQuery(query);

            // Insert new Student into User and Student tables
            query = "INSERT INTO User " +
                    "VALUES ( '" + newUser.getRegistrationNumber() + "', '" + newUser.getHash() + "', '" + newUser.getTitle() + "', '" + newUser.getSurname() +
                    "', '" + newUser.getOtherNames() + "', '" + newUser.getRole() + "', '" + newUser.getEmail() + "')";
            stmt2 = con.createStatement();
            stmt2.executeUpdate(query);
            query = "INSERT INTO Student " +
                    "VALUES ('" + newUser.getRegistrationNumber() + "', '" + newUser.getDegreeId() + "', '" + newUser.getTutorName() + "', '" + newUser.getLevel() +" ')" ;
            stmt2.executeUpdate(query);

            // Enrol student on all compulsory modules
            String moduleName;
            while (modules.next()) {
                moduleName = "'" + modules.getString(1) + "'";
                query = "INSERT INTO Student_Module " +
                        "VALUES ( '" + newUser.getRegistrationNumber() + "', '" + moduleName + "', '0')";
                stmt2.execute(query);
            }
            return true;
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return false;
        } finally {
            // Close all open resources
            try { if (users != null) users.close(); } catch (Exception e) {e.printStackTrace(System.err);}			try { if (modules != null) modules.close(); } catch (Exception e) {e.printStackTrace(System.err);}			try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}			try { if (stmt2 != null) stmt2.close(); } catch (Exception e) {e.printStackTrace(System.err);}		}
    }

    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\GETTING OPERATIONS\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */
    /**
     *
     * @param usernameInput The username of the user we want to return
     * @param hashInput The hashed password of the user we want to return
     * @return user from given or null if no such user
     * @throws SQLException if error with the database, should still return null
     */
    public static User getUser(String usernameInput, String hashInput) throws SQLException { //will have password hash if that gets done
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            try {
                Statement stmt = con.createStatement();
                ResultSet rs;

                String query = "SELECT * FROM User " +
                        "WHERE Username = '" + usernameInput +
                        "' AND Hash = '" + hashInput +"'";

                rs = stmt.executeQuery(query);
                if(rs.next()) {
                    String username = rs.getString("Username");
                    String hash = rs.getString("Hash");
                    String title = rs.getString("Title");
                    String surname = rs.getString("Surname");
                    String otherNames = rs.getString("Other_names");
                    String role = rs.getString("Role");
                    String email = rs.getString("Email");
                    return new User(username, hash, title, surname, otherNames, role, email);
                }else{
                    return null;
                }
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

    public static ArrayList<Department> getDept() throws SQLException{
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            try {
                ArrayList<Department> depts = new ArrayList<>();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Department");
                while (rs.next()) {
                    String id = rs.getString("Department_Code");
                    String name = rs.getString("Department_Name");
                    depts.add(new Department(id, name));
                }
                con.close();
                return depts;
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

    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\ MISC \\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */
    /**
    
	 * 
	 * @param currentUser The currently logged in user
	 * @param newUser The user that is to be added
	 * @param con The current connection to the database
	 * @return True if successful and false if not successful
	 * @throws SQLException Throws and prints the error if there is an issue with the database
	 */
	public static boolean addUser (User currentUser, User newUser, Connection con) throws SQLException {
		// Check user privilege
		if ((currentUser.permissionCheck() <= newUser.permissionCheck()) || (currentUser.permissionCheck() <= 2)) {
				System.out.println("Permission level not high enough to create a user of this permission level");
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
			
			// Insert new User into User tables
            query = "INSERT INTO User " +
  		              "VALUES ( " + newUser.getRegistrationNumber() + ", " + newUser.getHash() + ", " + newUser.getTitle() + ", " + newUser.getSurname() +
  		              ", " + newUser.getOtherNames() + ", " + newUser.getRole() + ", " + newUser.getEmail() + ")";
            stmt2 = con.createStatement();
            stmt2.executeUpdate(query);
			
			// If user is a student, perform student specific actions
			if (newUser.getRole().equals("'Student'")) {
				// Find all compulsory modules for student at their level and degree
	 			query = " SELECT Module_id FROM Degree_Module_Approved " +
						" WHERE Compulsory = '1' AND Degree_id = " + newUser.getDegreeId() + " AND Level = " + newUser.getLevel();
				modules = stmt.executeQuery(query);
				
				// Insert Student into student table
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
	
	/**
	 * 
	 * @param current 
	 * @param student
	 * @param con
	 * @return
	 */
	public static boolean giveCompModules(User current, User student, Connection con) {
		if (student.permissionCheck() == 1 && current.permissionCheck() >= 2) {
			Statement stmt = null;
			Statement stmt2 = null;
			ResultSet modules  = null; 
			String moduleName = null;
			try {
				stmt = con.createStatement();
				String query = "SELECT Module_id FROM Degree_Module_Approved " +
						"WHERE Compulsory = '1' AND Degree_id = " + student.getDegreeId() + " AND Level = " + student.getLevel();
				modules = stmt.executeQuery(query);
				
				stmt2 = con.createStatement();
				while (modules.next()) {
					moduleName = "'" + modules.getString(1) + "'";
	            	query = "INSERT INTO Student_Module " +
	            			"VALUES ( " + student.getRegistrationNumber() + ", " + moduleName + ", '0')";
	            	stmt2.execute(query);
					
				}
				return true;
				
			} catch (SQLException e){
		        e.printStackTrace(System.err);
		        return false;
		    } finally {
		        try { if (modules != null) modules.close(); } catch (Exception e) {}
		        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		        try { if (stmt2 != null) stmt2.close(); } catch (Exception e) {}
		    }
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param currentUser
	 * @param student
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean canProgressStudent (User currentUser, User student, Connection con) throws SQLException {
		if ((currentUser.permissionCheck() >= 2) && (student.permissionCheck() <= 1)) {
			System.out.println(student.calculateMeanGrade(con, student.getLevel()));
			if ((student.getLevel() <= '3') && (student.calculateMeanGrade(con, student.getLevel()) >= 40)) {
				//allowed to continue = true
				return true;
			}
			if ((student.getLevel() == '4') && (student.calculateMeanGrade(con, student.getLevel()) >= 50)) {
				//allowed to continue = true
				return true;
			}
			if (student.getLevel() == 'P') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param currentUser
	 * @param student
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean progressStudents(User currentUser, User student, Connection con) throws SQLException {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			
			if (canProgressStudent(currentUser, student, con)) {
				student.increaseLevel();
				// change students level in database
				String query = "UPDATE Student SET " +
						" Level = '" + student.getLevel() + "'" +
						" WHERE Username = " + student.getRegistrationNumber();
				stmt.executeUpdate(query);
				// give the student new compulsory modules
				giveCompModules(currentUser, student, con);
			}
		} catch (SQLException e){
	        e.printStackTrace(System.err);
	        return false;
	    } finally {
	        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
	    }
		return false;
		
	}

    public static String graduateUser(User currentUser, User userToGraduate, Connection con) throws SQLException {
        try {
            if (currentUser.permissionCheck() <= 2) {
                System.out.println("Permission level not high enough to perform this operation");
                return "This user cannot do this";
            }
            return userToGraduate.graduate(con);
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return "Error encountered";
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
     * @param currentUser The currently logged in user
     * @param userToUpdate The user that we want to update the grades for
     * @param moduleId The module that we are updating the grade for
     * @param resit Specifies whether it is a resit and therefore needs to be capped at 40% (0 if not resit, 1 if it is. Resit years are not capped afaik)
     * @param grade The grade the user obtained
     * @param con The currently open connection to the database
     * @return Returns true if it is successful and false if it is not
     * @throws SQLException Throws and prints the error if there is an issue with the database
     */
    public static boolean updateGrades (User currentUser, User userToUpdate, String moduleId, Boolean resit, int grade, Connection con) throws SQLException {
        try {
            //check user privilege
            //Lazy operator so will only run the update grades if the privilege check passes
            return currentUser.permissionCheck() >= 2 && userToUpdate.updateGrades(moduleId, resit, grade, con);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
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
