package main.SRCML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/*
 * 
 * Store statements as well.
 * 
 */

public class CommentsExtraction_old {
	
	static final String COMMENT ="comment";
	
	static Connection con = null;
	static Statement stmt = null;
	static PreparedStatement preparedStmt = null;
	static String query = "insert into comments (cid,fid,comment_text) values (?, ?, ?)";
	static int count = 3;
	
	static{
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Code_Comments_DB","postgres", "root");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error conneting DB");
		}
	}
	
	public static void main(String a[]) throws JDOMException, SQLException{
		
		 try
		    { //this is the file which we want to get its xml representation
		        String javaFileName = "/Users/chiragm/Documents/workspace/DR_SRCML/srcML/bin/java-design-patterns-master";
		        //use ProcessBuilder class and give it the arguements
		        ProcessBuilder processBuilder = new ProcessBuilder("./srcml", javaFileName);
		        //spicify the directory of [srcml.exe]
		        processBuilder.directory(new File("srcML/bin"));
		        //create the process
		        Process process = processBuilder.start();
		        InputStream inputStream = process.getInputStream();
		        SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build(inputStream);
				Element classElement = document.getRootElement();
				parseChilds(classElement, "");
		        
		    } catch (IOException e)
		    {
		        System.out.println("ERROR: " + e.getMessage());
		    }
		
	}
	
	public static void parseChilds(Element ele, String class_name) throws SQLException{
		
		List<Element> children = ele.getChildren();
		if(children.size()==0){
			if(ele.getName() .equals(COMMENT)){
				
//				System.out.println("Running Query");
//				preparedStmt = con.prepareStatement(query);
//				preparedStmt.setInt (1, count);
//			    preparedStmt.setInt (2, 1);
//			    preparedStmt.setString (3, ele.getText());
//			    preparedStmt.execute();
//			    count++;
//			    System.exit(0);
			    
//				System.out.println(class_name);
////				System.out.println(ele.getName() +" ---> "+ ele.getText());
//				System.out.println(ele.getName());
			}
			return;
		}
		for(Element c : children){
			if(ele.getName().equals("unit") && ele.getAttributeValue("filename") != null )
//				System.out.println(ele.getAttributeValue("filename"));
				class_name = ele.getAttributeValue("filename");
			parseChilds(c, class_name);
		}
	}
}
