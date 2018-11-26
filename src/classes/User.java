package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    // This is a class that will be used to represent the data of a user as an object once it has been fetched from the database

    // USER DATA

    private String registrationNumber;
    private String hash;
    private String title;
    private String surname;
    private String otherNames;
    private String role;
    private String degreeId;
    private String email;
    private String tutorName;
    private char level;

    //CONSTRUCTORS
    //Student
    public User (String registrationNumberInput, String hashInput, String titleInput, String surnameInput, String otherNamesInput, String roleInput, String degreeIdInput, String emailInput, String tutorNameInput, char levelInput){
        registrationNumber = registrationNumberInput;
        hash = hashInput;
        title = titleInput;
        surname = surnameInput;
        otherNames = otherNamesInput;
        role = roleInput;
        degreeId = degreeIdInput;
        email = emailInput;
        tutorName = tutorNameInput;
        level = levelInput;
    }

    //non student
    public User (String usernameInput, String hashInput, String titleInput, String surnameInput, String otherNamesInput, String roleInput, String emailInput){
        registrationNumber = usernameInput;
        hash = hashInput;
        title = titleInput;
        surname = surnameInput;
        otherNames = otherNamesInput;
        role = roleInput;
        email = emailInput;

    }
    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\GETTING METHODS\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */
    public String getDegreeId() {
        return degreeId;
    }

    public String getEmail() {
        return email;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public String getRole() {
    	return role;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getHash() {
    	return hash;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public String getTutorName() {
        return tutorName;
    }

    public char getLevel() { return level; }

    public String getFullName(){
        return (title + " " + otherNames + " " + surname);
    }


    /**
     * @param con The current connection to the sql database
     * @return true if they have the right amount of credits and false if they don't
     * @throws SQLException If error with database then will print the error and return false
     */
    private int getTotalCredits(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
//            TODO: Apparently Credits cannot be found need to fix this at some point
            String query = "SELECT Credits " +
                    "FROM Student_Module " +
                    " INNER JOIN Modules " +
                    " ON Student_Module.Module_id = Modules.Module_id " +
                    "WHERE Username = '" + this.getRegistrationNumber() + "'";
            //Insert sql query to get the modules that the user is doing
            rs = stmt.executeQuery(query);
            // Iterate over the ResultSet to total up the credits
            int counter = 0;
            while (rs.next()) {
                counter += rs.getInt("Credits");
            }
            return counter;
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
            return -1;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}		}
    }


    private boolean validateTotalCreditsCorrect(int numberOfCredits) {
        String level = this.getDegreeId().substring(3, 4);
        //If U then it is undergrad
        if (Objects.equals(level, "U")) {
            return numberOfCredits == 120;
        } else {
            return numberOfCredits == 180;
        }
    }

    private boolean validateTotalCreditsUnder(int numberOfCredits){
        String level = this.getDegreeId().substring(3, 4);
        //If U then it is undergrad
        if (Objects.equals(level, "U")) {
            return numberOfCredits <= 120;
        } else {
            return numberOfCredits <= 180;
        }
    }

    public boolean checkTotalCredits(Connection con) throws SQLException {
        try{
            return this.validateTotalCreditsCorrect(this.getTotalCredits(con));
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
            return false;
        }
    }


    /**
     * @param con The current connection to the database
     * @return true if the user is registered correctly for their current year false if not
     * @throws SQLException Will throw and return false if there is an error with the database connection
     */
    public boolean checkRegValid(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rsModules = null;
        ResultSet rsModulesApproved = null;
        try {
            //check if the total number of credits is correct
            if (!checkTotalCredits(con)){
                return false;
            }
            //Now we get the modules and check that they are all approved
            stmt = con.createStatement();
            String query = "SELECT Module_id " +
                    "FROM Student_Module " +
                    "WHERE Username = " + this.getRegistrationNumber();
            rsModules = stmt.executeQuery(query);
            query = "SELECT Module_id, compulsory " +
                    "FROM Degree_Module_Approved " +
                    "WHERE Degree_id = " + this.getDegreeId() + " AND Level = " + this.getLevel();
            rsModulesApproved = stmt.executeQuery(query);
            //Now we have the data we need to put it into a data structure that is easier to use
            ArrayList<String> moduleArray = new ArrayList<>();
            ArrayList<String> moduleArrayCompulsory = new ArrayList<>();
            ArrayList<String> moduleArrayOptional = new ArrayList<>();
            while(rsModules.next()){
                moduleArray.add(rsModules.getString("Module_id"));
            }
            while(rsModulesApproved.next()){
                //may need to change this too boolean but pretty sure it will work like this
                if (rsModulesApproved.getInt("Compulsory") == 1){
                    moduleArrayCompulsory.add(rsModulesApproved.getString("Module_id"));
                } else {
                    moduleArrayOptional.add(rsModulesApproved.getString("Module_id"));
                }
            }
            //Now we need to check that the student has all of the compulsory modules
            for (String currentModule :
                    moduleArrayCompulsory) {
                if (!moduleArray.contains(currentModule)) {
                    return false;
                }
                else {
                    moduleArray.remove(currentModule);
                }
            }
            //Now we need to check that the remaining modules are possible compulsory modules
            while (!moduleArray.isEmpty()){
                if (!moduleArrayOptional.contains(moduleArray.get(0))){
                    return false;
                }
                else {
                    moduleArray.remove(0);
                }
            }
            return true;
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rsModules != null) rsModules.close(); } catch (Exception e) {}
            try { if (rsModulesApproved != null) rsModulesApproved.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        }
    }
    
    /**
     * 
     * @param con The current connection to the database
     * @return The total mean grade for the current period of study
     * @throws SQLException will throw if an SQL error is encountered
     */
    public int calculateMeanGrade(Connection con) throws SQLException{
        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        ResultSet modules = null;
        ResultSet studentModules = null;
        ResultSet credits = null;
        ArrayList<String> moduleArray = new ArrayList<>();
        int total = 0;
        try {
        	stmt = con.createStatement();
        	String query = "SELECT Module_id FROM Degree_Module_Approved " +
        				   "WHERE Level = " + this.getLevel() + " AND Degree_id = " + this.getDegreeId();
        	modules = stmt.executeQuery(query);
        	while (modules.next()) {
        		moduleArray.add(modules.getString("Module_id"));
        	}
        	
        	stmt2 = con.createStatement();
        	query = "SELECT * FROM Student_Module " +
        			"WHERE Username = " + this.getRegistrationNumber();
        	studentModules = stmt2.executeQuery(query);
        	
        	while (studentModules.next()) {
        		System.out.println(studentModules.getString("Module_id"));
        		if (moduleArray.contains(studentModules.getString("Module_id"))) {
        			stmt3 = con.createStatement();
        			query = "SELECT Credits FROM Modules " +
        					"WHERE Module_id = '" + studentModules.getString("Module_id") + "'";
        			credits = stmt3.executeQuery(query);
        			while (credits.next()) {
        				//System.out.println(credits.getInt("Credits"));
        				total += (studentModules.getInt("Mark") * (credits.getFloat("Credits")/120));
        				System.out.println(total);
        		//		return total;
        			}
        		}
        	}
        	return total;
        } catch (SQLException e){
            e.printStackTrace(System.err);
            return (total);
        } finally {
            try { if (modules != null) modules.close(); } catch (Exception e) {}
            try { if (studentModules != null) modules.close(); } catch (Exception e) {}
            try { if (credits != null) modules.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (stmt2 != null) stmt.close(); } catch (Exception e) {}
            try { if (stmt3 != null) stmt.close(); } catch (Exception e) {}
        }
    }

    /**
     * @return An int value of the permissions of the User
     */
    public int permissionCheck() {
        String permission = this.getRole();
        int level = 0;
        if (permission.equals("Student"))
            level = 1;
        else if (permission.equals("Teacher"))
            level = 2;
        else if (permission.equals("Registrar"))
            level = 3;
        else if (permission.equals("Administrator"))
            level = 4;
        return level;
    }

    /**
     * @param moduleId The id of the module that is being added
     * @param con The currently open connection to the database
     * @return Returns true if the operation is successful, false if it isn't
     * @throws SQLException Will throw and return false if there is an issue with the database
     */
    public boolean addOptionalModule(String moduleId, Connection con) throws SQLException {
        //Check that the module is a valid optional module for the user
        ResultSet rs = null;
        Statement stmt = null;
        try {
            int totalCredits = this.getTotalCredits(con);
            //First we need to check the total number of credits to see if we can add another module
            if (this.validateTotalCreditsUnder(totalCredits)){
                System.out.println("Already at maximum credit count or over");
                return false;
            }
            //Now we fetch the details of the module
            stmt = con.createStatement();
            String query = "SELECT Modules.Module_id, Credits " +
                    "FROM Degree_Module_Approved " +
                    //Checking for degree, optional, and level so we can confirm all of these are correct all in one go when we check if rs.next exists
                    "INNER JOIN Modules " +
                    "ON Degree_Module_Approved.Module_id = Modules.Module_id " +
                    "WHERE Modules.Module_id = '" + moduleId + "' AND Degree_id = '" + this.getDegreeId() + "' AND Compulsory = '0' AND Level = '" + this.getLevel() + "'";
            rs = stmt.executeQuery(query);
            //Check that module exists
            if (rs.next()){
                //check that it wouldn't take the credit number over the limit
                totalCredits += rs.getInt("Credits");
                if(this.validateTotalCreditsUnder(totalCredits)){
                    stmt.close();
                    stmt = con.createStatement();
                    query = "INSERT INTO Student_Module " +
                            "VALUES ('" + this.getRegistrationNumber() + "', '" + moduleId + "', '0')";
                    stmt.executeQuery(query);
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}}
    }

    /**
     * @param moduleId The id of the module that is being dropped
     * @param con The currently open connection to the database
     * @return Returns true if the operation is successful, false if it isn't
     * @throws SQLException Will throw and return false if there is an issue with the database
     */
    public boolean dropOptionalModule(String moduleId, Connection con) throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            //Need to check that the module is an optional module
            String query = "SELECT Module_id " +
                    "FROM Degree_Module_Approved " +
                    "WHERE Degree_id = '" + this.getDegreeId() + "' AND Compulsory = '0' AND Module_id = '" + moduleId + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()){
                stmt.close();
                stmt = con.createStatement();
                query = "DELETE FROM Student_Module " +
                        "WHERE Module_id = '" + moduleId + "' AND Username = '" + this.getRegistrationNumber() + "'";
                stmt.executeQuery(query);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}}
    }
}
