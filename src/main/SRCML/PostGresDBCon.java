package main.SRCML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostGresDBCon {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		Class.forName("org.postgresql.Driver");
		Connection con = null;
		con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Code_Comments_DB","postgres", "root");
		
		String query = "select * from comments";
		Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
        	int cid = rs.getInt("cid");
        	String comment = rs.getString("comment");
        	
        	System.out.println(cid +" = "+ comment);
        }
		con.close();
	}
}
