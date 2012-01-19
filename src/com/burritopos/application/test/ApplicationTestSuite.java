package com.burritopos.application.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ApplicationTestSuite extends TestCase {

	public ApplicationTestSuite(String name) {
		super(name);
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for Burrito POS");
		//$JUnit-BEGIN$
                suite.addTest(com.burritopos.domain.test.AllTests.suite());
                suite.addTest(com.burritopos.service.test.AllTests.suite());
                suite.addTest(com.burritopos.business.test.AllTests.suite());
		//$JUnit-END$
		return suite;
	}

}
