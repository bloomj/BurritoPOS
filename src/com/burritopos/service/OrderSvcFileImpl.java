/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Order;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class OrderSvcFileImpl implements IOrderSvc {

        private static Logger dLog = Logger.getLogger(OrderSvcFileImpl.class);

	@Override
	public Order getOrder(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getOrder | Order ID: "+id);
		Order o = null;
		ObjectInputStream input = null;
		
		try {
			File file = new File("Order_"+id+".txt");
			boolean exists = file.exists();
			
			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Order_"+id+".txt"));
				o = (Order)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in getOrder: "+e1.getMessage());
		}
		catch (ClassNotFoundException e2) {
			dLog.error(new Date() + " | ClassNotFoundException in getOrder: "+e2.getMessage());
		}
		catch(Exception e3) {
			dLog.error(new Date() + " | Exception in getOrder: "+e3.getMessage());
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}
		
		return o;
	}

	@Override
	public boolean storeOrder(Order o) throws IOException, Exception {
		dLog.info(new Date() + " | Entering method storeOrder | Order ID: "+o.getOrderID());
		ObjectOutputStream output = null;
		boolean result = false;
		
		try {
			//ensure we were passed a valid object before attempting to write
			if(o.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Order_"+o.getOrderID()+".txt"));
				output.writeObject(o);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in storeOrder: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeOrder: "+e2.getMessage());
			result = false;
		}
		finally {
			//ensure that output is close regardless of the errors in try/catch
			if(output != null) {
				output.flush();
				output.close();
			}
		}
		
		return result;
	}

	@Override
	public boolean deleteOrder(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteOrder | Order ID:"+id);
		boolean result = false;
		
		try {
		    File f = new File("Order_"+id+".txt");

		    // Ensure the file exists
		    if (!f.exists())
		      throw new IllegalArgumentException("deleteOrder: no such file or directory: Order_"+id+".txt");

		    // Ensure the file is not locked
		    if (!f.canWrite())
		      throw new IllegalArgumentException("deleteOrder: write protected: Order_"+id+".txt");	
		    
		    // Attempt to delete it
		    result = f.delete();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteOrder: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
