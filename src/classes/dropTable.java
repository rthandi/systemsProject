package classes;

import java.sql.*;

public class dropTable {
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null; // a SQL statement object
			try {
				// Deleting all Tables...
				System.out.println("Deleting all Tables...");
				stmt = con.createStatement();
				
				// Deleting Degree_Module_Approved
				System.out.println("--- Deleting Degree_Module_Approved");
				String sql = "DROP TABLE Degree_Module_Approved";
				stmt.executeUpdate(sql);

				// Deleting Student_Module
				System.out.println("--- Deleting Student_Module");
				sql = "DROP TABLE Student_Module";
				stmt.executeUpdate(sql);

				// Deleting User
				System.out.println("--- Deleting User");
				sql = "DROP TABLE User";
				stmt.executeUpdate(sql);

				// Deleting Degree
				System.out.println("--- Deleting Degree");
				sql = "DROP TABLE Degree";
				stmt.executeUpdate(sql);

				// Deleting Modules
				System.out.println("--- Deleting Modules");
				sql = "DROP TABLE Modules";
				stmt.executeUpdate(sql);

				// Deleting Department
				System.out.println("--- Deleting Department");
				sql = "DROP TABLE Department";
				stmt.executeUpdate(sql);
				
				// Done
				System.out.println("Completed table deletion");

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
