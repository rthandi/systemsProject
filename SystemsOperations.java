
import java.sql.Connection;
import java.sql.ResultSet;
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
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
            Statement stmt = con.createStatement();
            //If database is setup correctly this should cascade and delete any mentions of this department
            String query = "DELETE FROM Department " +
                    "WHERE Department_Code = " + departmentToDelete;
            stmt.executeQuery(query);
        } catch (SQLException e ) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * @param currentUser The currently logged in user
     * @param degreeIdToDelete the ID of the degree that is to be deleted
     * @param con The open connection to the database
     * @throws SQLException Will print out the error with the database if one is encountered
     */
    public static void deleteDegree (User currentUser, String degreeIdToDelete, Connection con) throws SQLException{
        try {
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
            Statement stmt = con.createStatement();
            //If database is setup correctly this should cascade and delete any mentions of this department
            String query = "DELETE FROM Degree " +
                    "WHERE Degree_id = " + degreeIdToDelete;
            stmt.executeQuery(query);
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
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
            Statement stmt = con.createStatement();
            String query = "INSERT INTO Department " +
                    "VALUES (" + departmentCode + ", " + departmentName + ")";
            stmt.executeQuery(query);
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
            //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
            Statement stmt = con.createStatement();
            String query = "SELECT Department_Code " +
                    "FROM Department " +
                    "WHERE Department_Code = " + departmentCode;
            ResultSet rs = stmt.executeQuery(query);
            //Check if the department that was inputted exists
            if (rs.next()) {
                query = "INSERT INTO Degree " +
                        "VALUES (" + degreeId + ", " + degreeName + ", " + departmentCode + ")";
                stmt.executeQuery(query);
                return true;
            } else {
                return false;
            }
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
