/**
 * 
 */
package src.edu.regis.mscs670.bloom.domain;

import java.math.BigDecimal;
import java.util.UUID;
import org.apache.log4j.*;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import src.edu.regis.mscs670.bloom.domain.Burrito;

/**
 * @author james.bloom
 *
 */
public class BurritoTestCase extends TestCase {
        private static Logger dLog = Logger.getLogger(BurritoTestCase.class);

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
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Burrito#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
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
		catch(Exception e) {
			System.out.println("Exception in testValidBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Burrito Validate Unit Test
	 */
	public void testInvalidBurrito() throws AssertionFailedError {
		try {
			Burrito b = new Burrito();
			
			assertFalse(b.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link edu.regis.mscs670.bloom.domain.Burrito#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Burrito b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
			Burrito c = b;
			
			assertTrue(b.equals(c));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Burrito Equals Unit Test
	 */
	public void testNotEqualsBurrito() throws AssertionFailedError {
		try {
			Burrito b = new Burrito(new Integer("1"),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
			Burrito c = new Burrito();
			
			assertFalse(b.equals(c));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsBurrito: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
