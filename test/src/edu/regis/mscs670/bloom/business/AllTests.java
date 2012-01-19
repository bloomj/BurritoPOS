package src.edu.regis.mscs670.bloom.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for edu.regis.mscs670.bloom.business");
		//$JUnit-BEGIN$
		suite.addTestSuite(BurritoManagerTestCase.class);
		suite.addTestSuite(OrderManagerTestCase.class);
		suite.addTestSuite(InventoryManagerTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
