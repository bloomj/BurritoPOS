/**
 * 
 */
package com.burritopos.service.dao.mongo;

import org.apache.log4j.Logger;

import com.burritopos.domain.Manager;
import com.burritopos.service.dao.IManagerSvc;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImpl implements IManagerSvc {
	private static Logger dLog = Logger.getLogger(ManagerSvcImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#storeManager(com.burritopos.domain.Manager)
	 */
	@Override
	public boolean storeManager(Manager i) throws Exception {
		dLog.info("Entering method storeManager | Manager ID: "+i.getEmployeeID());
		boolean result = false;
		
		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("employee");

		BasicDBObject query = new BasicDBObject();
		query.put("id", i.getEmployeeID());

		dLog.trace("Finding if manager exists");
		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		query.put("firstName", i.getFirstName());
		query.put("isManager", i.getIsManager());
		query.put("lastName", i.getLastName());

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc == null) {
			dLog.trace("Inserting Manager");
			coll.insert(query);

			result = true;
		}
		else {
			dLog.trace("Updating Manager");
			WriteResult wr = coll.update(new BasicDBObject().append("id", i.getEmployeeID()), query);

			dLog.trace("Write Result: " + wr.getN());
			if(wr.getError() == null && wr.getN() == 1) {
				result = true;
			}
			else {
				dLog.error("Write Error: " + wr.getError());
				throw new Exception("Unable to update manager: " + wr.getError());
			}
		}

		dLog.trace("storeManager result: " + result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#getManager(java.lang.Integer)
	 */
	@Override
	public Manager getManager(Integer id) throws Exception {
		dLog.info("Entering method getManager | Manager ID: "+id);
		Manager i = new Manager();

		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("employee");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);

		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc != null) {
			i.setEmployeeID(id);
			i.setFirstName(myDoc.getString("firstName"));
			i.setIsManager(myDoc.getBoolean("isManager"));
			i.setLastName(myDoc.getString("lastName"));
		}
		dLog.trace("Finished setting manager");

		return i;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IManagerSvc#deleteManager(java.lang.Integer)
	 */
	@Override
	public boolean deleteManager(Integer id) throws Exception {
		dLog.info("Entering method deleteManger | Manager ID: "+id);
		boolean result = false;

		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("employee");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);

		WriteResult wr = coll.remove(query);

		dLog.trace("Write Result: " + wr.getN());
		if(wr.getN() == 1) {
			result = true;
		}
		else {
			dLog.error("Write Error: " + wr.getError());
			throw new Exception("Unable to delete manager: " + wr.getError());
		}

		return result;
	}

}
