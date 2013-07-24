/**
 * 
 */
package com.burritopos.business.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.test.BurritoPOSTestCase;

import junit.framework.AssertionFailedError;

import com.burritopos.business.OrderManager;

/**
 * @author james.bloom
 *
 */
public class OrderManagerTestCase extends BurritoPOSTestCase {
	@Autowired
	private OrderManager oManager;
	private Order o;
	private Burrito b;
	private Random rand;
	private static Logger dLog = Logger.getLogger(OrderManagerTestCase.class);
	
	public OrderManagerTestCase() {
		super();
	}
	
	/**
	 * Sets up the necessary code to run the tests.
	 *
	 * @throws Exception if it cannot set up the test.
	 */
	@Before
	public void initCommonResources() throws Exception {
		super.initCommonResources();

		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		rand = new Random();
	}
	
	/**
	 * Test method for {@link com.burritopos.business.OrderManager#createOrder(com.burritopos.domain.Order)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateOrder() throws AssertionFailedError, Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#addBurritoToOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddBurritoToOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));

		assertTrue(oManager.createOrder(o));

		assertTrue(oManager.addBurritoToOrder(o, b));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#editBurritoToOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testEditBurritoToOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));

		b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		assertTrue(oManager.addBurritoToOrder(o, b));

		assertTrue(oManager.updateBurritoInOrder(o, b));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#removeBurritoFromOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testRemoveBurritoFromOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));

		assertTrue(oManager.createOrder(o));

		assertTrue(oManager.addBurritoToOrder(o, b));

		assertTrue(oManager.removeBurritoFromOrder(o, b));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for Invalid Removal of Burrito from Order
	 * @throws Exception 
	 */
	@Test
	public void testInvalidRemoveBurritoFromOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));

		assertTrue(oManager.createOrder(o));

		assertFalse(oManager.removeBurritoFromOrder(o, b));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#submitOrder(com.burritopos.domain.Order)}.
	 * @throws Exception 
	 */
	@Test
	public void testSubmitOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));
		dLog.trace("Order id: " + o.getOrderID());

		assertTrue(oManager.addBurritoToOrder(o, b));

		o.setTotalCost(oManager.calculateTotal(o));
		if(o.getTotalCost().compareTo(new BigDecimal("-1")) != 1) {
			fail("Invalid total calculated for Order");
		}

		assertTrue(oManager.submitOrder(o));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for Invalid order
	 * @throws Exception 
	 */
	@Test
	public void testInvalidSubmitOrder() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));

		assertTrue(oManager.addBurritoToOrder(o, b));

		assertFalse(oManager.submitOrder(o));

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#calculateTotal(com.burritopos.domain.Order)}.
	 * @throws Exception 
	 */
	@Test
	public void testCalculateTotal() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));

		//randomly create some burritos to add to order
		int total = rand.nextInt(5)+1;
		for(int n=0; n<total; n++) {
			oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
		}

		o.setTotalCost(oManager.calculateTotal(o));
		dLog.trace("Total of Order: " + o.getTotalCost() + " | Number of Burritos: " + total);
		if(o.getTotalCost().compareTo(new BigDecimal("-1")) != 1) {
			fail("Invalid total calculated for Order");
		}

		assertTrue(oManager.cancelOrder(o));
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#getOrderHistories()}.
	 * @throws Exception 
	 */
	@Test
	public void testgetOrderHistories() throws Exception {
		o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		assertTrue(oManager.createOrder(o));

		assertNotNull(oManager.getOrderHistories());

		assertTrue(oManager.cancelOrder(o));
	}
}
