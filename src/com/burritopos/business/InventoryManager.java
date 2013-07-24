/**
 * 
 */
package com.burritopos.business;

import java.util.UUID;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;
import com.burritopos.service.Factory;
import com.burritopos.service.dao.IInventorySvc;
import com.burritopos.service.dao.IOrderSvc;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
@SuppressWarnings("unused")
public class InventoryManager extends FactoryManager {
	@Autowired
	private IInventorySvc inventorySvc;
    private static Logger dLog = Logger.getLogger(InventoryManager.class);

	/**
	 * 
	 * @throws ServiceLoadException
	 */
	public InventoryManager() throws ServiceLoadException {
	}

	// let's do this layer via annotations
	/*public void setInventorySvc(IInventorySvc inventorySvc) {
        this.inventorySvc = inventorySvc;
    }*/

	/**
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public boolean createInventory(Inventory i) throws Exception {
		boolean result = false;

		if(i != null)
			dLog.trace("Inventory validation: "+ i.validate());
		else
			dLog.trace("Inventory is null");

		if(i.validate()) {
			if(inventorySvc.storeInventory(i))
				result = true;
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

		if(i.validate()) {
			if(inventorySvc.storeInventory(i))
				result = true;
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

		if(i.validate()) {
			if(inventorySvc.deleteInventory(i.getId()));
			result = true;
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

		if(i.validate() && o.validate()) {

			// loop through all burritos on the order
			for(int n=0; n < o.getBurritos().size(); n++) {
				i = updateInventory(i,o.getBurritos().get(n),true);		
			}

			// ensure the inventory gets updated
			if(inventorySvc.storeInventory(i)) {
				result = true;
			}
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

		if(i.validate() && o.validate()) {

			// loop through all burritos on the order
			for(int n=0; n < o.getBurritos().size(); n++) {
				i = updateInventory(i,o.getBurritos().get(n),false);	
			}

			// ensure the inventory gets updated
			if(inventorySvc.storeInventory(i)) {
				result = true;
			}
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

		if(i.validate()) {
			i = updateInventory(i,b,true);

			// ensure the inventory gets updated
			if(inventorySvc.storeInventory(i)) {
				result = true;
			}
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

		if(i.validate()) {
			i = updateInventory(i,b,false);

			// ensure the inventory gets updated
			if(inventorySvc.storeInventory(i)) {
				result = true;
			}	
		}	

		return result;
	}

	public Inventory getInventory(Integer id) throws Exception {
		Inventory result;

		result = inventorySvc.getInventory(id);

		return result;
	}
	
	private Inventory updateInventory(Inventory i, Burrito b, boolean remove) throws Exception {
		int quantity = 1;
		if(remove) {
			quantity = -1;
		}
		
		if(b.validate()) {
			if(b.isBeef()) {i.setBeefQty(i.getBeefQty()+quantity);}
			if(b.isChicken()) {i.setChickenQty(i.getChickenQty()+quantity);}
			if(b.isHummus()) {i.setHummusQty(i.getHummusQty()+quantity);}

			// calculate remaining extras
			if(b.isChiliTortilla()) {i.setChiliTortillaQty(i.getChiliTortillaQty()+quantity);}
			else if(b.isHerbGarlicTortilla()) {i.setHerbGarlicTortillaQty(i.getHerbGarlicTortillaQty()+quantity);}
			else if(b.isJalapenoCheddarTortilla()) {i.setJalapenoCheddarTortillaQty(i.getJalapenoCheddarTortillaQty()+quantity);}					
			else if(b.isTomatoBasilTortilla()) {i.setTomatoBasilTortillaQty(i.getTomatoBasilTortillaQty()+quantity);}				
			else if(b.isWheatTortilla()) {i.setWheatTortillaQty(i.getWheatTortillaQty()+quantity);}
			else if(b.isFlourTortilla()) {i.setFlourTortillaQty(i.getFlourTortillaQty()+quantity);}

			if(b.isWhiteRice()) {i.setWhiteRiceQty(i.getWhiteRiceQty()+quantity);}
			if(b.isBrownRice()) {i.setBrownRiceQty(i.getBrownRiceQty()+quantity);}

			if(b.isBlackBeans()) {i.setBlackBeansQty(i.getBlackBeansQty()+quantity);}
			if(b.isPintoBeans()) {i.setPintoBeansQty(i.getPintoBeansQty()+quantity);}	

			if(b.isSalsaPico()) {i.setSalsaPicoQty(i.getSalsaPicoQty()+quantity);}
			if(b.isSalsaSpecial()) {i.setSalsaSpecialQty(i.getSalsaSpecialQty()+quantity);}
			if(b.isSalsaVerde()) {i.setSalsaVerdeQty(i.getSalsaVerdeQty()+quantity);}

			if(b.isGuacamole()) {i.setGuacamoleQty(i.getGuacamoleQty()+quantity);}

			if(b.isCucumber()) {i.setCucumberQty(i.getCucumberQty()+quantity);}
			if(b.isJalapenos()) {i.setJalapenosQty(i.getJalapenosQty()+quantity);}
			if(b.isLettuce()) {i.setLettuceQty(i.getLettuceQty()+quantity);}
			if(b.isOnion()) {i.setOnionQty(i.getOnionQty()+quantity);}
			if(b.isTomatoes()) {i.setTomatoesQty(i.getTomatoesQty()+quantity);}
		}
		
		return i;
	}
}
