package src.edu.regis.mscs670.bloom.service;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.*;

public class AllTests {

	public static Test suite() {
                String propertiesFile = "log4j.properties";
                PropertyConfigurator.configure(propertiesFile);

		TestSuite suite = new TestSuite(
				"Test for edu.regis.mscs670.bloom.service");
		//$JUnit-BEGIN$
		suite.addTest(src.edu.regis.mscs670.bloom.domain.AllTests.suite());
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
