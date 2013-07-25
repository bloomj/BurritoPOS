/**
 * 
 */
package com.burritopos.business;

import java.io.File;
import java.math.BigDecimal;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;

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
			dLog.trace("Got " + props.size() + " price categories");
			String tPrice = props.getProperty(name);
			dLog.info("Got " + tPrice + " from prices.txt file for name: " + name);
			retVal = BigDecimal.valueOf(Double.valueOf(tPrice));
		}
		catch(Exception e) {
			File dir1 = new File (".");
			dLog.trace("Current directory: " + dir1.getCanonicalPath());
			dLog.error("Exception in getItemPrice", e);
		}

		return retVal;
	}
	
	public static BigDecimal calculateTotal (Burrito b) throws Exception
	{
		BigDecimal result = new BigDecimal(-1);
		
		if(b.validate()) {
			result = new BigDecimal(0);
			
			// first determine base type of Chicken, Beef, or Hummus  then determine Extras
			if(b.isBeef()) {
				result = result.add(Prices.getItemPrice("BeefBurrito"));

				//add extra main
				if(b.isChicken()) {
					result = result.add(Prices.getItemPrice("ExtraMeat"));
				}
				if(b.isHummus()) {
					result = result.add(Prices.getItemPrice("ExtraBeans"));
				}
			}
			else if(b.isChicken()) {
				result = result.add(Prices.getItemPrice("ChickenBurrito"));

				//add extra main
				if(b.isBeef()) {
					result = result.add(Prices.getItemPrice("ExtraMeat"));
				}
				if(b.isHummus()) {
					result = result.add(Prices.getItemPrice("ExtraBeans"));
				}
			}
			else if(b.isHummus()) {
				result = result.add(Prices.getItemPrice("HummusBurrito"));
			}

			// calculate remaining extras
			if(b.isChiliTortilla() || b.isHerbGarlicTortilla() || b.isJalapenoCheddarTortilla() || b.isTomatoBasilTortilla() || b.isWheatTortilla()) {
				result = result.add(Prices.getItemPrice("FlavoredTortilla"));
			}

			if(b.isWhiteRice() && b.isBrownRice()) {
				result = result.add(Prices.getItemPrice("ExtraRice"));
			}

			if(b.isBlackBeans() && b.isPintoBeans()) {
				result = result.add(Prices.getItemPrice("ExtraBeans"));
			}

			if(b.isSalsaPico() && b.isSalsaSpecial() && b.isSalsaVerde()) {
				result = result.add(Prices.getItemPrice("ExtraSalsa"));
			}

			if(b.isGuacamole()) {
				result = result.add(Prices.getItemPrice("Guacamole"));
			}
		}

		return result;
	}
}
