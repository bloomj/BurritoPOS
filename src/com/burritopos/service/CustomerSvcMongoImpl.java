/**
 * 
 */
package com.burritopos.service;
import java.util.Date;

import org.apache.log4j.Logger;

import com.burritopos.domain.Customer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcMongoImpl implements ICustomerSvc {

	private static Logger dLog = Logger.getLogger(CustomerSvcMongoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#storeCustomer(com.burritopos.domain.Customer)
	 */
	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info(new Date() + " | Entering method storeCustomer | Customer ID: "+c.getId());
		boolean result = false;

		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("customer");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", c.getId());
	        
	        dLog.trace(new Date() + " | Finding if customer exists");
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);
	        
	        query.put("emailAddress", c.getEmailAddress());
	        query.put("firstName", c.getFirstName());
	        query.put("lastName", c.getLastName());

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc == null) {
            	dLog.trace(new Date() + " | Inserting Customer");
            	coll.insert(query);
            	
            	result = true;
            }
            else {
            	dLog.trace(new Date() + " | Updating Customer");
            	WriteResult wr = coll.update(new BasicDBObject().append("id", c.getId()), query);
            	
            	dLog.trace(new Date() + " | WriteResult: " + wr.getN());
    	        if(wr.getError() == null && wr.getN() == 1) {
    	        	result = true;
    	        }
    	        else {
    	        	dLog.trace(new Date() + " | WriteResult: " + wr.getError());
    	        }
            }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeCustomer: "+e.getMessage());
			result = false;
		}

	return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#getCustomer(java.lang.Integer)
	 */
	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getCustomer | Customer ID: "+id);
		Customer c = new Customer();
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("customer");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc != null) {
                c.setId(id);
                c.setEmailAddress(myDoc.getString("emailAddress"));
                c.setFirstName(myDoc.getString("firstName"));
                c.setLastName(myDoc.getString("lastName"));
            }
            dLog.trace(new Date() + " | Finished setting customer");
		} 
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getCustomer: "+e.getMessage());
		}
		
		return c;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.ICustomerSvc#deleteCustomer(java.lang.Integer)
	 */
	@Override
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("customer");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        WriteResult wr = coll.remove(query);
	        
	        dLog.trace(new Date() + " | WriteResult: " + wr.getN());
	        if(wr.getN() == 1) {
	        	result = true;
	        }
	        else {
	        	dLog.trace(new Date() + " | WriteResult: " + wr.getError());
	        }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteCustomer: "+e.getMessage());
			result = false;
		}
	
	return result;
	}

}
