/**
 * 
 */
package com.burritopos.service.dao.mongo;

import org.apache.log4j.Logger;

import com.burritopos.domain.Customer;
import com.burritopos.service.dao.ICustomerSvc;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImpl implements ICustomerSvc {
	private static Logger dLog = Logger.getLogger(CustomerSvcImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#storeCustomer(com.burritopos.domain.Customer)
	 */
	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info("Entering method storeCustomer | Customer ID: "+c.getId());
		boolean result = false;

		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("customer");

		BasicDBObject query = new BasicDBObject();
		query.put("id", c.getId());

		dLog.trace("Finding if customer exists");
		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		query.put("emailAddress", c.getEmailAddress());
		query.put("firstName", c.getFirstName());
		query.put("lastName", c.getLastName());

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc == null) {
			dLog.trace("Inserting Customer");
			coll.insert(query);

			result = true;
		}
		else {
			dLog.trace("Updating Customer");
			WriteResult wr = coll.update(new BasicDBObject().append("id", c.getId()), query);

			dLog.trace("Write Result: " + wr.getN());
			if(wr.getError() == null && wr.getN() == 1) {
				result = true;
			}
			else {
				dLog.error("Write Error: " + wr.getError());
				throw new Exception("Unable to update customer: " + wr.getError());
			}
		}

	return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#getCustomer(java.lang.Integer)
	 */
	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info("Entering method getCustomer | Customer ID: "+id);
		Customer c = new Customer();
		
		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("customer");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);

		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc != null) {
			c.setId(id);
			c.setEmailAddress(myDoc.getString("emailAddress"));
			c.setFirstName(myDoc.getString("firstName"));
			c.setLastName(myDoc.getString("lastName"));
		}
		dLog.trace("Finished setting customer");

		return c;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#deleteCustomer(java.lang.Integer)
	 */
	@Override
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info("Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;
		
		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("customer");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);

		WriteResult wr = coll.remove(query);

		dLog.trace("Write Result: " + wr.getN());
		if(wr.getN() == 1) {
			result = true;
		}
		else {
			dLog.error("Write Error: " + wr.getError());
			throw new Exception("Unable to delete customer: " + wr.getError());
		}
	
		return result;
	}

}
