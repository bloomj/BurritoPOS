package com.burritopos.service.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.*;

public class AllTests {

	public static Test suite() {
        String propertiesFile = "log4j.properties";
        PropertyConfigurator.configure(propertiesFile);

		TestSuite suite = new TestSuite("Test for com.burritopos.service");
		//$JUnit-BEGIN$
		suite.addTest(com.burritopos.domain.test.AllTests.suite());
		suite.addTestSuite(BurritoSvcImplTestCase.class);
		suite.addTestSuite(CustomerSvcImplTestCase.class);
		suite.addTestSuite(EmployeeSvcImplTestCase.class);
		suite.addTestSuite(InventorySvcImplTestCase.class);
		suite.addTestSuite(ManagerSvcImplTestCase.class);
		suite.addTestSuite(OrderSvcImplTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
