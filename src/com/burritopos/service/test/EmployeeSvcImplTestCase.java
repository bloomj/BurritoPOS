/**
 * 
 */
package com.burritopos.service.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import com.burritopos.domain.Employee;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.service.Factory;
import com.burritopos.service.IEmployeeSvc;

/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImplTestCase extends TestCase {
	private Factory factory;
	private Employee e;
	//private static Logger dLog = Logger.getLogger(EmployeeSvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		factory = Factory.getInstance();
		e = new Employee("Jim","Bloom",new Integer("1"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Employee service
	 */
	public void testStoreEmployee() throws AssertionFailedError {
		try {
			//week 3
			//IEmployeeSvc ics = factory.getEmployeeSvc();
			
			//week 4
			IEmployeeSvc ics = (IEmployeeSvc) factory.getService(IEmployeeSvc.NAME);
			
			// First let's store the Employee
			assertTrue(ics.storeEmployee(e));
			
			// Then let's read it back in
			e = ics.getEmployee(e.getEmployeeID());
			assertTrue(e.validate());
			
			// Finally, let's cleanup the file that was created
			assertTrue(ics.deleteEmployee(e.getEmployeeID()));
		}
		catch(Exception e) {
			System.out.println("Exception in testStoreEmployee: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
