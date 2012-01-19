/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;

import java.util.Date;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcFileImpl implements IBurritoSvc {

        private static Logger dLog = Logger.getLogger(BurritoSvcFileImpl.class);

	@Override
	public Burrito getBurrito(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getBurrito | ID: " + id);
		Burrito b = null;
		ObjectInputStream input = null;
		
		try {
			File file = new File("Burrito_"+id+".txt");
			boolean exists = file.exists();
			
			//ensure we were passed a valid object before attempting to write
			if(exists) {
				input = new ObjectInputStream (new FileInputStream("Burrito_"+id+".txt"));
				b = (Burrito)input.readObject();
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in getBurrito: "+e1.getMessage());
		}
		catch (ClassNotFoundException e2) {
			dLog.error(new Date() + " | ClassNotFoundException in getBurrito: "+e2.getMessage());
		}
		catch(Exception e3) {
			dLog.error(new Date() + " | Exception in getBurrito: "+e3.getMessage());
		}
		finally {
			//ensure that input is close regardless of the errors in try/catch
			if(input != null) {
				input.close();
			}
		}
		
		return b;
	}

	@Override
	public boolean storeBurrito(Burrito b) throws IOException, Exception {
		dLog.info(new Date() + " | Entering method storeBurrito | Burrito ID: "+b.getId());
		ObjectOutputStream output = null;
		boolean result = false;
		
		try {
			//ensure we were passed a valid object before attempting to write
			if(b.validate()) {
				output = new ObjectOutputStream (new FileOutputStream("Burrito_"+b.getId()+".txt"));
				output.writeObject(b);
				result = true;
			}
		} 
		catch (IOException e1) {
			dLog.error(new Date() + " | IOException in storeBurrito: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeBurrito: "+e2.getMessage());
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
	public boolean deleteBurrito(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
		
		try {
		    File f = new File("Burrito_"+id+".txt");

		    // Ensure the file exists
		    if (!f.exists())
		      throw new IllegalArgumentException("deleteBurrito: no such file or directory: Burrito_"+id+".txt");

		    // Ensure the file is not locked
		    if (!f.canWrite())
		      throw new IllegalArgumentException("deleteBurrito: write protected: Burrito_"+id+".txt");	
		    
		    // Attempt to delete it
		    result = f.delete();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteBurrito: "+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
