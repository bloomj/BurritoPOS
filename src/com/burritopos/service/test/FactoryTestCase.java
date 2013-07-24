/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import org.apache.log4j.*;
import org.junit.Test;

import com.burritopos.service.Factory;
import com.burritopos.service.dao.IBurritoSvc;
import com.burritopos.service.dao.ICustomerSvc;
import com.burritopos.service.dao.IEmployeeSvc;
import com.burritopos.service.dao.IInventorySvc;
import com.burritopos.service.dao.IManagerSvc;
import com.burritopos.service.dao.IOrderSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class FactoryTestCase extends BurritoPOSTestCase {
	private Factory factory;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(FactoryTestCase.class);

	public FactoryTestCase() {
		super();
		
		factory = Factory.getInstance();
	}

	/**
	 * Test method for {@link com.burritopos.service.Factory#getService()}.
	 */
    @Test
	public void testGetService() throws Exception {
    	IBurritoSvc burritoSvc = (IBurritoSvc) factory.getService(IBurritoSvc.NAME);
    	assertNotNull(burritoSvc);
    	
    	ICustomerSvc customerSvc = (ICustomerSvc) factory.getService(ICustomerSvc.NAME);
    	assertNotNull(customerSvc);
    	
    	IEmployeeSvc employeeSvc = (IEmployeeSvc) factory.getService(IEmployeeSvc.NAME);
    	assertNotNull(employeeSvc);
    	
    	IInventorySvc inventorySvc = (IInventorySvc) factory.getService(IInventorySvc.NAME);
    	assertNotNull(inventorySvc);
    	
    	IManagerSvc managerSvc = (IManagerSvc) factory.getService(IManagerSvc.NAME);
    	assertNotNull(managerSvc);
    	
    	IOrderSvc orderSvc = (IOrderSvc) factory.getService(IOrderSvc.NAME);
    	assertNotNull(orderSvc);
	}

	/**
	 * Test method for {@link com.burritopos.service.Factory#getBurritoSvc()}.
	 */
    @Test
	public void testGetBurritoSvc() throws Exception {
    	IBurritoSvc burritoSvc = factory.getBurritoSvc();
    	assertNotNull(burritoSvc);
	}
    
	/**
	 * Test method for {@link com.burritopos.service.Factory#getCustomerSvc()}.
	 */
    @Test
	public void testGetCustomerSvc() throws Exception {
    	ICustomerSvc customerSvc = factory.getCustomerSvc();
    	assertNotNull(customerSvc);
	}
    
	/**
	 * Test method for {@link com.burritopos.service.Factory#getEmployeeSvc()}.
	 */
    @Test
	public void testGetEmployeeSvc() throws Exception {	
    	IEmployeeSvc employeeSvc = factory.getEmployeeSvc();
    	assertNotNull(employeeSvc);
	}
    
	/**
	 * Test method for {@link com.burritopos.service.Factory#getInventorySvc()}.
	 */
    @Test
	public void testGetInventorySvc() throws Exception {	
    	IInventorySvc inventorySvc = factory.getInventorySvc();
    	assertNotNull(inventorySvc);
	}
    
	/**
	 * Test method for {@link com.burritopos.service.Factory#getManagerSvc()}.
	 */
    @Test
	public void testGetManagerSvc() throws Exception {	
    	IManagerSvc managerSvc = factory.getManagerSvc();
    	assertNotNull(managerSvc);
	}
    
	/**
	 * Test method for {@link com.burritopos.service.Factory#getOrderSvc()}.
	 */
    @Test
	public void testGetOrderSvc() throws Exception {	
    	IOrderSvc orderSvc = factory.getOrderSvc();
    	assertNotNull(orderSvc);
	}
    
}
