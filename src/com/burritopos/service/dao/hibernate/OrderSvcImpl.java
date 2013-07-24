/**
 * 
 */
package com.burritopos.service.dao.hibernate;

import org.hibernate.*;
import org.apache.log4j.*;

import com.burritopos.domain.Order;
import com.burritopos.service.dao.IOrderSvc;

import java.util.ArrayList;
import java.util.List;


/**
 * @author james.bloom
 *
 */
public class OrderSvcImpl extends BaseSvcImpl implements IOrderSvc {
	private static Logger dLog = Logger.getLogger(OrderSvcImpl.class);

	@Override
	public Order getOrder(Integer id) throws ClassNotFoundException, Exception {
		dLog.info("Entering method getOrder | Order ID: "+id);
		Order o = new Order();
		Session session = null;

		try {
			session = getSession();
			session.clear();
			Transaction tranx = session.beginTransaction();
			o = (Order)session.get(Order.class, id);
			tranx.commit();
			session.flush();
			session.evict(Order.class);
		} 
		catch(Exception e) {
			dLog.error("Exception in getOrder: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			//ensure that session is close regardless of the errors in try/catch
			if(session != null) {
				session.close();
			}
		}

		return o;
	}

	@Override
	public boolean storeOrder(Order o) throws Exception {
		dLog.info("Entering method storeOrder | Order ID: "+o.getOrderID());
		boolean result = false;
		Session session = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(o.validate()) {
				session = getSession();
				session.clear();
				Transaction tranx = session.beginTransaction();
				String hql = "from Order where orderid = " + o.getOrderID().toString();
				Query query = session.createQuery(hql);

				dLog.trace("OrderID: " + o.getOrderID().toString() + " | Exists: " + !query.list().isEmpty());
				if (query.list().isEmpty()) {
					dLog.trace("Saving the new order");
					session.save(o);
				}
				else {
					dLog.trace("Updating the order");
					session.merge(o);
				}

				tranx.commit();
				result = tranx.wasCommitted();
				session.flush();
				session.evict(Order.class);

				dLog.info("Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error("Exception in storeOrder: "+e.getMessage());
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
	public boolean deleteOrder(Integer id) throws Exception {
		dLog.info("Entering method deleteOrder | Order ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			session.clear();
			String hql = "delete from Order where orderid = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			dLog.trace("HQL: " + hql);
			dLog.trace("Order rows deleted: " + row);
			if (row > 0) {
				result = true;
			}

			dLog.trace("Deleted order: " + result);
		}
		catch(Exception e) {
			dLog.error("Exception in deleteOrder: "+e.getMessage());
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
	public ArrayList<Order> getAllOrders() throws Exception {
		dLog.info("Entering method getAllOrders");
		ArrayList<Order> result = new ArrayList<Order>();
		Session session = null;

		try {
			session = getSession();
			session.clear();
			Transaction tranx = session.beginTransaction();
			
			String hql = "select from Order";
			Query query = session.createQuery(hql);
			List<?> list = query.list();
			
			for(int n=0; n<list.size(); n++){
				result.add((Order)list.get(n));
			}
			
			tranx.commit();
			session.flush();
			session.evict(Order.class);
		} 
		catch(Exception e) {
			dLog.error("Exception in getAllOrders: "+e.getMessage());
			e.printStackTrace();
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
