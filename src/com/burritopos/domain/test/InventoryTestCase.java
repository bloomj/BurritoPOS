/**
 * 
 */
package com.burritopos.domain.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.domain.Inventory;

/**
 * @author james.bloom
 *
 */
public class InventoryTestCase extends TestCase {
    //private static Logger dLog = Logger.getLogger(InventoryTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Inventory#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
			Inventory i = new Inventory();
			i.setBeefQty(50);
			i.setBlackBeansQty(50);
			i.setBrownRiceQty(50);
			i.setChickenQty(50);
			i.setChiliTortillaQty(50);
			i.setCucumberQty(50);
			i.setFlourTortillaQty(50);
			i.setGuacamoleQty(20);
			i.setHerbGarlicTortillaQty(50);
			i.setHummusQty(50);
			i.setJalapenoCheddarTortillaQty(50);
			i.setLettuceQty(50);
			i.setOnionQty(50);
			i.setPintoBeansQty(50);
			i.setSalsaPicoQty(50);
			i.setSalsaSpecialQty(50);
			i.setSalsaVerdeQty(50);
			i.setTomatoBasilTortillaQty(50);
			i.setTomatoesQty(50);
			i.setWheatTortillaQty(50);
			i.setWhiteRiceQty(50);
			i.setJalapenosQty(50);
		
			assertTrue(i.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testValidInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Inventory Validate Unit Test
	 */
	public void testInvalidInventory() throws AssertionFailedError {
		try {
			Inventory i = new Inventory();
			
			assertFalse(i.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link com.burritopos.domain.Inventory#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Inventory i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
			Inventory j = i;
			
			assertTrue(i.equals(j));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Inventory Equals Unit Test
	 */
	public void testNotEqualsInventory() throws AssertionFailedError {
		try {
			Inventory i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
			Inventory j = new Inventory(new Integer("1"),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
			
			assertFalse(i.equals(j));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
