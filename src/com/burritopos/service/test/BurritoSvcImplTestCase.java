/**
 * 
 */
package com.burritopos.service.test;

import java.math.BigDecimal;
//import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import com.burritopos.domain.Burrito;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.service.Factory;
import com.burritopos.service.IBurritoSvc;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcImplTestCase extends TestCase {
	private Factory factory;
	private Burrito b;
	private static Logger dLog = Logger.getLogger(BurritoSvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
        @Override
	protected void setUp() throws Exception {
		super.setUp();
		factory = Factory.getInstance();
		b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("3.00"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
        @Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Burrito service
	 */
	public void testBurritoSvc() throws AssertionFailedError {
		try {
			//week 3
			//IBurritoSvc ics = factory.getBurritoSvc();
			
			//week 4
            dLog.trace(new Date() + " | TEST | Going to get the service implementation");
            System.out.println("TEST | Going to get the service implementation");
			IBurritoSvc ics = (IBurritoSvc) factory.getService(IBurritoSvc.NAME);

            dLog.trace(new Date() + " | TEST | Going to store burrito");
            System.out.println("TEST | Going to store burrito");
			// First let's store the Burrito
			assertTrue(ics.storeBurrito(b));

            dLog.trace(new Date() + " | TEST | Going to read burrito");
			// Then let's read it back in
			b = ics.getBurrito(b.getId());
			assertTrue(b.validate());

            dLog.trace(new Date() + " | TEST | Going to delete burrito");
			// Finally, let's cleanup the file that was created
			assertTrue(ics.deleteBurrito(b.getId()));
		}
		catch(Exception e) {
			System.out.println("Exception in testStoreBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

}
