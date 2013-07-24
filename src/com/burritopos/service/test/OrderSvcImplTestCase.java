/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.service.dao.IOrderSvc;
import com.burritopos.test.BurritoPOSTestCase;

import junit.framework.AssertionFailedError;

/**
 * @author james.bloom
 *
 */
public class OrderSvcImplTestCase extends BurritoPOSTestCase {
	private Order o;
	private Burrito b;
	@Autowired
	private IOrderSvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(OrderSvcImplTestCase.class);

	public OrderSvcImplTestCase() {
		super();
	}
	
	/**
	 * Sets up the necessary code to run the tests.
	 *
	 * @throws Exception if it cannot set up the test.
	 */
	@Before
	public void initCommonResources() throws Exception {
		super.initCommonResources();
        
		o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("17.00"));
		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		o.getBurritos().add(b);
	}

	/**
	 * Unit Tests for Order service
	 * @throws Exception 
	 */
    @Test
	public void testStoreOrder() throws AssertionFailedError, Exception {
		// First let's store the Order
		assertTrue(ics.storeOrder(o));

		// Then let's read it back in
		o = ics.getOrder(o.getOrderID());
		assertTrue(o.validate());

		// Update the Order
		o.setIsComplete(true);
		o.setIsSubmitted(true);
		assertTrue(ics.storeOrder(o));

		// Finally, let's cleanup the file that was created
		assertTrue(ics.deleteOrder(o.getOrderID()));
	}
}
