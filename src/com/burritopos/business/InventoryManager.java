/**
 * 
 */
package com.burritopos.business;

import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;
import com.burritopos.service.Factory;
import com.burritopos.service.IInventorySvc;
import com.burritopos.service.IOrderSvc;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
@SuppressWarnings("unused")
public class InventoryManager extends FactoryManager {
	//private Factory factory = Factory.getInstance();
	private IInventorySvc inventorySvc;
        private static Logger dLog = Logger.getLogger(InventoryManager.class);

	/**
	 * 
	 * @throws ServiceLoadException
	 */
	public InventoryManager() throws ServiceLoadException {
		//inventorySvc = (IInventorySvc) factory.getService(IInventorySvc.NAME);
	}

    public void setInventorySvc(IInventorySvc inventorySvc) {
        this.inventorySvc = inventorySvc;
    }

	/**
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public boolean createInventory(Inventory i) throws Exception {
		boolean result = false;
		
		try {
			if(i != null)
				dLog.trace(new Date() + " | Inventory validation: "+ i.validate());
			else
				dLog.trace(new Date() + " | Inventory is null");
			
			if(i.validate()) {
				if(inventorySvc.storeInventory(i))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in createInventory: "+e.getMessage() + " | " + e.getCause());
			dLog.trace(new Date() + " | Stack Trace: "+e.getStackTrace().toString());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public boolean updateInventory(Inventory i) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate()) {
				if(inventorySvc.storeInventory(i))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in updateInventory: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInventory(Inventory i) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate()) {
				if(inventorySvc.deleteInventory(i.getId()));
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteInventory: "+e.getMessage());
			result = false;		
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public boolean removeFromInventory(Inventory i, Order o) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate() && o.validate()) {
				
				// loop through all burritos on the order
				for(int n=0; n < o.getBurritos().size(); n++) {

					if(o.getBurritos().get(n).isBeef()) {i.setBeefQty(i.getBeefQty()-1);}
					if(o.getBurritos().get(n).isChicken()) {i.setChickenQty(i.getChickenQty()-1);}
					if(o.getBurritos().get(n).isHummus()) {i.setHummusQty(i.getHummusQty()-1);}
					
					// calculate remaining extras
					if(o.getBurritos().get(n).isChiliTortilla()) {i.setChiliTortillaQty(i.getChiliTortillaQty()-1);}
					else if(o.getBurritos().get(n).isHerbGarlicTortilla()) {i.setHerbGarlicTortillaQty(i.getHerbGarlicTortillaQty()-1);}
					else if(o.getBurritos().get(n).isJalapenoCheddarTortilla()) {i.setJalapenoCheddarTortillaQty(i.getJalapenoCheddarTortillaQty()-1);}					
					else if(o.getBurritos().get(n).isTomatoBasilTortilla()) {	i.setTomatoBasilTortillaQty(i.getTomatoBasilTortillaQty()-1);}				
					else if(o.getBurritos().get(n).isWheatTortilla()) {i.setWheatTortillaQty(i.getWheatTortillaQty()-1);}
					else if(o.getBurritos().get(n).isFlourTortilla()) {i.setFlourTortillaQty(i.getFlourTortillaQty()-1);}
					
					if(o.getBurritos().get(n).isWhiteRice()) {i.setWhiteRiceQty(i.getWhiteRiceQty()-1);}
					if(o.getBurritos().get(n).isBrownRice()) {i.setBrownRiceQty(i.getBrownRiceQty()-1);}
					
					if(o.getBurritos().get(n).isBlackBeans()) {i.setBlackBeansQty(i.getBlackBeansQty()-1);}
					if(o.getBurritos().get(n).isPintoBeans()) {i.setPintoBeansQty(i.getPintoBeansQty()-1);}	
					
					if(o.getBurritos().get(n).isSalsaPico()) {i.setSalsaPicoQty(i.getSalsaPicoQty()-1);}
					if(o.getBurritos().get(n).isSalsaSpecial()) {i.setSalsaSpecialQty(i.getSalsaSpecialQty()-1);}
					if(o.getBurritos().get(n).isSalsaVerde()) {i.setSalsaVerdeQty(i.getSalsaVerdeQty()-1);}
						
					if(o.getBurritos().get(n).isGuacamole()) {i.setGuacamoleQty(i.getGuacamoleQty()-1);}
					
					if(o.getBurritos().get(n).isCucumber()) {i.setCucumberQty(i.getCucumberQty()-1);}
					if(o.getBurritos().get(n).isJalapenos()) {i.setJalapenosQty(i.getJalapenosQty()-1);}
					if(o.getBurritos().get(n).isLettuce()) {i.setLettuceQty(i.getLettuceQty()-1);}
					if(o.getBurritos().get(n).isOnion()) {i.setOnionQty(i.getOnionQty()-1);}
					if(o.getBurritos().get(n).isTomatoes()) {i.setTomatoesQty(i.getTomatoesQty()-1);}		
				}
				
				// ensure the inventory gets updated
				if(inventorySvc.storeInventory(i)) {
					result = true;
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in removeFromInventory: "+e.getMessage());
			result = false;	
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public boolean returnToInventory(Inventory i, Order o) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate() && o.validate()) {
				
				// loop through all burritos on the order
				for(int n=0; n < o.getBurritos().size(); n++) {

					if(o.getBurritos().get(n).isBeef()) {i.setBeefQty(i.getBeefQty()+1);}
					if(o.getBurritos().get(n).isChicken()) {i.setChickenQty(i.getChickenQty()+1);}
					if(o.getBurritos().get(n).isHummus()) {i.setHummusQty(i.getHummusQty()+1);}
					
					// calculate remaining extras
					if(o.getBurritos().get(n).isChiliTortilla()) {i.setChiliTortillaQty(i.getChiliTortillaQty()+1);}
					else if(o.getBurritos().get(n).isHerbGarlicTortilla()) {i.setHerbGarlicTortillaQty(i.getHerbGarlicTortillaQty()+1);}
					else if(o.getBurritos().get(n).isJalapenoCheddarTortilla()) {i.setJalapenoCheddarTortillaQty(i.getJalapenoCheddarTortillaQty()+1);}					
					else if(o.getBurritos().get(n).isTomatoBasilTortilla()) {i.setTomatoBasilTortillaQty(i.getTomatoBasilTortillaQty()+1);}				
					else if(o.getBurritos().get(n).isWheatTortilla()) {i.setWheatTortillaQty(i.getWheatTortillaQty()+1);}
					else if(o.getBurritos().get(n).isFlourTortilla()) {i.setFlourTortillaQty(i.getFlourTortillaQty()+1);}
					
					if(o.getBurritos().get(n).isWhiteRice()) {i.setWhiteRiceQty(i.getWhiteRiceQty()+1);}
					if(o.getBurritos().get(n).isBrownRice()) {i.setBrownRiceQty(i.getBrownRiceQty()+1);}
					
					if(o.getBurritos().get(n).isBlackBeans()) {i.setBlackBeansQty(i.getBlackBeansQty()+1);}
					if(o.getBurritos().get(n).isPintoBeans()) {i.setPintoBeansQty(i.getPintoBeansQty()+1);}	
					
					if(o.getBurritos().get(n).isSalsaPico()) {i.setSalsaPicoQty(i.getSalsaPicoQty()+1);}
					if(o.getBurritos().get(n).isSalsaSpecial()) {i.setSalsaSpecialQty(i.getSalsaSpecialQty()+1);}
					if(o.getBurritos().get(n).isSalsaVerde()) {i.setSalsaVerdeQty(i.getSalsaVerdeQty()+1);}
						
					if(o.getBurritos().get(n).isGuacamole()) {i.setGuacamoleQty(i.getGuacamoleQty()+1);}
					
					if(o.getBurritos().get(n).isCucumber()) {i.setCucumberQty(i.getCucumberQty()+1);}
					if(o.getBurritos().get(n).isJalapenos()) {i.setJalapenosQty(i.getJalapenosQty()+1);}
					if(o.getBurritos().get(n).isLettuce()) {i.setLettuceQty(i.getLettuceQty()+1);}
					if(o.getBurritos().get(n).isOnion()) {i.setOnionQty(i.getOnionQty()+1);}
					if(o.getBurritos().get(n).isTomatoes()) {i.setTomatoesQty(i.getTomatoesQty()+1);}		
				}
				
				// ensure the inventory gets updated
				if(inventorySvc.storeInventory(i)) {
					result = true;
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in returnToInventory: "+e.getMessage());
			result = false;	
		}
		
		return result;
	}

	/**
	 * 
	 * @param i
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public boolean removeFromInventory(Inventory i, Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate() && b.validate()) {

				// add burrito ingredients from the order
				if(b.isBeef()) {i.setBeefQty(i.getBeefQty()-1);}
				if(b.isChicken()) {i.setChickenQty(i.getChickenQty()-1);}
				if(b.isHummus()) {i.setHummusQty(i.getHummusQty()-1);}
					
				// calculate remaining extras
				if(b.isChiliTortilla()) {i.setChiliTortillaQty(i.getChiliTortillaQty()-1);}
				else if(b.isHerbGarlicTortilla()) {i.setHerbGarlicTortillaQty(i.getHerbGarlicTortillaQty()-1);}
				else if(b.isJalapenoCheddarTortilla()) {i.setJalapenoCheddarTortillaQty(i.getJalapenoCheddarTortillaQty()-1);}					
				else if(b.isTomatoBasilTortilla()) {	i.setTomatoBasilTortillaQty(i.getTomatoBasilTortillaQty()-1);}				
				else if(b.isWheatTortilla()) {i.setWheatTortillaQty(i.getWheatTortillaQty()-1);}
				else if(b.isFlourTortilla()) {i.setFlourTortillaQty(i.getFlourTortillaQty()-1);}
					
				if(b.isWhiteRice()) {i.setWhiteRiceQty(i.getWhiteRiceQty()-1);}
				if(b.isBrownRice()) {i.setBrownRiceQty(i.getBrownRiceQty()-1);}
					
				if(b.isBlackBeans()) {i.setBlackBeansQty(i.getBlackBeansQty()-1);}
				if(b.isPintoBeans()) {i.setPintoBeansQty(i.getPintoBeansQty()-1);}	
					
				if(b.isSalsaPico()) {i.setSalsaPicoQty(i.getSalsaPicoQty()-1);}
				if(b.isSalsaSpecial()) {i.setSalsaSpecialQty(i.getSalsaSpecialQty()-1);}
				if(b.isSalsaVerde()) {i.setSalsaVerdeQty(i.getSalsaVerdeQty()-1);}
						
				if(b.isGuacamole()) {i.setGuacamoleQty(i.getGuacamoleQty()-1);}
					
				if(b.isCucumber()) {i.setCucumberQty(i.getCucumberQty()-1);}
				if(b.isJalapenos()) {i.setJalapenosQty(i.getJalapenosQty()-1);}
				if(b.isLettuce()) {i.setLettuceQty(i.getLettuceQty()-1);}
				if(b.isOnion()) {i.setOnionQty(i.getOnionQty()-1);}
				if(b.isTomatoes()) {i.setTomatoesQty(i.getTomatoesQty()-1);}		
				
				// ensure the inventory gets updated
				if(inventorySvc.storeInventory(i)) {
					result = true;
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in removeFromInventory: "+e.getMessage());
			result = false;	
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean returnToInventory(Inventory i, Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(i.validate() && b.validate()) {
				
				// remove burrito ingredients from the order
				if(b.isBeef()) {i.setBeefQty(i.getBeefQty()+1);}
				if(b.isChicken()) {i.setChickenQty(i.getChickenQty()+1);}
				if(b.isHummus()) {i.setHummusQty(i.getHummusQty()+1);}
					
				// calculate remaining extras
				if(b.isChiliTortilla()) {i.setChiliTortillaQty(i.getChiliTortillaQty()+1);}
				else if(b.isHerbGarlicTortilla()) {i.setHerbGarlicTortillaQty(i.getHerbGarlicTortillaQty()+1);}
				else if(b.isJalapenoCheddarTortilla()) {i.setJalapenoCheddarTortillaQty(i.getJalapenoCheddarTortillaQty()+1);}					
				else if(b.isTomatoBasilTortilla()) {i.setTomatoBasilTortillaQty(i.getTomatoBasilTortillaQty()+1);}				
				else if(b.isWheatTortilla()) {i.setWheatTortillaQty(i.getWheatTortillaQty()+1);}
				else if(b.isFlourTortilla()) {i.setFlourTortillaQty(i.getFlourTortillaQty()+1);}
					
				if(b.isWhiteRice()) {i.setWhiteRiceQty(i.getWhiteRiceQty()+1);}
				if(b.isBrownRice()) {i.setBrownRiceQty(i.getBrownRiceQty()+1);}
					
				if(b.isBlackBeans()) {i.setBlackBeansQty(i.getBlackBeansQty()+1);}
				if(b.isPintoBeans()) {i.setPintoBeansQty(i.getPintoBeansQty()+1);}	
					
				if(b.isSalsaPico()) {i.setSalsaPicoQty(i.getSalsaPicoQty()+1);}
				if(b.isSalsaSpecial()) {i.setSalsaSpecialQty(i.getSalsaSpecialQty()+1);}
				if(b.isSalsaVerde()) {i.setSalsaVerdeQty(i.getSalsaVerdeQty()+1);}
						
				if(b.isGuacamole()) {i.setGuacamoleQty(i.getGuacamoleQty()+1);}
					
				if(b.isCucumber()) {i.setCucumberQty(i.getCucumberQty()+1);}
				if(b.isJalapenos()) {i.setJalapenosQty(i.getJalapenosQty()+1);}
				if(b.isLettuce()) {i.setLettuceQty(i.getLettuceQty()+1);}
				if(b.isOnion()) {i.setOnionQty(i.getOnionQty()+1);}
				if(b.isTomatoes()) {i.setTomatoesQty(i.getTomatoesQty()+1);}		
				
				// ensure the inventory gets updated
				if(inventorySvc.storeInventory(i)) {
					result = true;
				}	
			}	
		
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in returnToInventory: "+e.getMessage());
			result = false;				
		}
		
		return result;
	}
	
	public Inventory getInventory(Integer id) {
		Inventory result;
		
		try {
			result = inventorySvc.getInventory(id);
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getInventory: "+e.getMessage());
			result = null;						
		}
		
		return result;
	}
}
