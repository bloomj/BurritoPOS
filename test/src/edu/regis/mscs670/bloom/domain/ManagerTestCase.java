/**
 * 
 */
package src.edu.regis.mscs670.bloom.domain;

import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import src.edu.regis.mscs670.bloom.domain.Manager;

/**
 * @author james.bloom
 *
 */
public class ManagerTestCase extends TestCase {
        private static Logger dLog = Logger.getLogger(ManagerTestCase.class);

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
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Manager#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
			Manager m = new Manager();
			m.setEmployeeID(new Integer("1"));
			m.setFirstName("Jim");
			m.setLastName("Bloom");
			
			assertTrue(m.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testValidManager: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Manager Validate Unit Test
	 */
	public void testInvalidManager() throws AssertionFailedError {
		try {
			Manager m = new Manager();
			
			assertFalse(m.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidManager: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Manager#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Manager m = new Manager("Jim","Bloom",new Integer("1"));
			Manager n = m;
			
			assertTrue(m.equals(n));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsManager: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Manager Equals Unit Test
	 */
	public void testNotEqualsManager() throws AssertionFailedError {
		try {
			Manager m = new Manager("Jim","Bloom",new Integer("1"));
			Manager n = new Manager();
			
			assertFalse(m.equals(n));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsManager: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
