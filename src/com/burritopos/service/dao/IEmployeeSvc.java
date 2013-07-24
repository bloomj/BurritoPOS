package com.burritopos.service.dao;

//import java.util.UUID;

import com.burritopos.domain.Employee;
import com.burritopos.service.IService;

/**
 * @author james.bloom
 *
 */
public interface IEmployeeSvc extends IService {
	public final String NAME = "IEmployeeSvc";
	
	public boolean storeEmployee(Employee e) throws Exception;
	public Employee getEmployee(Integer id) throws Exception;
	public boolean deleteEmployee(Integer id) throws Exception;
}
