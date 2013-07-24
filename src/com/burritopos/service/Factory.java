/**
 * 
 */
package com.burritopos.service;

import java.io.*;
import org.apache.log4j.*;
import java.util.Date;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import com.burritopos.exception.ServiceLoadException;
import com.burritopos.service.dao.IBurritoSvc;
import com.burritopos.service.dao.ICustomerSvc;
import com.burritopos.service.dao.IEmployeeSvc;
import com.burritopos.service.dao.IInventorySvc;
import com.burritopos.service.dao.IManagerSvc;
import com.burritopos.service.dao.IOrderSvc;
import com.burritopos.service.dao.file.BurritoSvcImpl;
import com.burritopos.service.dao.file.CustomerSvcImpl;
import com.burritopos.service.dao.file.EmployeeSvcImpl;
import com.burritopos.service.dao.file.InventorySvcImpl;
import com.burritopos.service.dao.file.ManagerSvcImpl;
import com.burritopos.service.dao.file.OrderSvcImpl;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.util.HashMap;

/**
 * @author james.bloom
 *
 */
@SuppressWarnings("unused")
public class Factory extends DefaultHandler {
	private static Logger dLog = Logger.getLogger(Factory.class);
	private HashMap<String, String> fProperties = new HashMap<String,String>();
	private String curKey = "";

	//week 4 generic Factory/Singleton calls
	private Factory() {
		try {
			// parse the file on load
			this.parse(new File("config/properties.xml"));
		}
		catch(Exception e) {
			dLog.error("Exception in parse: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private final static Factory factory = new Factory();

	// added synchronized to ensure thread safety
	public synchronized static Factory getInstance() {return factory;}

	//@SuppressWarnings("unchecked")
	public IService getService(String name) throws ServiceLoadException
	{
		dLog.trace("In getService | name: " + name);
		try 
		{
			Class<?> c = Class.forName(getImplName(name));
			return (IService)c.newInstance();
		} 
		catch (ClassNotFoundException e1) {
			dLog.error("ClassNotFoundException in getService: " + e1.getMessage());
			e1.printStackTrace();
			throw new ServiceLoadException(name + " not loaded");
		}
		catch (Exception e2) 
		{
			dLog.error("Exception in getService: " + e2.getMessage());
			e2.printStackTrace();
			throw new ServiceLoadException(name + " not loaded");
		}
	}

	private String getImplName (String name) throws Exception
	{
		String retVal = "";

		try {
			/*java.util.Properties props = new java.util.Properties();
		    //java.io.FileInputStream fis = new java.io.FileInputStream("config/properties.txt");
		    java.io.FileInputStream fis = new java.io.FileInputStream("config/properties.xml");
		    //props.load(fis);
		    props.loadFromXML(fis);
		    fis.close();
		    dLog.trace("Got " + props.size() + " service properties");
		    retVal = props.getProperty(name);*/

			//read out of HashMap populated by SAX parser
			retVal = fProperties.get(name).toString();

			dLog.trace("Got " + retVal + " from properties.xml file for name: " + name);
		}
		catch(Exception e) {
			File dir1 = new File (".");
			dLog.trace("Current diretory: " + dir1.getCanonicalPath());
			dLog.error("Exception in getImplName: "+e.getMessage());
			e.printStackTrace();
		}

		return retVal;
	}

	//week 3 specific implementation calls
	public ICustomerSvc getCustomerSvc() {
		return new CustomerSvcImpl();
	}

	public IBurritoSvc getBurritoSvc() {
		return new BurritoSvcImpl();
	}

	public IEmployeeSvc getEmployeeSvc() {
		return new EmployeeSvcImpl();
	}

	public IInventorySvc getInventorySvc() {
		return new InventorySvcImpl();
	}

	public IManagerSvc getManagerSvc() {
		return new ManagerSvcImpl();
	}

	public IOrderSvc getOrderSvc() {
		return new OrderSvcImpl();
	}

	// SAX Parsing
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrib) throws SAXException {
		//dLog.trace("In startElement | uri: " + uri + " | localName: " + localName + " | qName: " + qName);
		if (qName.equals("entry")) {
			// process the start of the entry tag
			//dLog.trace("Got an entry tag in startElement | Length: " + attrib.getLength());
			for(int n=0; n<attrib.getLength(); n++) {
				//dLog.trace("Element at " + n + ": " + attrib.getQName(n) + " | " + attrib.getValue(n));

				//set our key
				if(attrib.getQName(n).equals("key"))
					curKey = attrib.getValue(n);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//dLog.trace("In endElement | uri: " + uri + " | localName: " + localName + " | qName: " + qName);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//dLog.trace("In characters | ch: " + new String(ch, start, length));

		if(!curKey.equals("")) {
			dLog.trace("curKey: " + curKey + " | value: " + new String(ch, start, length) + " | hashmap size: " + fProperties.size());
			fProperties.put(curKey, new String(ch, start, length));
			curKey = "";
		}
	}

	private void parse(File file) throws Exception {
		try {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser parser = saxFactory.newSAXParser();
			//parser.validate(true);  // *** OPTIONAL ***
			//parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.parse(file, this);
		} catch (Exception e) {
			File dir1 = new File (".");
			dLog.trace("Current directory: " + dir1.getCanonicalPath());
			dLog.error("Exception in parse: "+e.getMessage());
			e.printStackTrace();
		}
	}

}
