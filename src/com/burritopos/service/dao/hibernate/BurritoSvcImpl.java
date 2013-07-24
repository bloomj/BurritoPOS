/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
//import java.math.BigDecimal;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.service.dao.IBurritoSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcImpl extends BaseSvcImpl implements IBurritoSvc {
	private static Logger dLog = Logger.getLogger(BurritoSvcImpl.class);

	@Override
	public Burrito getBurrito(Integer id) throws Exception {
		dLog.info("Entering method getBurrito | ID: " + id);
		Burrito b = new Burrito();
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			b = (Burrito)session.get(Burrito.class, id);
			tranx.commit();
		}
		catch(Exception e) {
			dLog.error("Exception in getBurrito: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return b;
	}

	@Override
	public boolean storeBurrito(Burrito b) throws SQLException, Exception {
		dLog.info("Entering method storeBurrito | Burrito ID: "+b.getId());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(b.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				String hql = "from Burrito where id = " + b.getId().toString();
				Query query = session.createQuery(hql);

				dLog.trace("HQL: " + hql);
				if (query.list().isEmpty()) {
					session.save(b);
				}
				else {
					session.update(b);
				}

				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info("Was committed: "+result);
			}
		}
		catch(Exception e) {
			dLog.error("Exception in storeBurrito: "+e.getMessage());
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
	public boolean deleteBurrito(Integer id) throws SQLException, Exception {
		dLog.info("Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Burrito where id = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("hql: " + hql);
			if (row > 0) {
				result = true;
			}
		}
		catch(Exception e) {
			dLog.error("Exception in deleteBurrito: "+e.getMessage());
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
