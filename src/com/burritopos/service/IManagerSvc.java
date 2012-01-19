/**
 * 
 */
package com.burritopos.service;

//import java.util.UUID;

import com.burritopos.domain.Manager;

/**
 * @author james.bloom
 *
 */
public interface IManagerSvc extends IService {
	public final String NAME = "IManagerSvc";
	
	public boolean storeManager(Manager i) throws Exception;
	public Manager getManager(Integer id) throws Exception;
	public boolean deleteManager(Integer id) throws Exception;
}
