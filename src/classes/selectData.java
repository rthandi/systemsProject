package classes;

import java.sql.*;

public class selectData {
	public static void main(String[] args) {
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");) {
			Statement stmt = null; // a SQL statement object
			try {
				System.out.println("Retrieving all data...");
				stmt = con.createStatement();
				
				// Outputs all entries to Department table
				System.out.println("\n\n--- Department ---\n");
				ResultSet res =
						stmt.executeQuery("SELECT * FROM Department");
				while(res.next()){
					//Retrieve by column name
					String code = res.getString("Department_Code");
					String name = res.getString("Department_Name");

					//Display values
					System.out.print("Department Code: " + code);
					System.out.println(", Department Name: " + name);
				}
				res.close();
				
				// Outputs all entries to Modules table
				System.out.println("\n\n--- Modules ---\n");
			    res = stmt.executeQuery("SELECT * FROM Modules");
	 			while(res.next()){
	 				//Retrieve by column name
	 		        String id = res.getString("Module_id");
	 		        String name = res.getString("Module_Name");
	 		        Integer credit = res.getInt("Credits");
   		            //Display values
	 		        System.out.print("Module Id: " + id);
	 		        System.out.print(", Module Name: " + name);
	 		        System.out.println(", Credits: " + credit);
	 		        
	 		    }
	 		    res.close();
	 		    
	 		    // Outputs all entries to Degree table
	 		    System.out.println("\n\n--- Degree ---\n");
	 		    res = stmt.executeQuery("SELECT * FROM Degree");
	 			while(res.next()){
	 				//Retrieve by column name
	 		        String id = res.getString("Degree_id");
	 		        String name = res.getString("Degree_Name");
	 		        String code = res.getString("Department_Code");
  		            //Display values
	 		        System.out.print("Degree id: " + id);
	 		        System.out.print(", Degree Name: " + name);
	 		        System.out.println(", Degree Code: " + code);
	 		        
	 		    }
	 		    res.close();
	 		    
	 		    // Outputs all entries to User table
	 		    System.out.println("\n\n--- User ---\n");
	 		    res = stmt.executeQuery("SELECT * FROM User");
	 			while(res.next()){
	 				//Retrieve by column name
	 		        String user = res.getString("Username");
	 		        String hash = res.getString("Hash");
	 		        String title = res.getString("Title");
	 		        String surname = res.getString("Surname");
	 		        String other = res.getString("Other_names");
	 		        String role = res.getString("Role");
	 		        String email = res.getString("Email");
 		            //Display values
	 		        System.out.print("Username: " + user);
	 		        System.out.print(" Hash: " + hash);
	 		        System.out.print(", Title: " + title);
	 		        System.out.print(", Surname: " + surname);
	 		        System.out.print(", Other: " + other);
	 		        System.out.print(", Email: " + email);
	 		        System.out.print(" Role: " + role);
	 		    }
	 		    res.close();
	 		    
	 		    // Outputs all entries to Student table
	 		    System.out.println("\n\n--- Student ---\n");
	 		    res = stmt.executeQuery("SELECT * FROM Student");
	 			while(res.next()){
	 				//Retrieve by column name
	 		        String user = res.getString("Username");
	 		        String degreeId = res.getString("Degree_id");
	 		        String tutor = res.getString("Tutor");
	 		        String level = res.getString("Level");
 		            //Display values
	 		        System.out.print("Username: " + user);
	 		        System.out.print(", Degree Id: " + degreeId);
	 		        System.out.print(", Tutor: " + tutor);
	 		        System.out.print(", Level: " + level);
	 		    }
	 		    res.close();
	 		    
	 		    // Outputs all entries to Student Module table
	 		    System.out.println("\n\n--- Student Module ---\n");
	 		    res = stmt.executeQuery("SELECT * FROM Student_Module");
	 			while(res.next()){
	 				//Retrieve by column name
	 				String user = res.getString("Username");
	 		        String id = res.getString("Module_id");
	 		        String mark = res.getString("Mark");
  		            //Display values
	 		        System.out.print("Username: " + user);
	 		        System.out.print(", Module Id: " + id);
	 		        System.out.print(", Mark: " + mark);
	 		    }
	 		    res.close();
	 		    
	 		    // Outputs all entries to Degree Module Approved table
	 		    System.out.println("\n\n--- Degree Module Approved ---\n");
	 		    res = stmt.executeQuery("SELECT * FROM Degree_Module_Approved");
	 		    while(res.next()){
	 				//Retrieve by column name
	 				String id = res.getString("Degree_id");
	 		        String level = res.getString("Level");
	 		        String modId = res.getString("Module_id");
	 		        String comp = res.getString("Compulsory");
  		            //Display values
	 		        System.out.print("Degree Id: " + id);
	 		        System.out.print(", Level: " + level);
	 		        System.out.print(", Module Id: " + modId);
	 		        System.out.println(", Compulosry: " + comp);
	 		    }
	 		    res.close();
	 		    
	 		    // Done
	 		    System.out.println("\n\nAll entries outputted");
	 		    
		      
			}
			catch (SQLException ex) {
			 ex.printStackTrace();
			}
			finally {
			 if (stmt != null) stmt.close();
			}


		}
		catch (SQLException ex) {
		 ex.printStackTrace();
		}

	}

}
