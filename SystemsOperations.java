import java.sql.Connection;
import java.util.Objects;

public class SystemsOperations {
    public static boolean checkTotalCredits(User user, Connection con){
        stmt = con.createStatement();
        String query = "SELECT Credits " +
                "FROM modules m " +
                "WHERE User_ID = " + user.userID +
                "INNER JOIN Student_Module sm" +
                "on m.Module = sm.Module_ID";
        //Insert sql query to get the modules that the user is doing
        ResultSet rs = stmt.executeQuery(query);
        // Iterate over the ResultSet to total up the credits
        int counter = 0;
        while (rs.next()){
            counter += rs.getInt("Credits");
        }
        String level = user.Degree_ID.substring(3,4);
        //If U then it is undergrad
        if (Objects.equals(level, "U")){
            return counter == 120;
        } else {
            return counter == 180;
        }
    }

    //DELETION OPERATIONS
    public static void deleteDepartment (User currentUser, String departmentToDelete, Connection con){
        //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
        stmt = con.createStatement();
        //If database is setup correctly this should cascade and delete any mentions of this department
        String query = "DELETE FROM Department" +
                "WHERE DepartmentCode = " + departmentToDelete;
        stmt.executeQuery(query);
    }

    //ADDING OPERATIONS
    public static void addDepartment (User currentUser, String departmentCode, String departmentName, Connection con){
        //TODO: if statement here to check correct user privileges. Not sure how we are doing this yet
        stmt = con.createStatement();
        String query = "INSERT INTO Department" +
                "VALUES (" + departmentCode + ", " + departmentName + ")";
        stmt.executeQuery(query);
    }
}
