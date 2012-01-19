/**
 * 
 */
package com.burritopos.business;

import java.math.BigDecimal;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.exception.ServiceLoadException;
//import com.burritopos.service.Factory;
import com.burritopos.service.IBurritoSvc;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class BurritoManager extends FactoryManager {
	//private Factory factory = Factory.getInstance();
	private IBurritoSvc burritoSvc;
    private static Logger dLog = Logger.getLogger(BurritoManager.class);
        
	/**
	 * 
	 * @throws ServiceLoadException
	 */
	public BurritoManager() throws ServiceLoadException {
		//burritoSvc = (IBurritoSvc) factory.getService(IBurritoSvc.NAME);
	}

    public void setBurritoSvc(IBurritoSvc burritoSvc) {
    	this.burritoSvc = burritoSvc;
    }

	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean createBurrito(Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(b.validate()) {
				if(burritoSvc.storeBurrito(b))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in createBurrito: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean updateBurrito(Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(b.validate()) {
				if(burritoSvc.storeBurrito(b))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in updateBurrito: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBurrito(Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(b.validate()) {	
				if(burritoSvc.deleteBurrito(b.getId()))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteBurrito: "+e.getMessage());
			result = false;		
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculatePrice(Burrito b) throws Exception {
		BigDecimal result = new BigDecimal(-1);
		
		try {
			// check for valid burrito
			if(b.validate()){
				result = new BigDecimal(0);
				
				// first determine base type of Chicken, Beef, or Hummus  then determine Extras
				if(b.isBeef()) {
					result = result.add(Prices.getItemPrice("BeefBurrito"));
						
					//add extra main
					if(b.isChicken())
						result = result.add(Prices.getItemPrice("ExtraMeat"));
					if(b.isHummus())
						result = result.add(Prices.getItemPrice("ExtraBeans"));
				}
				else if(b.isChicken()) {
					result = result.add(Prices.getItemPrice("ChickenBurrito"));
						
					//add extra main
					if(b.isBeef())
						result = result.add(Prices.getItemPrice("ExtraMeat"));
					if(b.isHummus())
						result = result.add(Prices.getItemPrice("ExtraBeans"));
				}
				else if(b.isHummus()) {
					result = result.add(Prices.getItemPrice("HummusBurrito"));
				}
					
				// calculate remaining extras
				if(b.isChiliTortilla() || b.isHerbGarlicTortilla() || b.isJalapenoCheddarTortilla() || b.isTomatoBasilTortilla() || b.isWheatTortilla())
					result = result.add(Prices.getItemPrice("FlavoredTortilla"));
					
				if(b.isWhiteRice() && b.isBrownRice())
					result = result.add(Prices.getItemPrice("ExtraRice"));
					
				if(b.isBlackBeans() && b.isPintoBeans())
					result = result.add(Prices.getItemPrice("ExtraBeans"));
					
				if(b.isSalsaPico() && b.isSalsaSpecial() && b.isSalsaVerde())
					result = result.add(Prices.getItemPrice("ExtraSalsa"));
					
				if(b.isGuacamole())
					result = result.add(Prices.getItemPrice("Guacamole"));	
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in calculatePrice: "+e.getMessage());
			result = new BigDecimal(-1);
		}
		
		return result;	
	}
	
	public String getBurritoType(Burrito b) {
		String result = "Unknown";
		
		try {
			if(b.isBeef()) {
				result = "Beef";
			}
			else if(b.isChicken()) {
				result = "Chicken";
			}
			else if(b.isHummus()) {
				result = "Hummus";
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getBurritoType: "+e.getMessage());
			result = "Unknown";			
		}
		
		return result;
	}
}
