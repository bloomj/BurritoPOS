/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class EmployeeSvcFileImpl implements IEmployeeSvc {

        private static Logger dLog = Logger.getLogger(EmployeeSvcFileImpl.class);

	@Override
	public Employee getEmployee(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getEmployee | Employee ID: "+id);
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
			dLog.error(new Date() + " | IOException in getEmployee: "+e1.getMessage());
		}
		catch (ClassNotFoundException e2) {
			dLog.error(new Date() + " | ClassNotFoundException in getEmployee: "+e2.getMessage());
		}
		catch(Exception e3) {
			dLog.error(new Date() + " | Exception in getEmployee: "+e3.getMessage());
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
		dLog.info(new Date() + " | Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
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
			dLog.error(new Date() + " | IOException in storeEmployee: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeEmployee: "+e2.getMessage());
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
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteEmployee | Employee ID:"+id);
		boolean result = false;
		
		try {
		    File f = new File("Employee_"+id+".txt");

		    // Ensure the file exists
		    if (!f.exists())
		      throw new IllegalArgumentException("deleteEmployee: no such file or directory: Employee_"+id+".txt");

		    // Ensure the file is not locked
		    if (!f.canWrite())
		      throw new IllegalArgumentException("deleteEmployee: write protected: Employee_"+id+".txt");	
		    
		    // Attempt to delete it
		    result = f.delete();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteEmployee: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
