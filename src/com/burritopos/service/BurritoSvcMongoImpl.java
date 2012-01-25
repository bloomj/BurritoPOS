/**
 * 
 */
package com.burritopos.service;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.burritopos.domain.Burrito;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcMongoImpl implements IBurritoSvc {

	private static Logger dLog = Logger.getLogger(BurritoSvcMongoImpl.class);

	@Override
	public boolean storeBurrito(Burrito b) throws Exception {
		dLog.info(new Date() + " | Entering method storeBurrito | Burrito ID: "+b.getId());
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("burrito");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", b.getId());
	        
	        dLog.trace(new Date() + " | Finding if burrito exists");
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);
	        
	        query.put("beef", b.isBeef());
	        query.put("blackBeans", b.isBlackBeans());
	        query.put("brownRice", b.isBrownRice());
	        query.put("chicken", b.isChicken());
	        query.put("chiliTortilla", b.isChiliTortilla());
	        query.put("cucumber", b.isCucumber());
	        query.put("flourTortilla", b.isFlourTortilla());
	        query.put("guacamole", b.isGuacamole());
	        query.put("herbGarlicTortilla", b.isHerbGarlicTortilla());
	        query.put("hummus", b.isHummus());
	        query.put("jalapenoCheddarTortilla", b.isJalapenoCheddarTortilla());
	        query.put("jalapenos", b.isJalapenos());
	        query.put("lettuce", b.isLettuce());
	        query.put("onion", b.isOnion());
	        query.put("pintoBeans", b.isPintoBeans());
	        query.put("price", Double.valueOf(b.getPrice().toString()));
	        query.put("salsaPico", b.isSalsaPico());
	        query.put("salsaSpecial", b.isSalsaSpecial());
	        query.put("salsaVerde", b.isSalsaVerde());
	        query.put("tomatoBasilTortilla", b.isTomatoBasilTortilla());
	        query.put("tomatoes", b.isTomatoes());
            query.put("wheatTortilla", b.isWheatTortilla());
            query.put("whiteRice", b.isWhiteRice());

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc == null) {
            	dLog.trace(new Date() + " | Inserting Burrito");
            	coll.insert(query);
            	
            	result = true;
            }
            else {
            	dLog.trace(new Date() + " | Updating Burrito");
            	WriteResult wr = coll.update(new BasicDBObject().append("id", b.getId()), query);
            	
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
			dLog.error(new Date() + " | Exception in storeBurrito: "+e.getMessage());
		}
		
		return result;
	}

	@Override
	public Burrito getBurrito(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getBurrito | ID: " + id);
		Burrito b = new Burrito();
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("burrito");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc != null) {
                b.setId(id);
                b.setBeef(myDoc.getBoolean("beef"));
                b.setBlackBeans(myDoc.getBoolean("blackBeans"));
                b.setBrownRice(myDoc.getBoolean("brownRice"));
                b.setChicken(myDoc.getBoolean("chicken"));
                b.setChiliTortilla(myDoc.getBoolean("chiliTortilla"));
                b.setCucumber(myDoc.getBoolean("cucumber"));
                b.setFlourTortilla(myDoc.getBoolean("flourTortilla"));
                b.setGuacamole(myDoc.getBoolean("guacamole"));
                b.setHerbGarlicTortilla(myDoc.getBoolean("herbGarlicTortilla"));
                b.setHummus(myDoc.getBoolean("hummus"));
                b.setJalapenoCheddarTortilla(myDoc.getBoolean("jalapenoCheddarTortilla"));
                b.setJalapenos(myDoc.getBoolean("jalapenos"));
                b.setLettuce(myDoc.getBoolean("lettuce"));
                b.setOnion(myDoc.getBoolean("onion"));
                b.setPintoBeans(myDoc.getBoolean("pintoBeans"));
                b.setPrice(BigDecimal.valueOf(myDoc.getDouble("price")));
                b.setSalsaPico(myDoc.getBoolean("salsaPico"));
                b.setSalsaSpecial(myDoc.getBoolean("salsaSpecial"));
                b.setSalsaVerde(myDoc.getBoolean("salsaVerde"));
                b.setTomatoBasilTortilla(myDoc.getBoolean("tomatoBasilTortilla"));
                b.setTomatoes(myDoc.getBoolean("tomatoes"));
                b.setWheatTortilla(myDoc.getBoolean("wheatTortilla"));
                b.setWhiteRice(myDoc.getBoolean("whiteRice"));
            }
            dLog.trace(new Date() + " | Finished setting burrito");
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getBurrito: "+e.getMessage());
		}
		
		return b;
	}

	@Override
	public boolean deleteBurrito(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("burrito");
	        
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
			dLog.error(new Date() + " | Exception in deleteBurrito: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
