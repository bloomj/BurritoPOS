/**
 * 
 */
package com.burritopos.business.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
//import java.util.UUID;
import org.springframework.context.*;
import org.springframework.context.support.*;

//import org.apache.log4j.*;
//import java.util.Date;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.business.OrderManager;

/**
 * @author james.bloom
 *
 */
public class OrderManagerTestCase extends TestCase {
	private OrderManager oManager;
	private Order o;
	private Burrito b;
	private Random rand;
	//private static Logger dLog = Logger.getLogger(OrderManagerTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
		oManager = (OrderManager)context.getBean("OrderManager");
		//oManager = new OrderManager();
		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		rand = new Random();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#createOrder(com.burritopos.domain.Order)}.
	 */
	public void testCreateOrder() throws AssertionFailedError {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
			System.out.println("Start testCreateOrder");
			assertTrue(oManager.createOrder(o));
			
			assertTrue(oManager.cancelOrder(o));
			System.out.println("Finish testCreateOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testCreateOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#addBurritoToOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 */
	public void testAddBurritoToOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			System.out.println("Start testAddBurritoToOrder");
			assertTrue(oManager.createOrder(o));
			
			assertTrue(oManager.addBurritoToOrder(o, b));
			
			assertTrue(oManager.cancelOrder(o));
			System.out.println("Finish testAddBurritoToOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testAddBurritoToOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#editBurritoToOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 */
	public void testEditBurritoToOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
			System.out.println("Start testEditBurritoToOrder");
			assertTrue(oManager.createOrder(o));

            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			assertTrue(oManager.addBurritoToOrder(o, b));
			
			assertTrue(oManager.updateBurritoInOrder(o, b));
			
			assertTrue(oManager.cancelOrder(o));
			System.out.println("Finish testEditBurritoToOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testEditBurritoToOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link com.burritopos.business.OrderManager#removeBurritoFromOrder(com.burritopos.domain.Order, com.burritopos.domain.Burrito)}.
	 */
	public void testRemoveBurritoFromOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			System.out.println("Start testRemoveBurritoFromOrder");
			assertTrue(oManager.createOrder(o));

			assertTrue(oManager.addBurritoToOrder(o, b));
			
			assertTrue(oManager.removeBurritoFromOrder(o, b));
			
			assertTrue(oManager.cancelOrder(o));
			System.out.println("Finish testRemoveBurritoFromOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testRemoveBurritoFromOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for Invalid Removal of Burrito from Order
	 */
	public void testInvalidRemoveBurritoFromOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			System.out.println("Start testInvalidRemoveBurritoFromOrder");
			assertTrue(oManager.createOrder(o));
			
			assertFalse(oManager.removeBurritoFromOrder(o, b));
			
			assertTrue(oManager.cancelOrder(o));
			System.out.println("Finish testInvalidRemoveBurritoFromOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidRemoveBurritoFromOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link com.burritopos.business.OrderManager#submitOrder(com.burritopos.domain.Order)}.
	 */
	public void testSubmitOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
            System.out.println("\nStart testSubmitOrder");
			assertTrue(oManager.createOrder(o));
			System.out.println("Order id: " + o.getOrderID());

			assertTrue(oManager.addBurritoToOrder(o, b));
			
			o.setTotalCost(oManager.calculateTotal(o));
			if(o.getTotalCost().compareTo(new BigDecimal("-1")) != 1) {
				fail("Invalid total calculated for Order");
			}
			
			assertTrue(oManager.submitOrder(o));
			
			assertTrue(oManager.cancelOrder(o));
            System.out.println("Finish testSubmitOrder\n");
		}
		catch(Exception e) {
			System.out.println("Exception in testSubmitOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for Invalid order
	 */
	public void testInvalidSubmitOrder() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
            System.out.println("\nStart testInvalidSubmitOrder");
			assertTrue(oManager.createOrder(o));
			
			assertTrue(oManager.addBurritoToOrder(o, b));
			
			assertFalse(oManager.submitOrder(o));
			
			assertTrue(oManager.cancelOrder(o));
            System.out.println("Finish testInvalidSubmitOrder\n");
		}
		catch(Exception e) {
			System.out.println("Exception in testSubmitOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#calculateTotal(com.burritopos.domain.Order)}.
	 */
	public void testCalculateTotal() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
			assertTrue(oManager.createOrder(o));
			
			//randomly create some burritos to add to order
			int total = rand.nextInt(5)+1;
			for(int n=0; n<total; n++) {
				oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
			}
			
			o.setTotalCost(oManager.calculateTotal(o));
			System.out.println("Total of Order: " + o.getTotalCost() + " | Number of Burritos: " + total);
			if(o.getTotalCost().compareTo(new BigDecimal("-1")) != 1) {
				fail("Invalid total calculated for Order");
			}
			
			assertTrue(oManager.cancelOrder(o));
		}
		catch(Exception e) {
			System.out.println("Exception in testCalculateTotal: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.OrderManager#getOrderHistories()}.
	 */
	public void testgetOrderHistories() {
		try {
            o = new Order(rand.nextInt(1000000),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
			assertTrue(oManager.createOrder(o));
			
			assertNotNull(oManager.getOrderHistories());
			
			assertTrue(oManager.cancelOrder(o));
		}
		catch(Exception e) {
			System.out.println("Exception in testgetOrderHistories: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
