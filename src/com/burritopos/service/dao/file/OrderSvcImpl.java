/**
 * 
 */
package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Order;
import com.burritopos.service.dao.IOrderSvc;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * @author james.bloom
 *
 */
public class OrderSvcImpl implements IOrderSvc {
	private static Logger dLog = Logger.getLogger(OrderSvcImpl.class);

	@Override
	public Order getOrder(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getOrder | Order ID: "+id);
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
			dLog.error("IOException in getOrder", e1);
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getOrder", e2);
		}
		catch(Exception e3) {
			dLog.error("Exception in getOrder: ", e3);
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
		dLog.info("Entering method storeOrder | Order ID: "+o.getOrderID());
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
			dLog.error("IOException in storeOrder", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeOrder", e2);
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
		dLog.info("Entering method deleteOrder | Order ID:"+id);
		boolean result = false;

		File f = new File("Order_"+id+".txt");

		// Ensure the file exists
		if (!f.exists()) {
			throw new IllegalArgumentException("deleteOrder: no such file or directory: Order_"+id+".txt");
		}

		// Ensure the file is not locked
		if (!f.canWrite()) {
			throw new IllegalArgumentException("deleteOrder: write protected: Order_"+id+".txt");	
		}

		// Attempt to delete it
		result = f.delete();

		return result;
	}

	@Override
	public ArrayList<Order> getAllOrders() throws Exception {
		dLog.info("Entering method getAllOrders");
		ArrayList<Order> result = new ArrayList<Order>();

		File dir1 = new File (".");
		dLog.trace("Current directory: " + dir1.getCanonicalPath());

		String[] children = dir1.list(); 
		if (children != null) { 
			for (int i=0; i<children.length; i++) { 
				// Get filename of file or directory 
				String filename = children[i];
				dLog.trace("  - On file: " + filename);
				StringTokenizer st = new StringTokenizer(filename, "_");
				String firstTok = st.nextToken();
				dLog.trace("  - First Token: " + firstTok);
				
				if(firstTok.equalsIgnoreCase("Order")) {
					String secondTok = st.nextToken();
					dLog.trace("  - Second Token: " + secondTok);
					String[] parts = secondTok.split(File.separator + ".");
					dLog.trace("  - Parts Length: " + parts.length);
					
					if(parts.length > 0) {
						Integer tOrderID = Integer.parseInt(parts[0]);
						dLog.trace("  - Found order: " + tOrderID);

						//add this file 
						result.add(getOrder(tOrderID));
					}
				}
			}
		}
		
		return result;
	}
}
