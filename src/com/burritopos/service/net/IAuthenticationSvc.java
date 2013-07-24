package com.burritopos.service.net;

import com.burritopos.domain.Employee;
import com.burritopos.domain.Manager;
import com.burritopos.service.IService;

/**
 *
 * @author james.bloom
 */
public interface IAuthenticationSvc extends IService {
	public final String NAME = "IAuthenticationSvc";

	public boolean login(Employee e, String password) throws Exception;
	public boolean login(Manager m, String password) throws Exception;
}
