/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.service.dao.IEmployeeSvc;

/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImpl extends BaseSvcImpl implements IEmployeeSvc {
	private static Logger dLog = Logger.getLogger(EmployeeSvcImpl.class);

	@Override
	public Employee getEmployee(Integer id) throws ClassNotFoundException, Exception {
		dLog.info("Entering method getEmployee | Employee ID: "+id);
		Employee e = null;
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			e = (Employee)session.get(Employee.class, id);
			tranx.commit();
		} 
		catch(Exception e1) {
			dLog.error("Exception in getEmployee", e1);
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return e;
	}

	@Override
	public boolean storeEmployee(Employee e) throws Exception {
		dLog.info("Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(e.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				session.save(e);
				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info("Was committed: "+result);
			}
		} 
		catch(Exception e1) {
			dLog.error("Exception in storeEmployee", e1);
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return result;
	}

	@Override
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info("Entering method deleteEmployee | Employee ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Employee where employeeid = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("HQL: " + hql);
			if (row > 0) {
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error("Exception in deleteEmployee", e);
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return result;
	}
}
