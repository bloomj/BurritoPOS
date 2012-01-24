/**
 * 
 */
package com.burritopos.service;

//import java.util.UUID;

import com.burritopos.domain.Order;

/**
 * @author james.bloom
 *
 */
public interface IOrderSvc extends IService {
	public final String NAME = "IOrderSvc";
	
	public boolean storeOrder(Order o) throws Exception;
	public Order getOrder(Integer id) throws Exception;
	public boolean deleteOrder(Integer id) throws Exception;
}