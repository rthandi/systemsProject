import java.sql.*;

public class dropTable {
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null; // a SQL statement object
			try {
				stmt = con.createStatement();
				String sql = "DROP TABLE Degree_Module_Approved";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE Student_Module";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE User";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE Degree";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE Modules";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE Department";
				stmt.executeUpdate(sql);
				

			}
			catch (SQLException ex) {
			 ex.printStackTrace();
			}
			finally {
				try{
			         if(stmt!=null)
			            con.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			         if(con!=null)
			            con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			}

		}
		catch (SQLException ex) {
		 ex.printStackTrace();
		}

	}

}
