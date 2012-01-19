/**
 * 
 */
package com.burritopos.business;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;
//import java.util.UUID;

import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;
import com.burritopos.service.*;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class OrderManager extends FactoryManager {
	//private Factory factory = Factory.getInstance();
	private IOrderSvc orderSvc;
	private static Logger dLog = Logger.getLogger(OrderManager.class);

	/**
	 * 
	 * @throws ServiceLoadException
	 */
	public OrderManager() throws ServiceLoadException {
		//orderSvc = (IOrderSvc) factory.getService(IOrderSvc.NAME);
	}

        public void setOrderSvc(IOrderSvc orderSvc) {
            this.orderSvc = orderSvc;
        }

	public ArrayList<Order> getOrderHistories() {
		ArrayList<Order> result = new ArrayList<Order>();
		
		try {
		    File dir1 = new File (".");
		    dLog.trace(new Date() + " | Current directory: " + dir1.getCanonicalPath());
		    
		    String[] children = dir1.list(); 
		    if (children != null) { 
		    	for (int i=0; i<children.length; i++) { 
		    		// Get filename of file or directory 
		    		String filename = children[i];
		    		dLog.trace(new Date() + " |   - On file: " + filename);
		    		StringTokenizer st = new StringTokenizer(filename, "_");
		    		String firstTok = st.nextToken();
		    		dLog.trace(new Date() + " |   - First Token: " + firstTok);
		    		if(firstTok.equalsIgnoreCase("Order")) {
		    			String secondTok = st.nextToken();
		    			dLog.trace(new Date() + " |   - Second Token: " + secondTok);
		    			String[] parts = secondTok.split("\\.");
		    			dLog.trace(new Date() + " |   - Parts Length: " + parts.length);
		    			if(parts.length > 0) {
		    				Integer tOrderID = Integer.parseInt(parts[0]);
		    				dLog.trace(new Date() + " |   - Found order: " + tOrderID);
		    				
		    				//add this file 
		    				result.add(orderSvc.getOrder(tOrderID));
		    			}
		    		}
		    	}
		    }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getOrderHistories: "+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param o
	 * @return success of operation
	 * @throws Exception
	 */
	public boolean createOrder(Order o) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate()) {
				if(orderSvc.storeOrder(o))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in createOrder: "+e.getMessage());
			result = false;
		}
		
		return result;
	}

	/**
	 * 
	 * @param o
	 * @return success of operation
	 * @throws Exception
	 */
	public boolean updateOrder(Order o) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate()) {
				if(orderSvc.storeOrder(o))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in createOrder: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	public boolean addBurritoToOrder(Order o, Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate() && b.validate()) {
				o.getBurritos().add(b);
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in addBurritoToOrder: "+e.getMessage());
			result = false;			
		}
		
		return result;	
	}
	
	public boolean updateBurritoInOrder(Order o, Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate() && b.validate()) {		
				for(int n=0; n<o.getBurritos().size(); n++) {
					if(b.getId() == o.getBurritos().get(n).getId()) {
						o.getBurritos().set(n, b);
						result = true;
						break;
					}
				}
			}
		}
		catch(Exception e) {
                        dLog.error(new Date() + " | Exception in removeBurritoFromOrder: "+e.getMessage());
			result = false;		
		}
		
		return result;		
	}
	
	public boolean removeBurritoFromOrder(Order o, Burrito b) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate() && b.validate()) {		
				for(int n=0; n<o.getBurritos().size(); n++) {
					if(b.equals(o.getBurritos().get(n))) {
						o.getBurritos().remove(n);
						result = true;
						break;
					}
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in removeBurritoFromOrder: "+e.getMessage());
			result = false;		
		}
		
		return result;
	}
	
	public boolean submitOrder(Order o) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate()) {
                                System.out.println("Order id: " + o.getOrderID() + " | Order size: " + o.getBurritos().size() + " | cost: " + o.getTotalCost().intValue());
				//ensure we have at least one burrito and the cost of the burritos has been calculated
				if(o.getBurritos().size() > 0 && o.getTotalCost().intValue() > 0) {
					// set order submitted flag
					o.setIsSubmitted(true);
					
					// store updated Order
					if(orderSvc.storeOrder(o)) {
						result = true;
					}
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in submitOrder: "+e.getMessage());
			o.setIsSubmitted(false);
			result = false;		
		}
		
		return result;
	}
	
	public boolean cancelOrder(Order o) throws Exception {
		boolean result = false;
		
		try {
			if(o.validate()) {	
				if(orderSvc.deleteOrder(o.getOrderID()))
					result = true;
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in cancelOrder: "+e.getMessage());
			result = false;		
		}
		
		return result;
	}
	
	public BigDecimal calculateTotal(Order o) throws Exception {
		BigDecimal result = new BigDecimal(-1);
		
		try {
			// check for valid order
			if(o.validate()){
				result = new BigDecimal(0);
				
				// loop through all burritos on the order
				for(int n=0; n < o.getBurritos().size(); n++) {
					// first determine base type of Chicken, Beef, or Hummus  then determine Extras
					if(o.getBurritos().get(n).isBeef()) {
						result = result.add(Prices.getItemPrice("BeefBurrito"));
						
						//add extra main
						if(o.getBurritos().get(n).isChicken())
							result = result.add(Prices.getItemPrice("ExtraMeat"));
						if(o.getBurritos().get(n).isHummus())
							result = result.add(Prices.getItemPrice("ExtraBeans"));
					}
					else if(o.getBurritos().get(n).isChicken()) {
						result = result.add(Prices.getItemPrice("ChickenBurrito"));
						
						//add extra main
						if(o.getBurritos().get(n).isBeef())
							result = result.add(Prices.getItemPrice("ExtraMeat"));
						if(o.getBurritos().get(n).isHummus())
							result = result.add(Prices.getItemPrice("ExtraBeans"));
					}
					else if(o.getBurritos().get(n).isHummus()) {
						result = result.add(Prices.getItemPrice("HummusBurrito"));
					}
					
					// calculate remaining extras
					if(o.getBurritos().get(n).isChiliTortilla() || o.getBurritos().get(n).isHerbGarlicTortilla() || o.getBurritos().get(n).isJalapenoCheddarTortilla() || o.getBurritos().get(n).isTomatoBasilTortilla() || o.getBurritos().get(n).isWheatTortilla())
						result = result.add(Prices.getItemPrice("FlavoredTortilla"));
					
					if(o.getBurritos().get(n).isWhiteRice() && o.getBurritos().get(n).isBrownRice())
						result = result.add(Prices.getItemPrice("ExtraRice"));
					
					if(o.getBurritos().get(n).isBlackBeans() && o.getBurritos().get(n).isPintoBeans())
						result = result.add(Prices.getItemPrice("ExtraBeans"));
					
					if(o.getBurritos().get(n).isSalsaPico() && o.getBurritos().get(n).isSalsaSpecial() && o.getBurritos().get(n).isSalsaVerde())
						result = result.add(Prices.getItemPrice("ExtraSalsa"));
					
					if(o.getBurritos().get(n).isGuacamole())
						result = result.add(Prices.getItemPrice("Guacamole"));
				}
					
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in calculateTotal: "+e.getMessage());
			result = new BigDecimal(-1);
		}
		
		return result;	
	}
}
