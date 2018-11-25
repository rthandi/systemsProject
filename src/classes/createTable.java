package classes;

import java.sql.*;

public class createTable {
	// This is a class that is used to create all tables within the Database
	
	/**
	 * @throws SQLException Will print out the error with the database if one is encountered
	 * @author Isaac Hill
	 * @
	 */
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null;
			try {
				// Creating Department
				System.out.println("Creating all Tables...");
				stmt = con.createStatement();
				String sql = "CREATE TABLE Department " +
							 "(Department_Code VARCHAR(3) not NULL, " +
							 " Department_Name VARCHAR(50) not NULL, " +
							 " PRIMARY KEY (Department_Code))";
				stmt.executeUpdate(sql);
				
				// Creating Modules
				System.out.println("--- Creating Modules");
				sql = "CREATE TABLE Modules " +
					 "(Module_id VARCHAR(7) not NULL, " +
					 " Module_Name VARCHAR(50) not NULL, " +
					 " Credits SMALLINT not NULL, " +
					 " PRIMARY KEY (Module_id))";
				stmt.executeUpdate(sql);
			
				// Creating Degree
				System.out.println("--- Creating Degree");
				sql = "CREATE TABLE Degree " +
					 "(Degree_id VARCHAR(8) not NULL, " +
					 " Degree_Name VARCHAR(50) not NULL, " +
					 " Department_Code VARCHAR(3) not NULL, " +
					 " PRIMARY KEY (Degree_id), " +
					 " CONSTRAINT Department_Code" +
						 " FOREIGN KEY (Department_Code)" +
						 " REFERENCES Department(Department_Code)" +
						 " ON DELETE CASCADE ON UPDATE CASCADE)";
				stmt.executeUpdate(sql);
				
				// Creating User
				System.out.println("--- Creating User");
				sql = "CREATE TABLE User " +
					 "(Username VARCHAR(8) not NULL, " +
					 " Hash VARCHAR(50) not NULL," +
					 " Title VARCHAR(5) not NULL, " +
					 " Surname VARCHAR(50) not NULL, " +
					 " Other_names VARCHAR(50) not NULL, " +
					 " Role VARCHAR(15) not NULL," +
					 " Email VARCHAR(50) not NULL, " +
					 " PRIMARY KEY (Username))";
					 
				stmt.executeUpdate(sql);
				
				// Creating Student
				System.out.println("--- Creating Student");
				sql = "CREATE TABLE Student " +
					 "(Username VARCHAR(8) not NULL, " +
					 " Degree_id VARCHAR(8) not NULL, " +
					 " Tutor VARCHAR(50) not NULL, " +
					 " Level VARCHAR(1) not NULL, " +
					 " PRIMARY KEY (Username), " +
					 " CONSTRAINT username" +
						 " FOREIGN KEY (Username)" +
						 " REFERENCES User (Username)" +
						 " ON DELETE CASCADE ON UPDATE CASCADE)";
					 
				stmt.executeUpdate(sql);
				
				// Creating Student_Module
				System.out.println("--- Creating Student_Module");
				sql = "CREATE TABLE Student_Module" +
					 "(Username VARCHAR(8) not NULL, " +
					 " Module_id VARCHAR(7) not NULL, " +
					 " Mark SMALLINT DEFAULT 0, " +
					 " CONSTRAINT Username2" +
						 " FOREIGN KEY (Username)" +
						 " REFERENCES User (Username)" +
						 " ON DELETE CASCADE ON UPDATE CASCADE)";
					 
				stmt.executeUpdate(sql);
				
				// Creating Degree_Module_Approved
				System.out.println("--- Creating Degree_Module_Approved");
				sql = "CREATE TABLE Degree_Module_Approved " +
					 "(Degree_id VARCHAR(8) not NULL, " +
					 " Level VARCHAR(1) not NULL, " +
					 " Module_id VARCHAR(7) not NULL, " +
					 " Compulsory BOOLEAN DEFAULT False, " +
					 " CONSTRAINT Degree_id2" +
						 " FOREIGN KEY (Degree_id)" +
						 " REFERENCES Degree (Degree_id)" +
						 " ON DELETE CASCADE ON UPDATE CASCADE," +
					 " CONSTRAINT Module_id" +
						 " FOREIGN KEY (Module_id)" +
						 " REFERENCES Modules (Module_id)" +
						 " ON DELETE CASCADE ON UPDATE CASCADE)";
				stmt.executeUpdate(sql);
				
				// Done
				System.out.println("Completed table creation");
			}
			catch (SQLException ex) {
			 ex.printStackTrace();
			}
			finally {
				try{
			         if(stmt!=null)
			            con.close();
			      }catch(SQLException se){
			    	  se.printStackTrace(System.err);
			      }
			      try{
			         if(con!=null)
			            con.close();
			      }catch(SQLException se){
			         se.printStackTrace(System.err);
			      }
			}

		}
		catch (SQLException ex) {
		 ex.printStackTrace();
		}
	}
}
