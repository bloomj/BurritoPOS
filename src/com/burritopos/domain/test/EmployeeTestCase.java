/**
 * 
 */
package com.burritopos.domain.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import com.burritopos.domain.Employee;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class EmployeeTestCase extends BurritoPOSTestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(EmployeeTestCase.class);

	public EmployeeTestCase() {
		super();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Employee#validate()}.
	 */
	@Test
	public void testValidate() throws AssertionFailedError {
		Employee e = new Employee();
		e.setEmployeeID(new Integer("1"));
		e.setFirstName("Jim");
		e.setLastName("Bloom");

		assertTrue(e.validate());
	}

	/**
	 * Negative Employee Validate Unit Test
	 */
	@Test
	public void testInvalidEmployee() throws AssertionFailedError {
		Employee e = new Employee();

		assertFalse(e.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Employee#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() throws AssertionFailedError {
		Employee e = new Employee("Jim","Bloom",new Integer("1"));
		Employee d = e;

		assertTrue(e.equals(d));
	}

	/**
	 * Negative Employee Equals Unit Test
	 */
	@Test
	public void testNotEqualsEmployee() throws AssertionFailedError {
		Employee e = new Employee("Jim","Bloom",new Integer("1"));
		Employee d = new Employee();

		assertFalse(e.equals(d));
	}
}
