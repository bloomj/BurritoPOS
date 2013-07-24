/**
 * 
 */
package com.burritopos.domain.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import com.burritopos.domain.Inventory;
import com.burritopos.exception.InsufficientInventoryException;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class InventoryTestCase extends BurritoPOSTestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(InventoryTestCase.class);

    public InventoryTestCase() {
    	super();
    }
    
	/**
	 * Test method for {@link com.burritopos.domain.Inventory#validate()}.
	 * @throws InsufficientInventoryException 
	 */
	@Test
	public void testValidate() throws AssertionFailedError, InsufficientInventoryException {
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

	/**
	 * Negative Inventory Validate Unit Test
	 */
	@Test
	public void testInvalidInventory() throws AssertionFailedError {
		Inventory i = new Inventory();

		assertFalse(i.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Inventory#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() throws AssertionFailedError {
		Inventory i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
		Inventory j = i;

		assertTrue(i.equals(j));
	}

	/**
	 * Negative Inventory Equals Unit Test
	 */
	@Test
	public void testNotEqualsInventory() throws AssertionFailedError {
		Inventory i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
		Inventory j = new Inventory(new Integer("1"),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

		assertFalse(i.equals(j));
	}
}
