/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
//import java.util.UUID;
import org.apache.log4j.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;

import junit.framework.AssertionFailedError;
import com.burritopos.service.dao.IBurritoSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcImplTestCase extends BurritoPOSTestCase {
	private Burrito b;
	@Autowired
	private IBurritoSvc ics;
	private static Logger dLog = Logger.getLogger(BurritoSvcImplTestCase.class);

	public BurritoSvcImplTestCase() {
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
		
		b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("3.00"));
	}

	/**
	 * Unit Tests for Burrito service
	 * @throws Exception 
	 */
    @Test
	public void testBurritoSvc() throws AssertionFailedError, Exception {
    	dLog.trace("Going to store burrito");
    	// First let's create the Burrito
    	assertTrue(ics.storeBurrito(b));

    	dLog.trace("Going to read burrito");
    	// Then let's read it back in
    	b = ics.getBurrito(b.getId());
    	assertTrue(b.validate());

    	dLog.trace("Going to update burrito");
    	// Update the burrito
    	b.setBeef(false);
    	b.setBrownRice(true);
    	b.setHummus(true);
    	assertTrue(ics.storeBurrito(b));

    	dLog.trace("Going to delete burrito");
    	// Finally, let's cleanup the file that was created
    	assertTrue(ics.deleteBurrito(b.getId()));
	}

}
