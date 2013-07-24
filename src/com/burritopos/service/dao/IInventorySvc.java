package com.burritopos.service.dao;

//import java.util.UUID;

import com.burritopos.domain.Inventory;
import com.burritopos.service.IService;

/**
 * @author james.bloom
 *
 */
public interface IInventorySvc extends IService {
	public final String NAME = "IInventorySvc";
	
	public boolean storeInventory(Inventory i) throws Exception;
	public Inventory getInventory(Integer id) throws Exception;
	public boolean deleteInventory(Integer id) throws Exception;
}
