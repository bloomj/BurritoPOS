/**
 * 
 */
package com.burritopos.service;

import org.hibernate.*;
import org.apache.log4j.*;

import com.burritopos.domain.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author james.bloom
 *
 */
public class OrderSvcHibernateImpl extends BaseSvcHibernateImpl implements IOrderSvc {

	private static Logger dLog = Logger.getLogger(OrderSvcHibernateImpl.class);

	@Override
	public Order getOrder(Integer id) throws ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getOrder | Order ID: "+id);
		System.out.println(new Date() + " | Entering method getOrder | Order ID: "+id);
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
			dLog.error(new Date() + " | Exception in getOrder: "+e.getMessage());
			System.out.println(new Date() + " | Exception in getOrder: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method storeOrder | Order ID: "+o.getOrderID());
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

				System.out.println("OrderID: " + o.getOrderID().toString() + " | Exists: " + !query.list().isEmpty());
				if (query.list().isEmpty()) {
					System.out.println("Saving the new order");
					session.save(o);
				}
				else {
					System.out.println("Updating the order");
					session.merge(o);
				}

				tranx.commit();
				result = tranx.wasCommitted();
				session.flush();
				session.evict(Order.class);

				dLog.info(new Date() + " | Was committed: "+result);
			}
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeOrder: "+e.getMessage());
			System.out.println(new Date() + " | Exception in storeOrder: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method deleteOrder | Order ID:"+id);
		System.out.println(new Date() + " | Entering method deleteOrder | Order ID:"+id);
		boolean result = false;
		Session session = null;

		try {
			session = getSession();
			session.clear();
			String hql = "delete from Order where orderid = " + id.toString();
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			System.out.println("Order rows deleted: " + row);
			if (row == 0)
				result = false;
			else
				result = true;

			System.out.println("Deleted order: " + result);
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteOrder: "+e.getMessage());
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
		dLog.info(new Date() + " | Entering method getAllOrders");
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
			dLog.error(new Date() + " | Exception in getAllOrders: "+e.getMessage());
			System.out.println(new Date() + " | Exception in getAllOrders: "+e.getMessage());
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
