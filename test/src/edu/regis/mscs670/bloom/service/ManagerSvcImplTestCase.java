/**
 * 
 */
package src.edu.regis.mscs670.bloom.service;

import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import src.edu.regis.mscs670.bloom.domain.Manager;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import src.edu.regis.mscs670.bloom.service.Factory;
import src.edu.regis.mscs670.bloom.service.IManagerSvc;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImplTestCase extends TestCase {
	private Factory factory;
	private Manager m;

        private static Logger dLog = Logger.getLogger(ManagerSvcImplTestCase.class);

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
			m = ics.getManager(m.getEmployeeID());
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
