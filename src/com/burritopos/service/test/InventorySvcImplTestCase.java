/**
 * 
 */
package com.burritopos.service.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import com.burritopos.domain.Inventory;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.service.Factory;
import com.burritopos.service.IInventorySvc;

/**
 * @author james.bloom
 *
 */
public class InventorySvcImplTestCase extends TestCase {
	private Factory factory;
	private Inventory i;
	//private static Logger dLog = Logger.getLogger(InventorySvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		factory = Factory.getInstance();
		i = new Inventory(new Integer("1"),50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Inventory service
	 */
	public void testStoreInventory() throws AssertionFailedError {
		try {
			//week 3
			//IInventorySvc ics = factory.getInventorySvc();
			
			//week 4
			IInventorySvc ics = (IInventorySvc) factory.getService(IInventorySvc.NAME);
			
			// First let's store the Inventory
			assertTrue(ics.storeInventory(i));
			
			// Then let's read it back in
			i = ics.getInventory(i.getId());
			assertTrue(i.validate());
			
			// Finally, let's cleanup the file that was created
			assertTrue(ics.deleteInventory(i.getId()));
		}
		catch(Exception e) {
			System.out.println("Exception in testStoreInventory: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
