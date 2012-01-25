/**
 * 
 */
package com.burritopos.service;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Inventory;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class InventorySvcHibernateImpl extends BaseSvcHibernateImpl implements IInventorySvc {

	private static Logger dLog = Logger.getLogger(InventorySvcHibernateImpl.class);

	@Override
	public Inventory getInventory(Integer id) throws ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getInventory | Inventory ID: "+id);
		Inventory i = null;
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			i = (Inventory)session.get(Inventory.class, id);
			tranx.commit();
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getInventory: "+e.getMessage());
			System.out.println(new Date() + " | Exception in getInventory: "+e.getMessage());
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return i;
	}

	@Override
	public boolean storeInventory(Inventory i) throws Exception {
		dLog.info(new Date() + " | Entering method storeInventory | Inventory ID: "+i.getId());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(i.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				session.save(i);
				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info(new Date() + " | Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeInventory: "+e.getMessage());
			System.out.println(new Date() + " | Exception in storeInventory: "+e.getMessage());
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
	public boolean deleteInventory(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Inventory where id = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			if (row == 0)
				result = false;
			else
				result = true;
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteInventory: "+e.getMessage());
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
