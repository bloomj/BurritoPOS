/**
 * 
 */
package com.burritopos.business;


import com.burritopos.exception.ServiceLoadException;
import com.burritopos.service.Factory;
import com.burritopos.service.IService;


/**
 * @author james.bloom
 * Notes: Renamed this to Manager to avoid confusion with Manager domain object for managers of the actual restaurants.
 */
public abstract class FactoryManager 
{
	protected Factory factory = Factory.getInstance();
	
	protected IService getService(String name) throws ServiceLoadException
	{
	     return factory.getService(name);
	}
}
