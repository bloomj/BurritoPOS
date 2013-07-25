/**
 * 
 */
package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.service.dao.IEmployeeSvc;


/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImpl implements IEmployeeSvc {

        private static Logger dLog = Logger.getLogger(EmployeeSvcImpl.class);

	@Override
	public Employee getEmployee(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getEmployee | Employee ID: "+id);
		Employee e = null;
		ObjectInputStream input = null;
		
		try {
			File file = new File("Employee_"+id+".txt");
			boolean exists = file.exists();
			
			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Employee_"+id+".txt"));
				e = (Employee)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in getEmployee", e1);
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getEmployee", e2);
		}
		catch(Exception e3) {
			dLog.error("Exception in getEmployee", e3);
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}
		
		return e;
	}

	@Override
	public boolean storeEmployee(Employee e) throws IOException, Exception {
		dLog.info("Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
		ObjectOutputStream output = null;
		boolean result = false;
		
		try {
			//ensure we were passed a valid object before attempting to write
			if(e.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Employee_"+e.getEmployeeID()+".txt"));
				output.writeObject(e);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in storeEmployee", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeEmployee", e2);
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
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info("Entering method deleteEmployee | Employee ID:"+id);
		boolean result = false;
		
		File f = new File("Employee_"+id+".txt");

		// Ensure the file exists
		if (!f.exists()) {
			throw new IllegalArgumentException("deleteEmployee: no such file or directory: Employee_"+id+".txt");
		}

		// Ensure the file is not locked
		if (!f.canWrite()) {
			throw new IllegalArgumentException("deleteEmployee: write protected: Employee_"+id+".txt");	
		}

		// Attempt to delete it
		result = f.delete();
		
		return result;
	}
}
