/**
 * 
 */
package com.burritopos.domain.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class OrderTestCase extends BurritoPOSTestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(OrderTestCase.class);

	public OrderTestCase() {
		super();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Order#validate()}.
	 */
	@Test
	public void testValidate() throws AssertionFailedError {
		Order o = new Order();
		o.setBurritos(new ArrayList<Burrito>());
		o.setOrderDate(new Date());
		o.setIsComplete(true);
		o.setIsSubmitted(true);
		o.setOrderID(new Integer("1"));
		o.setTotalCost(new BigDecimal("17.00"));

		assertTrue(o.validate());
	}

	/**
	 * Negative Order Validate Unit Test
	 */
	@Test
	public void testInvalidOrder() throws AssertionFailedError {
		Order o = new Order();

		assertFalse(o.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Order#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() throws AssertionFailedError {
		Order o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(), false,false,new BigDecimal("17.00"));
		Order p = o;

		assertTrue(o.equals(p));
	}

	/**
	 * Negative Order Equals Unit Test
	 */
	@Test
	public void testNotEqualsOrder() throws AssertionFailedError {
		Order o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(), false,false,new BigDecimal("17.00"));
		Order p= new Order();

		assertFalse(o.equals(p));
	}
}
