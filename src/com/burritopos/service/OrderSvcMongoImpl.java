/**
 * 
 */
package com.burritopos.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author james.bloom
 *
 */
public class OrderSvcMongoImpl implements IOrderSvc {

	private Factory factory;
	private static Logger dLog = Logger.getLogger(OrderSvcMongoImpl.class);
	private IBurritoSvc burritoSvc;
	
	/**
	 * 
	 * @throws ServiceLoadException
	 */
	public OrderSvcMongoImpl() throws ServiceLoadException {
		factory = Factory.getInstance();
		burritoSvc = (IBurritoSvc) factory.getService(IBurritoSvc.NAME);
	}
	
	// needed for Spring IoC
	/*public void setBurritoSvc(IBurritoSvc burritoSvc) {
    	//this.burritoSvc = burritoSvc;
    }*/
	
	/* (non-Javadoc)
	 * @see com.burritopos.service.IOrderSvc#storeOrder(com.burritopos.domain.Order)
	 */
	@Override
	public boolean storeOrder(Order o) throws Exception {
		dLog.info(new Date() + " | Entering method storeOrder | Order ID: "+o.getOrderID());
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("order");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", o.getOrderID());
	        
	        dLog.trace(new Date() + " | Finding if order exists");
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);
	        
	        query.put("isComplete", o.getIsComplete());
	        query.put("isSubmitted", o.getIsSubmitted());
	        query.put("orderDate", o.getOrderDate().toString());
	        query.put("totalCost", Double.valueOf(o.getTotalCost().toString()));

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc == null) {
            	dLog.trace(new Date() + " | Inserting Order");
            	coll.insert(query);
            	
            	result = true;
            }
            else {
            	dLog.trace(new Date() + " | Updating Order");
            	WriteResult wr = coll.update(new BasicDBObject().append("id", o.getOrderID()), query);
            	
            	dLog.trace(new Date() + " | WriteResult: " + wr.getN());
    	        if(wr.getError() == null && wr.getN() == 1) {
    	        	result = true;
    	        }
    	        else {
    	        	dLog.trace(new Date() + " | WriteResult: " + wr.getError());
    	        }
            }
            
        	//now insert the burritos
            if(result) {
				System.out.println("Trying to insert " + o.getBurritos().size() + " burritos");
				
				for(int n=0; n<o.getBurritos().size(); n++){
					//ensure we were passed a valid object before attempting to write
					Burrito b = o.getBurritos().get(n);
					b.setOrderId(o.getOrderID());
					
					System.out.println("Set order ID for burrito: " + n);
					if(b.validate()) {
						System.out.println("Storing burrito: " + n);
						result = burritoSvc.storeBurrito(b);
					}
				}
            }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in storeOrder: "+e.getMessage());
			System.out.println(new Date() + " | Exception in storeOrder: "+e.getMessage());
			result = false;
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IOrderSvc#getOrder(java.lang.Integer)
	 */
	@Override
	public Order getOrder(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getOrder | Order ID: "+id);
		System.out.println(new Date() + " | Entering method getOrder | Order ID: "+id);
		Order o = new Order();
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("order");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        BasicDBObject myDoc = (BasicDBObject) coll.findOne(query);

	        //ensure we were passed a valid object before attempting to write
	        dLog.trace(new Date() + " | myDoc: " + myDoc);
            if(myDoc != null) {
            	DateFormat dateFtmr = new SimpleDateFormat("E MMM dd hh:mm:ss zzz yyyy");
            	
            	o.setOrderID(id);
            	o.setIsComplete(myDoc.getBoolean("isComplete"));
            	o.setIsSubmitted(myDoc.getBoolean("isSubmitted"));
            	o.setOrderDate(dateFtmr.parse(myDoc.getString("orderDate")));
            	o.setTotalCost(BigDecimal.valueOf(myDoc.getDouble("totalCost")));
            	
            	//get burritos
            	System.out.println(new Date() + " | Getting burritos");
            	coll = db.getCollection("burrito");
            	query.clear();
    	        query.put("orderID", id);
    	        DBCursor cur = coll.find(query);
    	        
    	        o.setBurritos(new ArrayList<Burrito>());
    	        while(cur.hasNext()) {
    	        	//cleaner but too many extra queries this way
    	        	//o.getBurritos().add(burritoSvc.getBurrito(((BasicDBObject)cur.next()).getInt("id")));
    	        	
    	        	myDoc = (BasicDBObject)cur.next();
    	        	Burrito b = new Burrito();
    	        	
    	        	b.setId(myDoc.getInt("id"));
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
                    
                    o.getBurritos().add(b);
    	        }
            }
            dLog.trace(new Date() + " | Finished setting order");
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getOrder: "+e.getMessage());
			System.out.println(new Date() + " | Exception in getOrder: "+e.getMessage());
		}

		return o;
	}

	/* (non-Javadoc)
	 * @see com.burritopos.service.IOrderSvc#deleteOrder(java.lang.Integer)
	 */
	@Override
	public boolean deleteOrder(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteOrder | Order ID: "+id);
		boolean result = false;
		
		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("order");
	        
	        BasicDBObject query = new BasicDBObject();
	        query.put("id", id);
	        
	        WriteResult wr = coll.remove(query);
	        
	        dLog.trace(new Date() + " | WriteResult: " + wr.getN());
	        if(wr.getN() == 1) {
	        	//now delete the burritos
	        	System.out.println(new Date() + " | Deleting burritos");
            	coll = db.getCollection("burrito");
            	query.clear();
    	        query.put("orderID", id);
    	        wr = coll.remove(query);
	        	
	        	result = true;
	        }
	        else {
	        	dLog.trace(new Date() + " | WriteResult: " + wr.getError());
	        }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteOrder: "+e.getMessage());
			result = false;
		}

		return result;
	}

	//TODO: come back and reduce number of reads on DB
	@Override
	public ArrayList<Order> getAllOrders() throws Exception {
		dLog.info(new Date() + " | Entering method getAllOrders");
		ArrayList<Order> result = new ArrayList<Order>();

		try {
			Mongo m = new Mongo();
	        DB db = m.getDB("neatoBurrito");
	        DBCollection coll = db.getCollection("order");
	        
	        DBCursor cur = coll.find();

			//add this order 
	        while(cur.hasNext()) {
	        	result.add(getOrder(((BasicDBObject)cur.next()).getInt("id")));
	        }
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in getAllOrders: "+e.getMessage());
		}
		
		return result;
	}

}
