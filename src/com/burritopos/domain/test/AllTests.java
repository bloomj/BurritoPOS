package com.burritopos.domain.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ecom.burritopos.domain.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(BurritoTestCase.class);
		suite.addTestSuite(CustomerTestCase.class);
		suite.addTestSuite(EmployeeTestCase.class);
		suite.addTestSuite(InventoryTestCase.class);
		suite.addTestSuite(ManagerTestCase.class);
		suite.addTestSuite(OrderTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
