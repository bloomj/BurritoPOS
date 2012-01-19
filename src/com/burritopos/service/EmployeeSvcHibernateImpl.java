/**
 * 
 */
package com.burritopos.service;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class EmployeeSvcHibernateImpl extends BaseSvcHibernateImpl implements IEmployeeSvc {

    private static Logger dLog = Logger.getLogger(EmployeeSvcHibernateImpl.class);

	@Override
	public Employee getEmployee(Integer id) throws ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getEmployee | Employee ID: "+id);
		Employee e = null;
                Session session = null;
		
		try {
                    session = getSession();
                    Transaction tranx = session.beginTransaction();
                    e = (Employee)session.get(Employee.class, id);
                    tranx.commit();
		} 
		catch(Exception e1) {
			dLog.error(new Date() + " | Exception in getEmployee: "+e1.getMessage());
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
		dLog.info(new Date() + " | Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
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
                            dLog.info(new Date() + " | Was committed: "+result);
			}
		} 
		catch(Exception e1) {
			dLog.error(new Date() + " | Exception in storeEmployee: "+e1.getMessage());
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
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteEmployee | Employee ID:"+id);
		boolean result = false;
                Session session = null;
		
		try {
                    session = getSession();
                    String hql = "delete from Employee where employeeid = " + id.toString();
                    Query query = session.createQuery(hql);
                    int row = query.executeUpdate();

                    if (row == 0)
                        result = false;
                    else
                        result = true;
		}
		catch(Exception e1) {
			dLog.error(new Date() + " | Exception in deleteEmployee: "+e1.getMessage());
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
