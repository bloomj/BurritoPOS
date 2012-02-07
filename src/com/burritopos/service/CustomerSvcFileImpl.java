/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Customer;

import java.util.Date;


/**
 * @author james.bloom
 *
 */
public class CustomerSvcFileImpl implements ICustomerSvc {

	private static Logger dLog = Logger.getLogger(CustomerSvcFileImpl.class);

	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getCustomer | Customer ID: "+id);
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
			dLog.error(new Date() + " | IOException in getCustomer: "+e1.getMessage());
		}
		catch (ClassNotFoundException e2) {
			dLog.error(new Date() + " | ClassNotFoundException in getCustomer: "+e2.getMessage());
		}
		catch(Exception e3) {
			dLog.error(new Date() + " | Exception in getCustomer: "+e3.getMessage());
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
		dLog.info(new Date() + " | Entering method storeCustomer | Customer ID: "+c.getId());
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
			dLog.error(new Date() + " | IOException in storeCustomer: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeCustomer: "+e2.getMessage());
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
		dLog.info(new Date() + " | Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;

		try {
			File f = new File("Customer_"+id+".txt");

			// Ensure the file exists
			if (!f.exists())
				throw new IllegalArgumentException("deleteCustomer: no such file or directory: Customer_"+id+".txt");

			// Ensure the file is not locked
			if (!f.canWrite())
				throw new IllegalArgumentException("deleteCustomer: write protected: Customer_"+id+".txt");	

			// Attempt to delete it
			result = f.delete();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteCustomer: "+e.getMessage());
			result = false;
		}

		return result;
	}

}
