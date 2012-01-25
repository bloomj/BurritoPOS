/**
 * 
 */
package com.burritopos.service;

import org.hibernate.*;
//import java.math.BigDecimal;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;

import java.util.Date;
import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcHibernateImpl extends BaseSvcHibernateImpl implements IBurritoSvc {

	private static Logger dLog = Logger.getLogger(BurritoSvcHibernateImpl.class);

	@Override
	public Burrito getBurrito(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getBurrito | ID: " + id);
		Burrito b = new Burrito();
		Session session = null;

		try {
			session = getSession();
			Transaction tranx = session.beginTransaction();
			b = (Burrito)session.get(Burrito.class, id);
			tranx.commit();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getBurrito: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method storeBurrito | Burrito ID: "+b.getId());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(b.validate()) {
				session = getSession();
				Transaction tranx = session.beginTransaction();
				String hql = "from Burrito where id = " + b.getId().toString();
				Query query = session.createQuery(hql);

				if (query.list().isEmpty())
					session.save(b);
				else
					session.update(b);

				tranx.commit();
				result = tranx.wasCommitted();
				dLog.info(new Date() + " | Was committed: "+result);
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeBurrito: "+e.getMessage());
			System.out.println("Exception in storeBurrito: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			String hql = "delete from Burrito where id = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			if (row == 0)
				result = false;
			else
				result = true;
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteBurrito: "+e.getMessage());
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
