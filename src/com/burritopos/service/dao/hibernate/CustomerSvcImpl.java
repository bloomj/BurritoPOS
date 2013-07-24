/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Customer;
import com.burritopos.service.dao.ICustomerSvc;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImpl extends BaseSvcImpl implements ICustomerSvc {
	private static Logger dLog = Logger.getLogger(CustomerSvcImpl.class);

	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info("Entering method getCustomer | Customer ID: "+id);
		Customer c = null;
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			c = (Customer)session.get(Customer.class, id);
			tranx.commit();
		} 
		catch(Exception e) {
			dLog.error("Exception in getCustomer: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return c;
	}

	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info("Entering method storeCustomer | Customer ID: "+c.getId());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(c.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				session.save(c);
				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info("Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error("Exception in storeCustomer: "+e.getMessage());
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
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info("Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Customer where id = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("HQL: " + hql);
			if (row > 0) {
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error("Exception in deleteCustomer: "+e.getMessage());
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
