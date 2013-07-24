/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Inventory;
import junit.framework.AssertionFailedError;
import com.burritopos.service.dao.IInventorySvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class InventorySvcImplTestCase extends BurritoPOSTestCase {
	private Inventory i;
	@Autowired
	private IInventorySvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(InventorySvcImplTestCase.class);

	public InventorySvcImplTestCase() {
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
	}

	/**
	 * Unit Tests for Inventory service
	 * @throws Exception 
	 */
	@Test
	public void testStoreInventory() throws AssertionFailedError, Exception {
		// First let's store the Inventory
		assertTrue(ics.storeInventory(i));

		// Then let's read it back in
		i = ics.getInventory(i.getId());
		assertTrue(i.validate());

		// Update the Inventory
		i.setCucumberQty(17);
		i.setGuacamoleQty(1);
		assertTrue(ics.storeInventory(i));

		// Finally, let's cleanup the file that was created
		assertTrue(ics.deleteInventory(i.getId()));
	}
}
