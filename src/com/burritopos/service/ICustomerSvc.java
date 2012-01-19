/**
 * 
 */
package com.burritopos.service;

//import java.util.UUID;

import com.burritopos.domain.Customer;


/**
 * @author james.bloom
 *
 */
public interface ICustomerSvc extends IService {
	public final String NAME = "ICustomerSvc";
	
	public boolean storeCustomer(Customer c) throws Exception;
	public Customer getCustomer(Integer id) throws Exception;
	public boolean deleteCustomer(Integer id) throws Exception;
}
