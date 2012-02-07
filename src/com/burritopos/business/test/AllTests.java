package com.burritopos.business.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.burritopos.business");
		//$JUnit-BEGIN$
		suite.addTest(com.burritopos.domain.test.AllTests.suite());
		suite.addTest(com.burritopos.service.test.AllTests.suite());
		suite.addTestSuite(BurritoManagerTestCase.class);
		suite.addTestSuite(OrderManagerTestCase.class);
		suite.addTestSuite(InventoryManagerTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
