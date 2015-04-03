package model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import configuration.StringConstants;

/**
 * Helpful base class for loading and saving the bbs.xml document
 * that holds the applications data.
 * @author wouterken
 *
 */
public class XMLIO {
	
	protected static String datafilename = (StringConstants.DATA_FILE);
	protected static Document boards;

	/**
	 * Loads the data-file into memory. 
	 */
	public static synchronized void loadBoards(){
		
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			boards = docBuilder.parse(datafilename);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the bulletin boards, back to an XML file.
	 */
	protected static synchronized void saveBoards() {
		try {
			boards.normalizeDocument();
			
			TransformerFactory tff = TransformerFactory.newInstance();
			tff.setAttribute("indent-number", new Integer(2));
			
			Transformer transformer = tff.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(boards);
			
			transformer.transform(source, result);
	
			String xmlString = result.getWriter().toString();
			PrintWriter out = new PrintWriter(new FileWriter(datafilename));
			
			out.write(xmlString);
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper functions
	 * 
	 */
	
	/**
	 * Generates a 'relatively' unique id.
	 * @return
	 */
	protected static String uniqueID(){
		return  (Math.abs(UUID.randomUUID().getMostSignificantBits())+"").substring(0, 6);
	}
	
	/**
	 * Sets a set of properties on an XML element from an array of keys and values.
	 * @param elm
	 * @param keys
	 * @param values
	 */
	protected static void setKeyValues(Element elm, String[] keys, String[] values) {
		for (int i = 0; i < keys.length; i++) {
			elm.setAttribute(keys[i], values[i]);
		}
	}
	/**
	 * Generates an MD5 from a string
	 * useful for avoiding storing passwords as plain text in a file.
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	protected static String MD5(String str) throws NoSuchAlgorithmException{
    	return new BigInteger(1, MessageDigest.getInstance("MD5").digest(str.getBytes())).toString();
    }
	

}
