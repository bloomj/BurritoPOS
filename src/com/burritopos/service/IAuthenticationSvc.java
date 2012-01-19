/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.burritopos.service;

import com.burritopos.domain.Employee;
import com.burritopos.domain.Manager;

/**
 *
 * @author james.bloom
 */
public interface IAuthenticationSvc extends IService {
	public final String NAME = "IAuthenticationSvc";

	public boolean login(Employee e, String password) throws Exception;
        public boolean login(Manager m, String password) throws Exception;
}
