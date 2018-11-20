import java.util.Objects;

public class SystemsOperations {
    public boolean checkTotalCredits(User user, Connection con){
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
}
