/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Inventory;
import com.burritopos.service.dao.IInventorySvc;

/**
 * @author james.bloom
 *
 */
public class InventorySvcImpl extends BaseSvcImpl implements IInventorySvc {
	private static Logger dLog = Logger.getLogger(InventorySvcImpl.class);

	@Override
	public Inventory getInventory(Integer id) throws ClassNotFoundException, Exception {
		dLog.info("Entering method getInventory | Inventory ID: "+id);
		Inventory i = null;
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			i = (Inventory)session.get(Inventory.class, id);
			tranx.commit();
		} 
		catch(Exception e) {
			dLog.error("Exception in getInventory", e);
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
		dLog.info("Entering method storeInventory | Inventory ID: "+i.getId());
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
				dLog.info("Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error("Exception in storeInventory", e);
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
		dLog.info("Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Inventory where id = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("HQL: " + hql);
			if (row > 0) {
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error("Exception in deleteInventory", e);
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
