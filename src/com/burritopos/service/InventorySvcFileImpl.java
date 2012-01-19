/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Inventory;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class InventorySvcFileImpl implements IInventorySvc {

        private static Logger dLog = Logger.getLogger(InventorySvcFileImpl.class);

	@Override
	public Inventory getInventory(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getInventory | Inventory ID: "+id);
		Inventory i = null;
		ObjectInputStream input = null;
		
		try {
			File file = new File("Inventory_"+id+".txt");
			boolean exists = file.exists();
			
			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Inventory_"+id+".txt"));
				i = (Inventory)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in getInventory: "+e1.getMessage());
		}
		catch (ClassNotFoundException e2) {
			dLog.error(new Date() + " | ClassNotFoundException in getInventory: "+e2.getMessage());
		}
		catch(Exception e3) {
			dLog.error(new Date() + " | Exception in getInventory: "+e3.getMessage());
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}
		
		return i;
	}

	@Override
	public boolean storeInventory(Inventory i) throws IOException, Exception {
		dLog.info(new Date() + " | Entering method storeInventory | Inventory ID: "+i.getId());
		ObjectOutputStream output = null;
		boolean result = false;
		
		try {
			//ensure we were passed a valid object before attempting to write
			if(i.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Inventory_"+i.getId()+".txt"));
				output.writeObject(i);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in storeInventory: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeInventory: "+e2.getMessage());
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
	public boolean deleteInventory(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		
		try {
		    File f = new File("Inventory_"+id+".txt");

		    // Ensure the file exists
		    if (!f.exists())
		      throw new IllegalArgumentException("deleteInventory: no such file or directory: Inventory_"+id+".txt");

		    // Ensure the file is not locked
		    if (!f.canWrite())
		      throw new IllegalArgumentException("deleteInventory: write protected: Inventory_"+id+".txt");	
		    
		    // Attempt to delete it
		    result = f.delete();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteInventory: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
