package es.bsc.inb.limtox.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLUtil {
	
	static DocumentBuilder documentBuilder = null;
	
	public static DocumentBuilder getDocumentBuilder() {
		if (documentBuilder == null) {
	        try {
	        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				documentBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return documentBuilder;
		}else {
			return documentBuilder;
		}
	}
}
