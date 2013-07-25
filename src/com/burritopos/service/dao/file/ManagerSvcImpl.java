/**
 * 
 */
package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Manager;
import com.burritopos.service.dao.IManagerSvc;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImpl implements IManagerSvc {

	private static Logger dLog = Logger.getLogger(ManagerSvcImpl.class);

	@Override
	public Manager getManager(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getManager | Manager ID: "+id);
		Manager m = null;
		ObjectInputStream input = null;

		try {
			File file = new File("Manager_"+id+".txt");
			boolean exists = file.exists();

			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Manager_"+id+".txt"));
				m = (Manager)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in getManager", e1);
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getManager", e2);
		}
		catch(Exception e3) {
			dLog.error("Exception in getManager", e3);
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}

		return m;
	}

	@Override
	public boolean storeManager(Manager m) throws IOException, Exception {
		dLog.info("Entering method storeManager | Manager ID: "+m.getEmployeeID());
		ObjectOutputStream output = null;
		boolean result = false;

		try {
			//ensure we were passed a valid object before attempting to write
			if(m.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Manager_"+m.getEmployeeID()+".txt"));
				output.writeObject(m);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in storeManager", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeManager", e2);
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
	public boolean deleteManager(Integer id) throws Exception {
		dLog.info("Entering method deleteManger | Manager ID:"+id);
		boolean result = false;

		File f = new File("Manager_"+id+".txt");

		// Ensure the file exists
		if (!f.exists()) {
			throw new IllegalArgumentException("deleteManger: no such file or directory: Manager_"+id+".txt");
		}

		// Ensure the file is not locked
		if (!f.canWrite()) {
			throw new IllegalArgumentException("deleteManger: write protected: Manager_"+id+".txt");	
		}

		// Attempt to delete it
		result = f.delete();

		return result;
	}
}
