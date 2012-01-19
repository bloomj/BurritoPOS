/**
 * 
 */
package src.edu.regis.mscs670.bloom.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import src.edu.regis.mscs670.bloom.domain.Burrito;
import src.edu.regis.mscs670.bloom.domain.Order;

/**
 * @author james.bloom
 *
 */
public class OrderTestCase extends TestCase {
        private static Logger dLog = Logger.getLogger(OrderTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Order#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
			Order o = new Order();
			o.setBurritos(new ArrayList<Burrito>());
			o.setOrderDate(new Date());
			o.setIsComplete(true);
			o.setIsSubmitted(true);
			o.setOrderID(new Integer("1"));
			o.setTotalCost(new BigDecimal("17.00"));
			
			assertTrue(o.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testValidOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Order Validate Unit Test
	 */
	public void testInvalidOrder() throws AssertionFailedError {
		try {
			Order o = new Order();
			
			assertFalse(o.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Order#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Order o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(), false,false,new BigDecimal("17.00"));
			Order p = o;
			
			assertTrue(o.equals(p));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Negative Order Equals Unit Test
	 */
	public void testNotEqualsOrder() throws AssertionFailedError {
		try {
			Order o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(), false,false,new BigDecimal("17.00"));
			Order p= new Order();
			
			assertFalse(o.equals(p));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
