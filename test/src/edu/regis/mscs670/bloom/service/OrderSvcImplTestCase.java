/**
 * 
 */
package src.edu.regis.mscs670.bloom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.apache.log4j.*;

import src.edu.regis.mscs670.bloom.domain.Burrito;
import src.edu.regis.mscs670.bloom.domain.Order;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author james.bloom
 *
 */
public class OrderSvcImplTestCase extends TestCase {
	private Factory factory;
	private Order o;

        private static Logger dLog = Logger.getLogger(OrderSvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
        @Override
	protected void setUp() throws Exception {
		super.setUp();
                String propertiesFile = "log4j.properties";
                PropertyConfigurator.configure(propertiesFile);
		factory = Factory.getInstance();
		o = new Order(new Integer("1"),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("17.00"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
        @Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Order service
	 */
	public void testStoreOrder() throws AssertionFailedError {
		try {
			//week 3
			//IOrderSvc ics = factory.getOrderSvc();
			
			//week 4
			IOrderSvc ics = (IOrderSvc) factory.getService(IOrderSvc.NAME);
			
			// First let's store the Order
			assertTrue(ics.storeOrder(o));
			
			// Then let's read it back in
			o = ics.getOrder(o.getOrderID());
			assertTrue(o.validate());
			
			// Finally, let's cleanup the file that was created
			assertTrue(ics.deleteOrder(o.getOrderID()));
		}
		catch(Exception e) {
			System.out.println("Exception in testStoreOrder: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
