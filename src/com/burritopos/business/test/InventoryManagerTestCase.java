/**
 * 
 */
package com.burritopos.business.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;
import org.springframework.context.*;
import org.springframework.context.support.*;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;
import com.burritopos.domain.Order;
import junit.framework.TestCase;
import com.burritopos.business.InventoryManager;
import com.burritopos.business.OrderManager;

/**
 * @author james.bloom
 *
 */
public class InventoryManagerTestCase extends TestCase {
	private InventoryManager iManager;
	private OrderManager oManager;
	private Inventory i;
	private Order o;
	private Burrito b;
	private Random rand;
	//private static Logger dLog = Logger.getLogger(InventoryManagerTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
        iManager = (InventoryManager)context.getBean("InventoryManager");
		oManager = (OrderManager)context.getBean("OrderManager");
		//iManager = new InventoryManager();
		//oManager = new OrderManager();
		i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
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
	 * Test method for {@link com.burritopos.business.InventoryManager#createInventory(com.burritopos.domain.Inventory)}.
	 */
	public void testCreateInventory() {
		try {
			System.out.println("Start testCreateInventory");
			assertTrue(iManager.createInventory(i));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testCreateInventory");
		}
		catch(Exception e) {
			System.out.println("Exception in testCreateInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#updateInventory(com.burritopos.domain.Inventory)}.
	 */
	public void testUpdateInventory() {
		try {
			System.out.println("Start testUpdateInventory");
			assertTrue(iManager.createInventory(i));
			
			i.setBeefQty(i.getBeefQty()-5);
			i.setChiliTortillaQty(i.getChiliTortillaQty()-1);
			i.setWhiteRiceQty(i.getWhiteRiceQty()-3);
			
			assertTrue(iManager.updateInventory(i));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testUpdateInventory");
		}
		catch(Exception e) {
			System.out.println("Exception in testUpdateInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#removeFromInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Order)}.
	 */
	public void testRemoveFromInventoryInventoryOrder() {	
		try {
			System.out.println("Start testRemoveFromInventoryInventoryOrder");
			assertTrue(iManager.createInventory(i));
			
			//randomly create some burritos to remove from Inventory
			int total = rand.nextInt(5)+1;
			for(int n=0; n<total; n++) {
				oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
			}
			
			assertTrue(iManager.removeFromInventory(i, o));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testRemoveFromInventoryInventoryOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testRemoveFromInventoryInventoryOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#returnToInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Order)}.
	 */
	public void testReturnToInventoryInventoryOrder() {
		try {
			System.out.println("Start testReturnToInventoryInventoryOrder");
			assertTrue(iManager.createInventory(i));
			
			//randomly create some burritos to return to Inventory
			int total = rand.nextInt(5)+1;
			for(int n=0; n<total; n++) {
				oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
			}
			
			assertTrue(iManager.returnToInventory(i, o));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testReturnToInventoryInventoryOrder");
		}
		catch(Exception e) {
			System.out.println("Exception in testReturnToInventoryInventoryOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#removeFromInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Burrito)}.
	 */
	public void testRemoveFromInventoryInventoryBurrito() {
		try {
			System.out.println("Start testRemoveFromInventoryInventoryBurrito");
			assertTrue(iManager.createInventory(i));
			
			//randomly create a burrito to remove from inventory
			b = new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));
			
			assertTrue(iManager.removeFromInventory(i, b));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testRemoveFromInventoryInventoryBurrito");
		}
		catch(Exception e) {
			System.out.println("Exception in testRemoveFromInventoryInventoryBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#returnToInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Burrito)}.
	 */
	public void testReturnToInventoryInventoryBurrito() {
		try {
			System.out.println("Start testReturnToInventoryInventoryBurrito");
			assertTrue(iManager.createInventory(i));
			
			//randomly create a burrito to remove from inventory
			b = new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));
			
			assertTrue(iManager.returnToInventory(i, b));
			
			assertTrue(iManager.deleteInventory(i));
			System.out.println("Finish testReturnToInventoryInventoryBurrito");
		}
		catch(Exception e) {
			System.out.println("Exception in testReturnToInventoryInventoryBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

}
