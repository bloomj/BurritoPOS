/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Employee;
import junit.framework.AssertionFailedError;
import com.burritopos.service.dao.IEmployeeSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImplTestCase extends BurritoPOSTestCase {
	private Employee e;
	@Autowired
	private IEmployeeSvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(EmployeeSvcImplTestCase.class);

	public EmployeeSvcImplTestCase() {
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
		
		e = new Employee("Jim","Bloom",new Integer("1"));
	}

	/**
	 * Unit Tests for Employee service
	 * @throws Exception 
	 */
	@Test
	public void testStoreEmployee() throws AssertionFailedError, Exception {
		// First let's store the Employee
		assertTrue(ics.storeEmployee(e));

		// Then let's read it back in
		e = ics.getEmployee(e.getEmployeeID());
		assertTrue(e.validate());

		// Update the Employee
		e.setLastName("Smith");
		assertTrue(ics.storeEmployee(e));

		// Finally, let's cleanup the file that was created
		assertTrue(ics.deleteEmployee(e.getEmployeeID()));
	}
}
