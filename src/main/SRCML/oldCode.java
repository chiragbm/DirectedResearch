package main.SRCML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class oldCode {
	
	static final String WORKING_DIRECTORY = System.getProperty("user.dir");
	static final String FILE_NAME = "text.xml";
	
	public static void main(String a[]){
		
		 try
		    { //this is the file which we want to get its xml representation
		        String javaFileName = "/srcML/DuplicateNumber.java";
		        //use ProcessBuilder class and give it the arguements
		        ProcessBuilder processBuilder = new ProcessBuilder("./srcml", javaFileName);
		        //spicify the directory of [srcml.exe]
		        processBuilder.directory(new File("/Users/chiragm/Documents/DR/SRCML/srcML/bin"));
		        //create the process
		        
		        Process process = processBuilder.start();
//		        let's read the output of this process[ie: the xml data]
		        InputStream inputStream = process.getInputStream();
		        int i;
		        StringBuilder xmlData = new StringBuilder();
		        while ((i = inputStream.read()) != -1)
		        {
		            xmlData.append((char) i);
		        }
		        System.out.println(xmlData.toString());
//		        PrintWriter writer = new PrintWriter(FILE_NAME);
//		        writer.println(xmlData.toString());
//		        writer.close();
//		        getComments();
		    } catch (IOException e)
		    {
		        System.out.println("ERROR: " + e.getMessage());
		    }
		
	}
	
	public static void getComments(){
		try
	    { //this is the file which we want to get its xml representation
	        String javaFileName = WORKING_DIRECTORY+"/"+FILE_NAME;
	        System.out.println(javaFileName);
	        //use ProcessBuilder class and give it the arguements
	        ProcessBuilder processBuilder = new ProcessBuilder("./srcml", "--xpath","string(//src:unit/@filename)", javaFileName);
	        //spicify the directory of [srcml.exe]
	        processBuilder.directory(new File("/Users/chiragm/Documents/DR/SRCML/srcML/bin"));
	        //create the process
	        Process process = processBuilder.start();
	        //let's read the output of this process[ie: the xml data]
	        InputStream inputStream = process.getInputStream();
	        int i;
	        StringBuilder xmlData = new StringBuilder();
	        while ((i = inputStream.read()) != -1)
	        {
	            xmlData.append((char) i);
	        }
	        System.out.println(xmlData.toString());
	    } catch (IOException e)
	    {
	        System.out.println("ERROR: " + e.getMessage());
	    }
	}
}
