package com.burritopos.service.dao;

//import java.util.UUID;

import com.burritopos.domain.Customer;
import com.burritopos.service.IService;


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
