package main.SRCML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class CommentsExtractions {
	
	
	final static String COMMENT ="comment";
	static Set<String> statement_set = null;
	static Database db; 
	static String curr_class_name = "";
	static String new_class_name = "";
	static int statements = 0;
	static int fid = 0;
	static int pid = 0;
	
	public static void main(String args[]) throws SQLException, JDOMException, IOException{
		
		// read project directory --> list of projects
			// read subdirectory --> subversions
				// query to insert subversions
				// run srcML for each subversions
					// read scrML and store comments and file names;
		
		statement_set = new HashSet<String>(){{
			
			add("if");
			add("while");
			add("for");
			add("do");
			add("break");
			add("continue");
			add("lable");
			add("switch");
			add("case");
			add("default");
			add("assert");
			add("block");
			add("empty_stmt");
			
		}};
		
		db = new Database();
		String path = "/Users/chiragm/Documents/DR/SRCML/projects";
		
		File[] projects = new File(path).listFiles();
		
		for(File project : projects){
			if(!project.isDirectory())	continue;
			File[] subVersions = project.listFiles();
			for(File subVersion : subVersions){
				if(!subVersion.isDirectory())	continue;
				System.out.println(subVersion.getName());
				pid = db.insertProject(subVersion.getName());
				System.out.println(pid);
				// call srcML
				InputStream is = executeSRCML(subVersion);
				
				parseXML(is, 0);
			}
		}
		
		
	}
	
	
	public static InputStream executeSRCML(File project){
		
		try
	    { //this is the file which we want to get its xml representation
	        String javaFileName = project.getAbsolutePath();
	        //use ProcessBuilder class and give it the arguements
	        ProcessBuilder processBuilder = new ProcessBuilder("./srcml", javaFileName);
	        //spicify the directory of [srcml.exe]
	        processBuilder.directory(new File("srcML/bin"));
	        //create the process
	        Process process = processBuilder.start();
	        InputStream inputStream = process.getInputStream();
	        
	        return inputStream;
	        
	        
	        
	    } catch (IOException e)
	    {
	        System.out.println("ERROR: Input Stream " + e.getMessage());
	    }
		
		return null;
	}
	
	public static void parseXML(InputStream stream, int pid) throws JDOMException, IOException, SQLException{
		
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(stream);
		Element classElement = document.getRootElement();
		parseChilds(classElement);
	}
	
	
	/**
	 * 
	 * @param ele
	 * @param pid
	 * @param cid
	 * @throws SQLException
	 * 
	 * File comes see if 
	 * 
	 */
	
	public static void parseChilds(Element ele) throws SQLException{
		
		if(ele.getName().equals(COMMENT)){
			if(!curr_class_name.equals(new_class_name)){
				// insert curr_class_name file
				fid = db.insertFile(pid, new_class_name);
				curr_class_name = new_class_name;
				System.out.println("Comment : "+curr_class_name);
				
			}
			//insert comment
			db.insertComment(fid, ele.getText());
		}
//			return;
		
		if(statement_set.contains(ele.getName())){
			statements+=1;
		}
		
		List<Element> children = ele.getChildren();
		for(Element c : children){
			if(ele.getName().equals("unit") && ele.getAttributeValue("filename") != null ){
				if(statements > 0){
					if(!curr_class_name.equals(new_class_name)){
						// insert new_class_name file
						fid = db.insertFile(pid, new_class_name);
						System.out.println("statements = " +new_class_name);
					}
					System.out.println("st = " + statements);
					// insert statements
					db.insertStatement(fid, statements);
				}	
				new_class_name = ele.getAttributeValue("filename");
				statements = 0;
			}
 			parseChilds(c);
		}
	}
}
