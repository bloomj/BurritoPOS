/**
 * 
 */
package com.burritopos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.UUID;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.service.dao.IOrderSvc;

/**
 * @author james.bloom
 *
 */
public class OrderManager extends FactoryManager {
	@Autowired
	private IOrderSvc orderSvc;
	private static Logger dLog = Logger.getLogger(OrderManager.class);

	/**
	 * 
	 */
	public OrderManager() {

	}

	// let's do this layer via annotations
	/*public void setOrderSvc(IOrderSvc orderSvc) {
		this.orderSvc = orderSvc;
	}*/

	/**
	 * Gets all orders
	 */
	public ArrayList<Order> getOrderHistories() throws Exception {
		return orderSvc.getAllOrders();
	}

	/**
	 * 
	 * @param o
	 * @return success of operation
	 * @throws Exception
	 */
	public boolean createOrder(Order o) throws Exception {
		boolean result = false;

		if(o.validate()) {
			if(orderSvc.storeOrder(o))
				result = true;
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

		if(o.validate()) {
			if(orderSvc.deleteOrder(o.getOrderID()))
				result = true;
		}

		return result;
	}

	/**
	 * 
	 * @param o
	 * @return success of operation
	 * @throws Exception
	 */
	public boolean deleteOrder(Order o) throws Exception {
		boolean result = false;

		if(o.validate()) {
			if(orderSvc.storeOrder(o))
				result = true;
		}

		return result;
	}

	public boolean addBurritoToOrder(Order o, Burrito b) throws Exception {
		boolean result = false;

		if(o.validate() && b.validate()) {
			o.getBurritos().add(b);
			result = true;
		}

		return result;	
	}

	public boolean updateBurritoInOrder(Order o, Burrito b) throws Exception {
		boolean result = false;

		if(o.validate() && b.validate()) {		
			for(int n=0; n<o.getBurritos().size(); n++) {
				if(b.getId() == o.getBurritos().get(n).getId()) {
					o.getBurritos().set(n, b);
					result = true;
					break;
				}
			}
		}

		return result;		
	}

	public boolean removeBurritoFromOrder(Order o, Burrito b) throws Exception {
		boolean result = false;

		if(o.validate() && b.validate()) {		
			for(int n=0; n<o.getBurritos().size(); n++) {
				if(b.equals(o.getBurritos().get(n))) {
					o.getBurritos().remove(n);
					result = true;
					break;
				}
			}
		}

		return result;
	}

	public boolean submitOrder(Order o) throws Exception {
		boolean result = false;
		o.setIsSubmitted(false);

		if(o.validate()) {
			dLog.trace("Order id: " + o.getOrderID() + " | Order size: " + o.getBurritos().size() + " | cost: " + o.getTotalCost().intValue());
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

		return result;
	}

	public boolean cancelOrder(Order o) throws Exception {
		boolean result = false;

		if(o.validate()) {	
			if(orderSvc.deleteOrder(o.getOrderID()))
				result = true;
		}

		return result;
	}

	public BigDecimal calculateTotal(Order o) throws Exception {
		BigDecimal result = new BigDecimal(-1);

		// check for valid order
		if(o.validate()) {
			result = new BigDecimal(0);

			// loop through all burritos on the order
			for(int n=0; n < o.getBurritos().size(); n++) {
				result = result.add(Prices.calculateTotal(o.getBurritos().get(n)));
			}
		}

		return result;	
	}
}
