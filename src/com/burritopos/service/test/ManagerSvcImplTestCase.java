/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Manager;
import junit.framework.AssertionFailedError;
import com.burritopos.service.dao.IManagerSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImplTestCase extends BurritoPOSTestCase {
	private Manager m;
	@Autowired
	private IManagerSvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(ManagerSvcImplTestCase.class);

	public ManagerSvcImplTestCase() {
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
		
		m = new Manager("Jim","Bloom",new Integer("1"));
	}

	/**
	 * Unit Tests for Manager service
	 * @throws Exception 
	 */
	@Test
	public void testStoreManager() throws AssertionFailedError, Exception {
		// First let's store the Inventory
		assertTrue(ics.storeManager(m));

		// Then let's read it back in
		m = ics.getManager(m.getEmployeeID());
		assertTrue(m.validate());

		// Update the Employee
		m.setLastName("Smith");
		assertTrue(ics.storeManager(m));

		// Finally, let's cleanup the file that was created
		assertTrue(ics.deleteManager(m.getEmployeeID()));
	}
}
