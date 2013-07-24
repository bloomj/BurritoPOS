package com.burritopos.service.dao;

//import java.util.UUID;

import java.util.ArrayList;

import com.burritopos.domain.Order;
import com.burritopos.service.IService;

/**
 * @author james.bloom
 *
 */
public interface IOrderSvc extends IService {
	public final String NAME = "IOrderSvc";
	
	public boolean storeOrder(Order o) throws Exception;
	public Order getOrder(Integer id) throws Exception;
	public boolean deleteOrder(Integer id) throws Exception;
	public ArrayList<Order> getAllOrders() throws Exception;
}
