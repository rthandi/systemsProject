import java.sql.*; // adequate for most purposes
import javax.sql.*; // only for advanced features

public class createTable {
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null; // a SQL statement object
			try {
				stmt = con.createStatement();
				String sql = "CREATE TABLE Department " +
							 "(Department_Code VARCHAR(3) not NULL, " +
							 " Department_Name VARCHAR(50) not NULL, " +
							 " PRIMARY KEY (Department_Code))";
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE Modules " +
					 "(Module_id VARCHAR(7) not NULL, " +
					 " Module_Name VARCHAR(50) not NULL, " +
					 " Credits SMALLINT not NULL, " +
					 " PRIMARY KEY (Module_id))";
					stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE Degree " +
					 "(Degree_id VARCHAR(8) not NULL, " +
					 " Degree_Name VARCHAR(50) not NULL, " +
					 " Department_Code VARCHAR(3) not NULL, " +
					 " PRIMARY KEY (Degree_id), " +
					 " FOREIGN KEY (Department_Code) REFERENCES Department(Department_Code))";
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE User " +
					 "(Username VARCHAR(7) not NULL, " +
					 " Title VARCHAR(5) not NULL, " +
					 " Surname VARCHAR(50) not NULL, " +
					 " Other_names VARCHAR(50) not NULL, " +
					 " Degree_id VARCHAR(8) not NULL, " +
					 " Email VARCHAR(50) not NULL, " +
					 " Tutor VARCHAR(50) not NULL, " +
					 " PRIMARY KEY (Username), " +
					 " FOREIGN KEY (Degree_id) REFERENCES Degree (Degree_id))";
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE Student_Module" +
					 "(Username VARCHAR(7) not NULL, " +
					 " Module_id VARCHAR(7) not NULL, " +
					 " Mark SMALLINT DEFAULT 0, " +
					 " FOREIGN KEY (Username) REFERENCES User (Username))";
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE Degree_Module_Approved " +
					 "(Degree_id VARCHAR(8) not NULL, " +
					 " Level VARCHAR(1) not NULL, " +
					 " Module_id VARCHAR(7) not NULL, " +
					 " Compulsory BOOLEAN DEFAULT False, " +
					 " FOREIGN KEY (Degree_id) REFERENCES Degree (Degree_id), " +
					 " FOREIGN KEY (Module_id) REFERENCES Modules (Module_id))";
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
