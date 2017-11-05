package main.SRCML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Connection con = null;
	Statement stmt = null;
	PreparedStatement preparedStmt = null;
	
	final String DRIVER = "org.postgresql.Driver";
	final String DBCON = "jdbc:postgresql://localhost:5432/Code_Comments_DB";
	final String USERNAME = "postgres";
	final String PWD = "root";
	
	String projectQuery = null;
	String fileQuery = null;
	String commentQuery = null;
	String statementQuery = null;
	int p_id_counter = 0;
	int s_id_counter = 0;
	int c_id_counter = 0;
	int f_id_counter = 0;
	
	Database(){
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(DBCON,USERNAME, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error conneting DB");
		}
		projectQuery = "insert into project (name) values (?)";
		commentQuery = "insert into comments (fid,comment_text) values (?, ?)";
		fileQuery = "insert into file (pid, name) values (?, ?)";
		statementQuery = "insert into statements (fid, statement) values (?, ?)";
	}
	
	public int insertProject(String p_name) throws SQLException{
		
		System.out.println("Running Project Query");
		preparedStmt = con.prepareStatement(projectQuery, Statement.RETURN_GENERATED_KEYS);
	    preparedStmt.setString (1, p_name);
	    preparedStmt.executeUpdate();
	    ResultSet rs = preparedStmt.getGeneratedKeys();
	    while(rs.next())
	    	return rs.getInt(2);
	    return 0;
		
	}
	
	public int insertFile(int pid, String f_name) throws SQLException{
		
		System.out.println("Running File Query");
		preparedStmt = con.prepareStatement(fileQuery, Statement.RETURN_GENERATED_KEYS);
	    preparedStmt.setInt (1, pid);
	    preparedStmt.setString (2, f_name);
	    preparedStmt.executeUpdate();
	    ResultSet rs = preparedStmt.getGeneratedKeys();
	    while(rs.next())
	    	return rs.getInt(3);
	    return 0;
	}
	
	public int insertStatement(int fid, int statement) throws SQLException{
		
		s_id_counter+=1;
		System.out.println("Running Statement Query");
		preparedStmt = con.prepareStatement(statementQuery, Statement.RETURN_GENERATED_KEYS);
	    preparedStmt.setInt (1, fid);
	    preparedStmt.setInt (2, statement);
	    preparedStmt.executeUpdate();
	    ResultSet rs = preparedStmt.getGeneratedKeys();
	    while(rs.next())
	    	return rs.getInt(3);
	    return 0;
	}
	
	public int insertComment(int fid, String text) throws SQLException{
		
		c_id_counter +=1;
		System.out.println("Running Comment Query");
		preparedStmt = con.prepareStatement(commentQuery, Statement.RETURN_GENERATED_KEYS);
	    preparedStmt.setInt (1, fid);
	    preparedStmt.setString (2, text);
	    preparedStmt.executeUpdate();
	    ResultSet rs = preparedStmt.getGeneratedKeys();
	    while(rs.next())
	    	return rs.getInt(3);
	    return 0;
	}
}
