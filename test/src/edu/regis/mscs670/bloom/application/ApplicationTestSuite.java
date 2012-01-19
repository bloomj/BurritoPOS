package src.edu.regis.mscs670.bloom.application;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ApplicationTestSuite extends TestCase {

	public ApplicationTestSuite(String name) {
		super(name);
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for Neato Burrito");
		//$JUnit-BEGIN$
                suite.addTest(src.edu.regis.mscs670.bloom.domain.AllTests.suite());
                suite.addTest(src.edu.regis.mscs670.bloom.service.AllTests.suite());
                suite.addTest(src.edu.regis.mscs670.bloom.business.AllTests.suite());
		//$JUnit-END$
		return suite;
	}

}
