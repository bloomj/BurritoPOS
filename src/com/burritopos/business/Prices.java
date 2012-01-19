/**
 * 
 */
package com.burritopos.business;

import java.io.File;
import java.math.BigDecimal;
import org.apache.log4j.*;
import java.util.Date;

/**
 * @author james.bloom
 *
 */
public final class Prices {
        private static Logger dLog = Logger.getLogger(Prices.class);

	/**
	 * 
	 * @param name
	 * @return price of given item, -1 if price retrieval was unsuccessful
	 * @throws Exception
	 */
	public static BigDecimal getItemPrice (String name) throws Exception
	{
		BigDecimal retVal = new BigDecimal(-1);
		
		try {
		    java.util.Properties props = new java.util.Properties();
		    //java.io.FileInputStream fis = new java.io.FileInputStream("config/prices.txt");
		    java.io.FileInputStream fis = new java.io.FileInputStream("config/prices.xml");
		    //props.load(fis);
		    props.loadFromXML(fis);
		    fis.close();
		    dLog.trace(new Date() + " | Got " + props.size() + " price categories");
		    String tPrice = props.getProperty(name);
                    dLog.info(new Date() + " | Got " + tPrice + " from prices.txt file for name: " + name);
		    retVal = BigDecimal.valueOf(Double.valueOf(tPrice));
		}
		catch(Exception e) {
                    	File dir1 = new File (".");
                        dLog.trace(new Date() + " | Current directory: " + dir1.getCanonicalPath());
			dLog.error(new Date() + " | Exception in getImplName: "+e.getMessage());
			retVal = new BigDecimal(-1);
		}
		
		return retVal;
	}
}
