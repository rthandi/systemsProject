package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static classes.SystemsOperations.checkTotalCredits;

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

    //CONSTRUCTOR
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

    //GETTER METHODS
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

    //TODO: delete this at the end if not needed
    //SETTER METHODS (MAY NOT NEED SO NOT ADDING YET)

    public boolean checkRegValid(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rsModules = null;
        ResultSet rsModulesApproved = null;
        try {
            //check if the total number of credits is correct
            if (!checkTotalCredits(this, con)){
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
}
