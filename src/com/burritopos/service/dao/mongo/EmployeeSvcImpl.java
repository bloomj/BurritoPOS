/**
 * 
 */
package com.burritopos.service.dao.mongo;

import org.apache.log4j.Logger;

import com.burritopos.domain.Employee;
import com.burritopos.service.dao.IEmployeeSvc;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImpl implements IEmployeeSvc {
	private static Logger dLog = Logger.getLogger(EmployeeSvcImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.IEmployeeSvc#storeEmployee(com.burritopos.domain.Employee)
	 */
	@Override
	public boolean storeEmployee(Employee e) throws Exception {
		dLog.info("Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
		boolean result = false;
		
		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("employee");

		BasicDBObject query = new BasicDBObject();
		query.put("id", e.getEmployeeID());

		dLog.trace("Finding if employee exists");
		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		query.put("firstName", e.getFirstName());
		query.put("isManager", e.getIsManager());
		query.put("lastName", e.getLastName());

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc == null) {
			dLog.trace("Inserting Employeee");
			coll.insert(query);

			result = true;
		}
		else {
			dLog.trace("Updating Employee");
			WriteResult wr = coll.update(new BasicDBObject().append("id", e.getEmployeeID()), query);

			dLog.trace("Write Result: " + wr.getN());
			if(wr.getError() == null && wr.getN() == 1) {
				result = true;
			}
			else {
				dLog.error("Write Error: " + wr.getError());
				throw new Exception("Unable to update employee: " + wr.getError());
			}
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IEmployeeSvc#getEmployee(java.lang.Integer)
	 */
	@Override
	public Employee getEmployee(Integer id) throws Exception {
		dLog.info("Entering method getEmployee | Employee ID: "+id);
		Employee e = new Employee();
		
		Mongo m = new Mongo();
		DB db = m.getDB("neatoBurrito");
		DBCollection coll = db.getCollection("employee");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);

		BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

		//ensure we were passed a valid object before attempting to write
		dLog.trace("myDoc: " + myDoc);
		if(myDoc != null) {
			e.setEmployeeID(id);
			e.setFirstName(myDoc.getString("firstName"));
			e.setIsManager(myDoc.getBoolean("isManager"));
			e.setLastName(myDoc.getString("lastName"));
		}
		dLog.trace("Finished setting employee");

		return e;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IEmployeeSvc#deleteEmployee(java.lang.Integer)
	 */
	@Override
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info("Entering method deleteEmployee | Employee ID:"+id);
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
			throw new Exception("Unable to delete employee: " + wr.getError());
		}

		return result;
	}

}
