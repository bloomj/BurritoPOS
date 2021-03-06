/**
 * 
 */
package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Inventory;
import com.burritopos.service.dao.IInventorySvc;

/**
 * @author james.bloom
 *
 */
public class InventorySvcImpl implements IInventorySvc {
	private static Logger dLog = Logger.getLogger(InventorySvcImpl.class);

	@Override
	public Inventory getInventory(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getInventory | Inventory ID: "+id);
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
			dLog.error("IOException in getInventory", e1);
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getInventory", e2);
		}
		catch(Exception e3) {
			dLog.error("Exception in getInventory", e3);
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
		dLog.info("Entering method storeInventory | Inventory ID: "+i.getId());
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
			dLog.error("IOException in storeInventory", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeInventory", e2);
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
		dLog.info("Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		
		    File f = new File("Inventory_"+id+".txt");

		    // Ensure the file exists
		    if (!f.exists()) {
		    	throw new IllegalArgumentException("deleteInventory: no such file or directory: Inventory_"+id+".txt");
		    }

		    // Ensure the file is not locked
		    if (!f.canWrite()) {
		    	throw new IllegalArgumentException("deleteInventory: write protected: Inventory_"+id+".txt");	
		    }
		    
		    // Attempt to delete it
		    result = f.delete();
		
		return result;
	}
}
