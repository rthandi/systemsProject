package classes;

import javax.print.DocFlavor;

import java.sql.Connection;
import java.sql.DriverManager;
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

        degreeId = null;
        tutorName = null;
        level = ' ';

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
    
    // Setter method
    public String setRole(String newRole) {
    	role = newRole;
    	return role;
    }


    /* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\SETTER METHODS\\\\\\\\\\\\\\\
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ */
    public void setDegreeId(String degId){
        degreeId = degId;
    }

    public void setTutor(String tutorNameInput){
        tutorName = tutorNameInput;
    }

    public void setLevel(char levelInput){
        level = levelInput;
    }

    /**
     * Method to increase the level of a student
     * @return increased student level
     */
    public char increaseLevel() {
    	if (degreeId.substring(4, 6).equals("U1")) {
    		if (level == '2') {
    			level = 'P';
    			return level;
    		}
    		else if (level == 'P') {
    			level = '3';
    			return level;
    		}
    		else {
    			level = (char)((int)level + 1);
    			return level;
    		}
    	}
    	else if (degreeId.substring(4, 6).equals("P1")) {
    		if (level == '3') {
    			level = 'P';
    			return level;
    		}
    		else if (level == 'P') {
    			level = '4';
    			return level;
    		}
    		else {
    			level = (char)((int)level + 1);
    			return level;
    		}
    	}
    	else {
			level = (char)((int)level + 1);
			return level;
		}
    }


    /**
     * Checks if a student has the correct number of credits for their studies
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
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}		
            }   
    }



    /**
     * Validate the number of credits a student has
     * @param numberOfCredits The number of Credits a student has
     * @return True if valid, False if not
     */
    private boolean validateTotalCreditsCorrect(int numberOfCredits) {
        char level = this.getLevel();
        //If 1, 2, or 3 then it is 120 credits
        if (level == 1 || level == 2 || level ==  3) {
            return numberOfCredits == 120;
        } else {
            return numberOfCredits == 180;
        }
    }

    /**
     * Validate the number of credits an Undergraduate student has
     * @param numberOfCredits The number of Credits a student has
     * @return True if valid, False if not
     */
    private boolean validateTotalCreditsUnder(int numberOfCredits){
        char level = this.getLevel();
        //If 1, 2, or 3 then it is 120 credits
        if (level == 1 || level == 2 || level ==  3) {
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
     * Check to see if a Student is registered for their degree and current year correctly
     * @param con The current connection to the database
     * @return True if the user is registered correctly for their current year, False if not
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
     * Calculates the weighted mean grade a student has obtained in a given level of study
     * @param con The current connection to the database
     * @return The total mean grade for the current period of study
     * @throws SQLException will throw if an SQL error is encountered
     */
    public int calculateMeanGrade(Connection con, char inpLevel) throws SQLException{
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
        				   "WHERE Level = " + inpLevel + " AND Degree_id = '" + this.getDegreeId()+"'";
        	modules = stmt.executeQuery(query);
        	while (modules.next()) {
        		moduleArray.add(modules.getString("Module_id"));
        	}
        	
        	stmt2 = con.createStatement();
        	query = "SELECT * FROM Student_Module " +
        			"WHERE Username = '" + this.getRegistrationNumber()+"'";
        	studentModules = stmt2.executeQuery(query);
        	
        	while (studentModules.next()) {
        		if (moduleArray.contains(studentModules.getString("Module_id"))) {
        			stmt3 = con.createStatement();
        			query = "SELECT Credits FROM Modules " +
        					"WHERE Module_id = '" + studentModules.getString("Module_id") + "'";
        			credits = stmt3.executeQuery(query);
        			while (credits.next()) {
        				total += (studentModules.getInt("Mark") * (credits.getFloat("Credits")/120));
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
     * Checks the role of a user and gives them a permission level based on that
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
     * Gives a student an optional module
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
                    stmt.executeUpdate(query);
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        }
    }

    /**
     * Removes an optional module from a student
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
                stmt.executeUpdate(query);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        }
    }

    /**
     * Change the grade a Student has obtained for a given module
     * @param moduleId The id of the module we are updating
     * @param resit Specifies whether it is a resit and therefore needs to be capped at 40% (0 if not resit, 1 if it is. Resit years are not capped afaik)
     * @param grade The grade the user obtained
     * @param con The currently open connection to the database
     * @return True if it is successful, false if it is not
     * @throws SQLException Throws and prints the error if there is an issue with the database
     */
    public boolean updateGrades(String moduleId, Boolean resit, int grade, Connection con) throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            //Check the user is taking the module
            stmt = con.createStatement();
            String query = "SELECT Module_id " +
                    "FROM Student_Module " +
                    "WHERE Module_id = '" + moduleId + "' AND Username = '" + this.getRegistrationNumber() + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()){
                //Check if it is a resit or not so we can cap at 40% (year 1-3) and 50% (year 4)
            	if (this.getLevel() == '4') {
		            if (resit && grade > 50){
		                grade = 50;
		            }
            	}
            	else {
            		if (resit && grade > 40) {
            			grade = 40;
            		}
            	}
                stmt.close();
                stmt = con.createStatement();
                query = "UPDATE Student_Module " +
                        "SET Mark = '" + grade + "' " +
                        "WHERE Module_id = '" + moduleId + "' AND Username = '" + this.getRegistrationNumber() + "'";
                stmt.executeUpdate(query);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        }
    }

    /**
     * Calculate what class degree a student has obtained through their studies
     * @param con The currently open connection to the database
     * @return The level that the student graduates with
     * @throws SQLException Throws and prints the error if there is an issue with the database
     */
    public String graduate(Connection con) throws SQLException {
        ResultSet rs = null;
        ResultSet checkResit = null;
        Statement stmt = null;
        Statement resit = null;
        
        try {
            String output;
            char level = this.getLevel();
            //Check if one year MSc
            if (Objects.equals(this.getDegreeId().substring(3, 4), "P")) {
                //Check if dissertation was passed
                stmt = con.createStatement();
                String query = "SELECT Student_Module.Mark, Credits " +
                        "FROM Student_Module " +
                        "INNER JOIN Modules " +
                        "ON Student_Module.Module_id = Modules.Module_id " +
                        "WHERE Student_Module.Username = '" + this.getRegistrationNumber() + "'";
                rs = stmt.executeQuery(query);
                boolean passedDissertation = true;
                ArrayList<StudentModsGrades> gradeList = new ArrayList<>();
                while (rs.next()){
                    if (rs.getInt("Credits") == 60){
                        if(rs.getInt("Mark") <= 49.5){
                            passedDissertation = false;
                        } else {
                            gradeList.add(new StudentModsGrades(rs.getInt("Mark"), rs.getInt("Credits")));
                        }
                    }
                }
                if (!passedDissertation){
                    //dissertation failed. Check to see if they qualify for a PGDip
                    int amountOfCreditsPassed = 0;
                    boolean concededUsed = false;
                    for (StudentModsGrades aGradeList : gradeList) {
                        if (aGradeList.getStudentsMarks() >= 49.5) {
                            //Module passed add credits to total
                            amountOfCreditsPassed += aGradeList.getModuleCredits();
                        } else if (!concededUsed && aGradeList.getStudentsMarks()>= 39.5){
                            amountOfCreditsPassed += aGradeList.getModuleCredits();
                            concededUsed = true;
                        }
                    }
                    if (amountOfCreditsPassed == 120){
                        output = "Failed dissertation, PGDip awarded";
                    } else {
                        output = "fail";
                    }
                } else {
                    int total = this.calculateMeanGrade(con, 'P');
                    //Calculate degree class
                    if (total >= 69.5) {
                        output = "Distinction";
                    } else if (total >= 59.5) {
                        output = "Merit";
                    } else if (total >= 49.5) {
                        output = "Pass";
                    } else {
                        //Failed, check for PGCert
                        int amountOfCreditsPassed = 0;
                        for (StudentModsGrades aGradeList : gradeList) {
                            if (aGradeList.getStudentsMarks() >= 49.5) {
                                //Module passed add credits to total
                                amountOfCreditsPassed += aGradeList.getModuleCredits();
                            }
                        }
                        if (amountOfCreditsPassed >= 60) {
                            output = "Qualified for PGCert";
                        } else {
                            output = "Failed";
                        }
                    }
                }
            } else {
                int secondYearMark = this.calculateMeanGrade(con, '2');
                int thirdYearMark = this.calculateMeanGrade(con, '3');
                //Check current level to see if masters or undergraduate (may be better way of doing this lemme know)
                if (level == '3') {
                    //calculate weighted total grade
                    int total = ((secondYearMark + (thirdYearMark * 2)) / 3);
                    //Calculate degree class
                    //If student had to re-sit third year, achieve a pass
                    resit = con.createStatement();
                    String query = "SELECT Resit FROM Student WHERE Username = '" + this.getRegistrationNumber() + "'";
                    checkResit = resit.executeQuery(query);
                    if (checkResit.next()) {
                    	if ((checkResit.getInt("Resit") == 3) & (total >= 39.5)){
                    		output = "Pass (non-honours)";
                    	} else {
                    		if (total >= 69.5) {
                                output = "First class";
                            } else if (total >= 59.5) {
                                output = "Upper second";
                            } else if (total >= 49.5) {
                                output = "Lower second";
                            } else if (total >= 44.5) {
                                output = "Third class";
                            } else if (total >= 39.5) {
                                output = "Pass (non-honours)";
                            } else {
                                output = "fail";
                            }
                    	}
                    } else {
                    	output = "fail";
                    }
                    
                } else if (level == '4') {
                    int fourthYearMark = this.calculateMeanGrade(con, '4');
                    int total = ((secondYearMark + (thirdYearMark * 2) + (fourthYearMark * 2)) / 5);
                    //Calculate degree class
                    if (total >= 69.5) {
                        output = "First class";
                    } else if (total >= 59.5) {
                        output = "Upper second";
                    } else if (total >= 49.5) {
                        output = "Lower second";
                    } else {
                        //They graduate with a bachelor's
                        //calculate weighted total grade
                        total = ((secondYearMark + (thirdYearMark * 2)) / 3);
                        //Calculate degree class
                        if (total >= 69.5) {
                            output = "Failed fourth level: graduate with bachelor's first class";
                        } else if (total >= 59.5) {
                            output = "Failed fourth level: graduate with bachelor's upper second";
                        } else if (total >= 49.5) {
                            output = "Failed fourth level: graduate with bachelor's lower second";
                        } else if (total >= 44.5) {
                            output = "Failed fourth level: graduate with bachelor's third class";
                        } else if (total >= 39.5) {
                            output = "Failed fourth level: graduate with bachelor's pass (non-honours)";
                        } else {
                            output = "fail";
                        }
                    }
                } else {
                    output = "Not yet ready for graduation";
                }
            }
            return output;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return "Error encountered";
        } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        }
    }

    /**
     * Makes a user into a student if it is possible, if not nothing changes
     * @return student version of the user
     */
    public void toStudent(Connection con) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            String query = "SELECT * FROM Student" +
                    " WHERE Username = '" + getRegistrationNumber()+"'";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                String degreeId = rs.getString("Degree_id");
                String tutor = rs.getString("Tutor");
                char level = rs.getString("Level").charAt(0);

                setDegreeId(degreeId);
                setTutor(tutor);
                setLevel(level);
            }
        } catch (SQLException e){
            e.printStackTrace(System.err);
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
    }

    /**
     *
     */
    public ArrayList<StudentModsGrades> getGrades(Connection con){
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<StudentModsGrades> grades = new ArrayList<>();
        try{
            stmt = con.createStatement();
            String query = "SELECT Student_Module.Module_id, Module_Name, Student_Module.Mark, Credits " +
                    "FROM Student_Module " +
                    "INNER JOIN Modules " +
                    "ON Student_Module.Module_id = Modules.Module_id " +
                    "WHERE Student_Module.Username = '" + this.getRegistrationNumber() + "'";
            rs = stmt.executeQuery(query);

            while(rs.next()){
                String modId = rs.getString("Module_id");
                String modName = rs.getString("Module_Name");
                int mark = rs.getInt("Mark");
                int credits = rs.getInt("Credits");

                grades.add(new StudentModsGrades(modId, modName, mark, credits));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return grades;
    }
}
