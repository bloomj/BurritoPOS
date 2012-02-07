/**
 * 
 */
package com.burritopos.service.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import com.burritopos.domain.Manager;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.service.Factory;
import com.burritopos.service.IManagerSvc;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImplTestCase extends TestCase {
	private Factory factory;
	private Manager m;

    //private static Logger dLog = Logger.getLogger(ManagerSvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		factory = Factory.getInstance();
		m = new Manager("Jim","Bloom",new Integer("1"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Manager service
	 */
	public void testStoreManager() throws AssertionFailedError {
		try {
			//week 3
			//IManagerSvc ics = factory.getManagerSvc();
			
			//week 4
			IManagerSvc ics = (IManagerSvc) factory.getService(IManagerSvc.NAME);
			
			// First let's store the Inventory
			assertTrue(ics.storeManager(m));
			
			// Then let's read it back in
			//System.out.println("here1");
			m = ics.getManager(m.getEmployeeID());
			//System.out.println("here2");
			assertTrue(m.validate());
			
			// Finally, let's cleanup the file that was created
			assertTrue(ics.deleteManager(m.getEmployeeID()));
		}
		catch(Exception e) {
			System.out.println("Exception in testStoreManager: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
