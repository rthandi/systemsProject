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
				//COM - MEng
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1001', 'Intro to Software Engineering', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1002', 'Foundations of Computer Science', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1003', 'Devices and Networks', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1004', 'Java Programming', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1005', 'Machines and Intelligence', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2001', 'Data Driven Computing', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2002', 'Systems Design', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COMP000', 'Placement', 0)";
			    stmt.executeUpdate(sql);
			    
			    // COM - Bsc
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1006', 'Foundations of Object Oriented Programming', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1007', 'Professional Issues', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1008', 'Advanced Java Programming', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1009', 'Computer Security and Forensics', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM1010', 'Web Technologies', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2003', 'Cloud Computing', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2004', 'Information Systems Modelling', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2005', 'Information Systems Project Management', 10)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
			    	  "VALUES ('COM2006', 'Information Systems Organisations', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM2007', 'Information Systems and Information Society', 10)";
			    stmt.executeUpdate(sql);
			      
			    //PSY - MPsy
			    sql = "INSERT INTO Modules " +
		              "VALUES ('PSY3001', 'Research Methods', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('PSY3002', 'Current Issues in Psychology', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('PSY3003', 'Psychology Fundamentals', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('COM3004', 'Computational Neuroscience', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
			    	  "VALUES ('COM3005', 'Text Processing', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('PSY3006', 'Natural Language Processing', 20)";
			    stmt.executeUpdate(sql);
				    
			    //BUS - MSC
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4001', 'Managing People in Organisation', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4002', 'Marketing', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4003', 'Strategic Management', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4004', 'Company Project', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4005', 'Economics', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4006', 'Management Inquiry', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4007', 'Accountic', 20)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Modules " +
		              "VALUES ('BUS4008', 'Financial Management', 20)";
			    stmt.executeUpdate(sql);
										    
			    
			    System.out.println("--- Populating Degree table");
				  
			    sql = "INSERT INTO Degree " +
		              "VALUES ('COMU01', 'BSc Information Systems', 'COM')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('COMP12', 'MEng Software Engineering with a Year in Industry', 'COM')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('PSYP01', 'MPsy Cognitive Science', 'PSY')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree " +
		              "VALUES ('BUSP01', 'MSc in Business Administration', 'BUS')";
			    stmt.executeUpdate(sql);
			      
			    
			    System.out.println("--- Populating User table");
			    sql = "INSERT INTO User " +
			           "VALUES ('aaa18aa', '"+Sha.getSHA("hash") +"', 'Mr', 'Callum', 'Neenan', 'Administrator', 'cneen@uni.ac.uk')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO User " +
			           "VALUES ('bbb18bb', '"+Sha.getSHA("hash") +"', 'Mr', 'Robbie', 'Thandie', 'Registrar', 'rthan@uni.ac.uk')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO User " +
			           "VALUES ('ccc18cc', '"+Sha.getSHA("hash") +"', 'Mr', 'Seth', 'Faulkner', 'Teacher', 'sfaul@uni.ac.uk')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO User " +
			           "VALUES ('ddd18dd', '"+Sha.getSHA("hash") +"', 'Mr', 'Isaac', 'Hill', 'Student', 'cneen@uni.ac.uk')";
			    stmt.executeUpdate(sql);
			    
			    
			    System.out.println("--- Populating Student table");
			    sql = "INSERT INTO Student " +
				      "VALUES ('ddd18dd', 'COMU01', 'Mr Phil', '1', DEFAULT)";
			    stmt.executeUpdate(sql); 
			      
			    System.out.println("--- Populating Student Module table");
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1001', '50')";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1002', '50')";
			    stmt.executeUpdate(sql); 
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1003', '50')";
			    stmt.executeUpdate(sql); 
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1004', '50')";
			    stmt.executeUpdate(sql); 
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1005', '50')";
			    stmt.executeUpdate(sql); 
			    sql = "INSERT INTO Student_Module " +
				      "VALUES ('ddd18dd', 'COM1006', '50')";
			    stmt.executeUpdate(sql); 
			      
			    System.out.println("--- Populating Degree_Module_Approved table");
				// COM
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 1, 'COM1001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 1, 'COM1002', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 1, 'COM1003', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 1, 'COM1004', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 1, 'COM1005', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1006', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1007', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1008', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1009', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 1, 'COM1010', False)";
			    stmt.executeUpdate(sql);
			    
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 2, 'COM2001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMP12', 2, 'COM2002', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2003', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2004', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2005', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2006', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('COMU01', 2, 'COM2007', False)";
			    stmt.executeUpdate(sql);
			    
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'COM3004', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'COM3005', False)";
			    stmt.executeUpdate(sql);
			    
			    // PSY
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'PSY3001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'PSY3002', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'PSY3003', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('PSYP01', 3, 'PSY3006', False)";
			    stmt.executeUpdate(sql);
			    
			    // BUS
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4001', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4002', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4003', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4004', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4005', True)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4006', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4007', False)";
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO Degree_Module_Approved " +
		              "VALUES ('BUSP01', 4, 'BUS4008', False)";
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
