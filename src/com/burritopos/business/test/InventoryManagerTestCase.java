/**
 * 
 */
package com.burritopos.business.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
//import java.util.UUID;
import org.apache.log4j.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;
import com.burritopos.domain.Order;
import com.burritopos.test.BurritoPOSTestCase;

import com.burritopos.business.InventoryManager;
import com.burritopos.business.OrderManager;

/**
 * @author james.bloom
 *
 */
public class InventoryManagerTestCase extends BurritoPOSTestCase {
	@Autowired
	private InventoryManager iManager;
	@Autowired
	private OrderManager oManager;
	private Inventory i;
	private Order o;
	private Burrito b;
	private Random rand;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(InventoryManagerTestCase.class);
	
	public InventoryManagerTestCase() {
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

		i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		rand = new Random();
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#createInventory(com.burritopos.domain.Inventory)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateInventory() throws Exception {
		assertTrue(iManager.createInventory(i));

		assertTrue(iManager.deleteInventory(i));
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#updateInventory(com.burritopos.domain.Inventory)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateInventory() throws Exception {
		assertTrue(iManager.createInventory(i));

		i.setBeefQty(i.getBeefQty()-5);
		i.setChiliTortillaQty(i.getChiliTortillaQty()-1);
		i.setWhiteRiceQty(i.getWhiteRiceQty()-3);

		assertTrue(iManager.updateInventory(i));

		assertTrue(iManager.deleteInventory(i));
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#removeFromInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Order)}.
	 * @throws Exception 
	 */
	@Test
	public void testRemoveFromInventoryInventoryOrder() throws Exception {	
		assertTrue(iManager.createInventory(i));

		//randomly create some burritos to remove from Inventory
		int total = rand.nextInt(5)+1;
		for(int n=0; n<total; n++) {
			oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
		}

		assertTrue(iManager.removeFromInventory(i, o));

		assertTrue(iManager.deleteInventory(i));
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#returnToInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Order)}.
	 * @throws Exception 
	 */
	@Test
	public void testReturnToInventoryInventoryOrder() throws Exception {
		assertTrue(iManager.createInventory(i));

		//randomly create some burritos to return to Inventory
		int total = rand.nextInt(5)+1;
		for(int n=0; n<total; n++) {
			oManager.addBurritoToOrder(o, new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00")));
		}

		assertTrue(iManager.returnToInventory(i, o));

		assertTrue(iManager.deleteInventory(i));
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#removeFromInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testRemoveFromInventoryInventoryBurrito() throws Exception {
		assertTrue(iManager.createInventory(i));

		//randomly create a burrito to remove from inventory
		b = new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));

		assertTrue(iManager.removeFromInventory(i, b));

		assertTrue(iManager.deleteInventory(i));
	}

	/**
	 * Test method for {@link com.burritopos.business.InventoryManager#returnToInventory(com.burritopos.domain.Inventory, com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testReturnToInventoryInventoryBurrito() throws Exception {
		assertTrue(iManager.createInventory(i));

		//randomly create a burrito to return to inventory
		b = new Burrito(rand.nextInt(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));

		assertTrue(iManager.returnToInventory(i, b));

		assertTrue(iManager.deleteInventory(i));
	}

}
