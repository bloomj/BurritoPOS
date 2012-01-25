/**
 * 
 */
package com.burritopos.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.burritopos.domain.Manager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcMongoImpl implements IManagerSvc {

	private static Logger dLog = Logger.getLogger(ManagerSvcMongoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#storeManager(com.burritopos.domain.Manager)
	 */
	@Override
	public boolean storeManager(Manager i) throws Exception {
		dLog.info(new Date() + " | Entering method storeManager | Manager ID: "+i.getEmployeeID());
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("employee");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", i.getEmployeeID());
	        
	        dLog.trace(new Date() + " | Finding if manager exists");
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);
	        
	        query.put("firstName", i.getFirstName());
	        query.put("isManager", i.getIsManager());
	        query.put("lastName", i.getLastName());

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc == null) {
            	dLog.trace(new Date() + " | Inserting Manager");
            	coll.insert(query);
            	
            	result = true;
            }
            else {
            	dLog.trace(new Date() + " | Updating Manager");
            	WriteResult wr = coll.update(new BasicDBObject().append("id", i.getEmployeeID()), query);
            	
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
			dLog.error(new Date() + " | Exception in storeManager: "+e.getMessage());
		}
		
		dLog.trace(new Date() + " | storeManager result: " + result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#getManager(java.lang.Integer)
	 */
	@Override
	public Manager getManager(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getManager | Manager ID: "+id);
		Manager i = new Manager();
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("employee");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc != null) {
                i.setEmployeeID(id);
                i.setFirstName(myDoc.getString("firstName"));
                i.setIsManager(myDoc.getBoolean("isManager"));
                i.setLastName(myDoc.getString("lastName"));
            }
            dLog.trace(new Date() + " | Finished setting manager");
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getManager: "+e.getMessage());
		}

		return i;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#deleteManager(java.lang.Integer)
	 */
	@Override
	public boolean deleteManager(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteManger | Manager ID: "+id);
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("employee");
	        
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
			dLog.error(new Date() + " | Exception in deleteManger: "+e.getMessage());
			result = false;
		}

		return result;
	}

}
