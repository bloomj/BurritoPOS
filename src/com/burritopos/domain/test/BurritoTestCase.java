/**
 * 
 */
package com.burritopos.domain.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import com.burritopos.domain.Burrito;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class BurritoTestCase extends BurritoPOSTestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(BurritoTestCase.class);

	public BurritoTestCase() {
		super();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Burrito#validate()}.
	 */
	@Test
	public void testValidate() throws AssertionFailedError {
		Burrito b = new Burrito();
		b.setId(new Integer("1"));
		b.setBeef(true);
		b.setBlackBeans(true);
		b.setBrownRice(true);
		b.setChicken(false);
		b.setChiliTortilla(false);
		b.setCucumber(true);
		b.setFlourTortilla(false);
		b.setGuacamole(true);
		b.setHerbGarlicTortilla(true);
		b.setHummus(false);
		b.setJalapenoCheddarTortilla(false);
		b.setJalapenos(true);
		b.setLettuce(true);
		b.setOnion(false);
		b.setPintoBeans(false);
		b.setSalsaPico(true);
		b.setSalsaSpecial(false);
		b.setSalsaVerde(false);
		b.setTomatoBasilTortilla(false);
		b.setTomatoes(false);
		b.setWheatTortilla(false);
		b.setWhiteRice(false);
		b.setPrice(new BigDecimal("3.00"));

		assertTrue(b.validate());
	}

	/**
	 * Negative Burrito Validate Unit Test
	 */
	@Test
	public void testInvalidBurrito() throws AssertionFailedError {
		Burrito b = new Burrito();

		assertFalse(b.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Burrito#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() throws AssertionFailedError {
		Burrito b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
		Burrito c = b;

		assertTrue(b.equals(c));
	}

	/**
	 * Negative Burrito Equals Unit Test
	 */
	@Test
	public void testNotEqualsBurrito() throws AssertionFailedError {
		Burrito b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
		Burrito c = new Burrito();

		assertFalse(b.equals(c));
	}
}
