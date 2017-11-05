package main.SRCML;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
public class Parser {
	
	public static void main(String[] args) throws JDOMException, IOException{
		SAXBuilder saxBuilder = new SAXBuilder();
		File inputFile = new File("text.xml"); 
		Document document = saxBuilder.build(inputFile);
		Element classElement = document.getRootElement();
		
		dfs(classElement);
//		System.out.println(classElement.toString());
//		for(Element ele : classElement.getChildren()){
//			System.out.println(ele.toString());
//			System.out.println(ele.getChildText());
//		}
	}
	
	public static void dfs(Element ele){
		
		List<Element> children = ele.getChildren();
		if(children.size()==0){
			System.out.println(ele.getName() +" ---> "+ ele.getText());
			return;
		}
		for(Element c : children)	dfs(c);
	}
}
