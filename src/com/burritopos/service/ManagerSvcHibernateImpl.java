/**
 * 
 */
package com.burritopos.service;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Manager;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class ManagerSvcHibernateImpl extends BaseSvcHibernateImpl implements IManagerSvc {

        private static Logger dLog = Logger.getLogger(ManagerSvcHibernateImpl.class);

	@Override
	public Manager getManager(Integer id) throws ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getManager | Manager ID: "+id);
		Manager m = null;
                Session session = null;
		
		try {
                    session = getSession();
                    Transaction tranx = session.beginTransaction();
                    m = (Manager)session.get(Manager.class, id);
                    tranx.commit();
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getManager: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method storeManager | Manager ID: "+m.getEmployeeID());
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
                            dLog.info(new Date() + " | Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeManager: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method deleteManger | Manager ID:"+id);
		boolean result = false;
                Session session = null;
		
		try {
                    session = getSession();
                    String hql = "delete from Manager where employeeid = " + id.toString();
                    Query query = session.createQuery(hql);
                    int row = query.executeUpdate();

                    if (row == 0)
                        result = false;
                    else
                        result = true;
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteManger: "+e.getMessage());
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
