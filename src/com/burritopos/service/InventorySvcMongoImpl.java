/**
 * 
 */
package com.burritopos.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.burritopos.domain.Inventory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class InventorySvcMongoImpl implements IInventorySvc {

	private static Logger dLog = Logger.getLogger(InventorySvcJDBCImpl.class);
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.IInventorySvc#storeInventory(com.burritopos.domain.Inventory)
	 */
	@Override
	public boolean storeInventory(Inventory i) throws Exception {
		dLog.info(new Date() + " | Entering method storeInventory | Inventory ID: "+i.getId());
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("inventory");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", i.getId());
	        
	        dLog.trace(new Date() + " | Finding if inventory exists");
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);
	        
	        query.put("beefQty", i.getBeefQty());
	        query.put("blackBeansQty", i.getBlackBeansQty());
	        query.put("brownRiceQty", i.getBrownRiceQty());
	        query.put("chickenQty", i.getChickenQty());
	        query.put("chiliTortillaQty", i.getChiliTortillaQty());
	        query.put("cucumberQty", i.getCucumberQty());
	        query.put("flourTortillaQty", i.getFlourTortillaQty());
	        query.put("guacamoleQty", i.getGuacamoleQty());
	        query.put("herbGarlicTortillaQty", i.getHerbGarlicTortillaQty());
	        query.put("hummusQty", i.getHummusQty());
	        query.put("jalapenoCheddarTortillaQty", i.getJalapenoCheddarTortillaQty());
	        query.put("jalapenosQty", i.getJalapenosQty());
	        query.put("lettuceQty", i.getLettuceQty());
	        query.put("onionQty", i.getOnionQty());
	        query.put("pintoBeansQty", i.getPintoBeansQty());
	        query.put("salsaPicoQty", i.getSalsaPicoQty());
	        query.put("salsaSpecialQty", i.getSalsaSpecialQty());
	        query.put("salsaVerdeQty", i.getSalsaVerdeQty());
	        query.put("tomatoBasilTortillaQty", i.getTomatoBasilTortillaQty());
	        query.put("tomatoesQty", i.getTomatoesQty());
            query.put("wheatTortillaQty", i.getWheatTortillaQty());
            query.put("whiteRiceQty", i.getWhiteRiceQty());

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc == null) {
            	dLog.trace(new Date() + " | Inserting Inventory");
            	coll.insert(query);
            	
            	result = true;
            }
            else {
            	dLog.trace(new Date() + " | Updating Inventory");
            	WriteResult wr = coll.update(new BasicDBObject().append("id", i.getId()), query);
            	
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
			dLog.error(new Date() + " | Exception in storeInventory: "+e.getMessage());
			System.out.println(new Date() + " | Exception in storeInventory: "+e.getMessage());
			result = false;
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IInventorySvc#getInventory(java.lang.Integer)
	 */
	@Override
	public Inventory getInventory(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getInventory | Inventory ID: "+id);
		Inventory i = new Inventory();
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("inventory");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc != null) {
                i.setId(id);
                i.setBeefQty(myDoc.getInt("beefQty"));
                i.setBlackBeansQty(myDoc.getInt("blackBeansQty"));
                i.setBrownRiceQty(myDoc.getInt("brownRiceQty"));
                i.setChickenQty(myDoc.getInt("chickenQty"));
                i.setChiliTortillaQty(myDoc.getInt("chiliTortillaQty"));
                i.setCucumberQty(myDoc.getInt("cucumberQty"));
                i.setFlourTortillaQty(myDoc.getInt("flourTortillaQty"));
                i.setGuacamoleQty(myDoc.getInt("guacamoleQty"));
                i.setHerbGarlicTortillaQty(myDoc.getInt("herbGarlicTortillaQty"));
                i.setHummusQty(myDoc.getInt("hummusQty"));
                i.setJalapenoCheddarTortillaQty(myDoc.getInt("jalapenoCheddarTortillaQty"));
                i.setJalapenosQty(myDoc.getInt("jalapenosQty"));
                i.setLettuceQty(myDoc.getInt("lettuceQty"));
                i.setOnionQty(myDoc.getInt("onionQty"));
                i.setPintoBeansQty(myDoc.getInt("pintoBeansQty"));
                i.setSalsaPicoQty(myDoc.getInt("salsaPicoQty"));
                i.setSalsaSpecialQty(myDoc.getInt("salsaSpecialQty"));
                i.setSalsaVerdeQty(myDoc.getInt("salsaVerdeQty"));
                i.setTomatoBasilTortillaQty(myDoc.getInt("tomatoBasilTortillaQty"));
                i.setTomatoesQty(myDoc.getInt("tomatoesQty"));
                i.setWheatTortillaQty(myDoc.getInt("wheatTortillaQty"));
                i.setWhiteRiceQty(myDoc.getInt("whiteRiceQty"));
            }
            dLog.trace(new Date() + " | Finished setting inventory");
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getInventory: "+e.getMessage());
			System.out.println(new Date() + " | Exception in getInventory: "+e.getMessage());
		}
		
		return i;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IInventorySvc#deleteInventory(java.lang.Integer)
	 */
	@Override
	public boolean deleteInventory(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("inventory");
	        
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
			dLog.error(new Date() + " | Exception in deleteInventory: "+e.getMessage());
			result = false;
		}
		
		return result;
	}

}
