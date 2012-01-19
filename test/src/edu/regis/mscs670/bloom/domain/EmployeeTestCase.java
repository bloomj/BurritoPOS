/**
 * 
 */
package src.edu.regis.mscs670.bloom.domain;

import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import src.edu.regis.mscs670.bloom.domain.Employee;

/**
 * @author james.bloom
 *
 */
public class EmployeeTestCase extends TestCase {
        private static Logger dLog = Logger.getLogger(EmployeeTestCase.class);


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
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Employee#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
			Employee e = new Employee();
			e.setEmployeeID(new Integer("1"));
			e.setFirstName("Jim");
			e.setLastName("Bloom");
			
			assertTrue(e.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testValidEmployee: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Employee Validate Unit Test
	 */
	public void testInvalidEmployee() throws AssertionFailedError {
		try {
			Employee e = new Employee();
			
			assertFalse(e.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidEmployee: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Employee#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Employee e = new Employee("Jim","Bloom",new Integer("1"));
			Employee d = e;
			
			assertTrue(e.equals(d));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsEmployee: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Employee Equals Unit Test
	 */
	public void testNotEqualsEmployee() throws AssertionFailedError {
		try {
			Employee e = new Employee("Jim","Bloom",new Integer("1"));
			Employee d = new Employee();
			
			assertFalse(e.equals(d));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsEmployee: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
