/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Manager;
import com.burritopos.service.dao.IManagerSvc;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImpl extends BaseSvcImpl implements IManagerSvc {
	private static Logger dLog = Logger.getLogger(ManagerSvcImpl.class);

	@Override
	public Manager getManager(Integer id) throws ClassNotFoundException, Exception {
		dLog.info("Entering method getManager | Manager ID: "+id);
		Manager m = null;
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			m = (Manager)session.get(Manager.class, id);
			tranx.commit();
		} 
		catch(Exception e) {
			dLog.error("Exception in getManager: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return m;
	}

	@Override
	public boolean storeManager(Manager m) throws Exception {
		dLog.info("Entering method storeManager | Manager ID: "+m.getEmployeeID());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(m.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				session.save(m);
				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info("Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error("Exception in storeManager: "+e.getMessage());
			e.printStackTrace();
			result = false;
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
	public boolean deleteManager(Integer id) throws Exception {
		dLog.info("Entering method deleteManger | Manager ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Manager where employeeid = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("HQL: " + hql);
			if (row > 0) {
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error("Exception in deleteManger: "+e.getMessage());
			e.printStackTrace();
			result = false;
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
