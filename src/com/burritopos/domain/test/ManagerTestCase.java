/**
 * 
 */
package com.burritopos.domain.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.domain.Manager;

/**
 * @author james.bloom
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ManagerTestCase extends TestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(ManagerTestCase.class);

    public ManagerTestCase() {
    	super();
    }

	/**
	 * Test method for {@link com.burritopos.domain.Manager#validate()}.
	 */
    @Test
	public void testValidate() throws AssertionFailedError {
		Manager m = new Manager();
		m.setEmployeeID(new Integer("1"));
		m.setFirstName("Jim");
		m.setLastName("Bloom");

		assertTrue(m.validate());
	}

	/**
	 * Negative Manager Validate Unit Test
	 */
    @Test
	public void testInvalidManager() throws AssertionFailedError {
		Manager m = new Manager();

		assertFalse(m.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Manager#equals(java.lang.Object)}.
	 */
    @Test
	public void testEqualsObject() throws AssertionFailedError {
		Manager m = new Manager("Jim","Bloom",new Integer("1"));
		Manager n = m;

		assertTrue(m.equals(n));
	}

	/**
	 * Negative Manager Equals Unit Test
	 */
    @Test
	public void testNotEqualsManager() throws AssertionFailedError {
		Manager m = new Manager("Jim","Bloom",new Integer("1"));
		Manager n = new Manager();

		assertFalse(m.equals(n));
	}
}
