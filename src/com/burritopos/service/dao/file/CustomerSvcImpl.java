/**
 * 
 */
package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Customer;
import com.burritopos.service.dao.ICustomerSvc;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImpl implements ICustomerSvc {

	private static Logger dLog = Logger.getLogger(CustomerSvcImpl.class);

	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info("Entering method getCustomer | Customer ID: "+id);
		Customer c = new Customer();
		ObjectInputStream input = null;

		try {
			File file = new File("Customer_"+id+".txt");
			boolean exists = file.exists();

			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Customer_"+id+".txt"));
				c = (Customer)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in getCustomer: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getCustomer: "+e2.getMessage());
			e2.printStackTrace();
		}
		catch(Exception e3) {
			dLog.error("Exception in getCustomer: "+e3.getMessage());
			e3.printStackTrace();
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}

		return c;
	}

	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info("Entering method storeCustomer | Customer ID: "+c.getId());
		ObjectOutputStream output = null;
		boolean result = false;

		try {
			//ensure we were passed a valid object before attempting to write
			if(c.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Customer_"+c.getId()+".txt"));
				output.writeObject(c);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error("IOException in storeCustomer: "+e1.getMessage());
			e1.printStackTrace();
			result = false;
		}
		catch(Exception e2) {
			dLog.error("Exception in storeCustomer: "+e2.getMessage());
			e2.printStackTrace();
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
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info("Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;

		File f = new File("Customer_"+id+".txt");

		// Ensure the file exists
		if (!f.exists()) {
			throw new IllegalArgumentException("deleteCustomer: no such file or directory: Customer_"+id+".txt");
		}

		// Ensure the file is not locked
		if (!f.canWrite()) {
			throw new IllegalArgumentException("deleteCustomer: write protected: Customer_"+id+".txt");	
		}

		// Attempt to delete it
		result = f.delete();

		return result;
	}

}
