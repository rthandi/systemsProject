package classes;

import java.sql.*;

public class fillTable {
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null; // a SQL statement object
			try {
				System.out.println("Populating all tables");
				stmt = con.createStatement();
			      
				System.out.println("--- Populating Department table");
				
			    String sql = "INSERT INTO Department " +
			                 "VALUES ('COM', 'Computer Science')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Department " +
		              "VALUES ('BUS', 'Business School')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Department " +
		              "VALUES ('PSY', 'Psychology')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Department " +
		              "VALUES ('LAN', 'Modern Languages	')";
			    stmt.executeUpdate(sql);
			      
			    
			    System.out.println("--- Populating Modules table");
				
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1001', 'Intro to Software', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1002', 'Foundations of Computer Science', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1006', 'Devices and Dirk', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2004', 'Data Driven Computing', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2008', 'Systems Design', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COMP000', 'Placement', 0)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4001', 'Sell things', 20)";
			    stmt.executeUpdate(sql);
			      
			    
			    System.out.println("--- Populating Degree table");
				  
			    sql = "INSERT INTO Degree " +
		              "VALUES ('COMU01', 'BSc Computer Science', 'COM')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('COMU11', 'BSc Computer Science with a Year in Industry', 'COM')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('COMP12', 'MEng Software Engineering with a Year in Industry', 'COM')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('BUSU01', 'BSc in Business Administration', 'BUS')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('BUSP01', 'MSc in Business Administration', 'BUS')";
			    stmt.executeUpdate(sql);
			      
			    
			    System.out.println("--- Populating User table");
				  
			    sql = "INSERT INTO User " +
		              "VALUES ('aca17ab', 'hashsash', 'Mr', 'Bobby', 'Aegar', 'Student', 'COMU01', 'ABobby1@uni.ac.uk', 'McMe')";
			    stmt.executeUpdate(sql);
			      
			      
			    System.out.println("--- Populating Student Module table");
				
			    sql = "INSERT INTO Student_Module " +
		              "VALUES ('aca17ab', 'COM1001', 60)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Student_Module " +
		              "VALUES ('aca17ab', 'COM1002', 61)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Student_Module " +
		              "VALUES ('aca17ab', 'COM1006', 69)";
			    stmt.executeUpdate(sql);
			      
			      
			    System.out.println("--- Populating Degree_Module_Approved table");
				
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1002', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1006', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2004', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2008', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU11', 'P', 'COMP000', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 3, 'COM1006', False)";
			    stmt.executeUpdate(sql);
			    
			    System.out.println("Completed populating tables");
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
