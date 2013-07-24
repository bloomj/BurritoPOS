/**
 * 
 */
package com.burritopos.business.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Random;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Burrito;
import com.burritopos.test.BurritoPOSTestCase;
import com.burritopos.business.BurritoManager;

/**
 * @author james.bloom
 *
 */
public class BurritoManagerTestCase extends BurritoPOSTestCase {
	@Autowired
	private BurritoManager bManager;
	private Burrito b;
	private Random rand;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(BurritoManagerTestCase.class);
	
	public BurritoManagerTestCase() {
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

		b = new Burrito(new Integer("1"),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		rand = new Random();
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#createBurrito(com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateBurrito() throws Exception {
    	b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		assertTrue(bManager.createBurrito(b));
			
		assertTrue(bManager.deleteBurrito(b));
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#updateBurrito(com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateBurrito() throws Exception {
		b = new Burrito(rand.nextInt(1000000),true,false,false,true,false,false,true,false,false,true,true,false,false,true,false,true,false,true,false,false,false,true,new BigDecimal("0.00"));
		assertTrue(bManager.createBurrito(b));

		b.setBeef(rand.nextBoolean());
		b.setBlackBeans(rand.nextBoolean());
		b.setFlourTortilla(rand.nextBoolean());
		b.setChiliTortilla(rand.nextBoolean());
		b.setSalsaSpecial(rand.nextBoolean());
		b.setJalapenos(rand.nextBoolean());

		assertTrue(bManager.updateBurrito(b));

		assertTrue(bManager.deleteBurrito(b));
	}

	/**
	 * Test method for {@link com.burritopos.business.BurritoManager#calculatePrice(com.burritopos.domain.Burrito)}.
	 * @throws Exception 
	 */
	@Test
	public void testCalculatePrice() throws Exception {
		b = new Burrito(rand.nextInt(1000000),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),new BigDecimal("0.00"));
		assertTrue(bManager.createBurrito(b));

		b.setPrice(bManager.calculatePrice(b));
		if(b.getPrice().compareTo(new BigDecimal("-1")) != 1) {
			fail("Invalid price calculated for Burrito");
		}

		assertTrue(bManager.deleteBurrito(b));
	}

}
