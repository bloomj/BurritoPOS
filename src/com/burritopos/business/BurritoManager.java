/**
 * 
 */
package com.burritopos.business;

import java.math.BigDecimal;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
//import com.burritopos.service.Factory;
import com.burritopos.service.dao.IBurritoSvc;


/**
 * @author james.bloom
 *
 */
public class BurritoManager extends FactoryManager {
	@Autowired
	private IBurritoSvc burritoSvc;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(BurritoManager.class);

	/**
	 * 
	 */
	public BurritoManager() {

	}

	// let's do this layer via annotations
	/*public void setBurritoSvc(IBurritoSvc burritoSvc) {
    	this.burritoSvc = burritoSvc;
    }*/

	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public boolean createBurrito(Burrito b) throws Exception {
		boolean result = false;

		if(b.validate()) {
			if(burritoSvc.storeBurrito(b))
				result = true;
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

		if(b.validate()) {
			if(burritoSvc.storeBurrito(b))
				result = true;
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

		if(b.validate()) {	
			if(burritoSvc.deleteBurrito(b.getId()))
				result = true;
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
		return Prices.calculateTotal(b);	
	}

	public String getBurritoType(Burrito b) {
		String result = "Unknown";

		if(b.isBeef()) {
			result = "Beef";
		}
		else if(b.isChicken()) {
			result = "Chicken";
		}
		else if(b.isHummus()) {
			result = "Hummus";
		}

		return result;
	}
}
