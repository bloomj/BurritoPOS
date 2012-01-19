/**
 * 
 */
package com.burritopos.business.test;

import java.math.BigDecimal;
import java.util.Random;
//import org.apache.log4j.*;
import org.springframework.context.*;
import org.springframework.context.support.*;


import com.burritopos.domain.Burrito;
import junit.framework.TestCase;
import com.burritopos.business.BurritoManager;

/**
 * @author james.bloom
 *
 */
public class BurritoManagerTestCase extends TestCase {
	private BurritoManager bManager;
	private Burrito b;
	private Random rand;
	//private static Logger dLog = Logger.getLogger(BurritoManagerTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
		bManager = (BurritoManager)context.getBean("BurritoManager");
		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		rand = new Random();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#createBurrito(com.burritopos.domain.Burrito)}.
	 */
	public void testCreateBurrito() {
		try {
            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			System.out.println("Start testCreateBurrito");
			assertTrue(bManager.createBurrito(b));
			
			assertTrue(bManager.deleteBurrito(b));
			System.out.println("Finish testCreateBurrito");
		}
		catch(Exception e) {
			System.out.println("Exception in testCreateBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#updateBurrito(com.burritopos.domain.Burrito)}.
	 */
	public void testUpdateBurrito() {
		try {
            b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
			System.out.println("Start testUpdateBurrito");
			assertTrue(bManager.createBurrito(b));
			
			assertTrue(bManager.deleteBurrito(b));
			System.out.println("Finish testUpdateBurrito");
		}
		catch(Exception e) {
			System.out.println("Exception in testUpdateBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#calculatePrice(com.burritopos.domain.Burrito)}.
	 */
	public void testCalculatePrice() {
		try {
			System.out.println("Start testCalculatePrice");
			
			b = new Burrito(rand.nextInt(1000000),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));
			assertTrue(bManager.createBurrito(b));
			
			b.setPrice(bManager.calculatePrice(b));
			if(b.getPrice().compareTo(new BigDecimal("-1")) != 1) {
				fail("Invalid price calculated for Burrito");
			}
			
			assertTrue(bManager.deleteBurrito(b));
			System.out.println("Finish testCalculatePrice");
		}
		catch(Exception e) {
			System.out.println("Exception in testCalculatePrice: " + e.getMessage());
			fail(e.getMessage());
		}
	}

}
