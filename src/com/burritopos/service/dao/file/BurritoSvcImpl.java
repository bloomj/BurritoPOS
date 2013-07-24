package com.burritopos.service.dao.file;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.service.dao.IBurritoSvc;


/**
 * @author james.bloom
 *
 */
public class BurritoSvcImpl implements IBurritoSvc {
	private static Logger dLog = Logger.getLogger(BurritoSvcImpl.class);

	@Override
	public Burrito getBurrito(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getBurrito | ID: " + id);
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
			dLog.error("IOException in getBurrito: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e2) {
			dLog.error("ClassNotFoundException in getBurrito: "+e2.getMessage());
			e2.printStackTrace();
		}
		catch(Exception e3) {
			dLog.error("Exception in getBurrito: "+e3.getMessage());
			e3.printStackTrace();
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
		dLog.info("Entering method storeBurrito | Burrito ID: "+b.getId());
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
			dLog.error("IOException in storeBurrito: "+e1.getMessage());
			e1.printStackTrace();
			result = false;
		}
		catch(Exception e2) {
			dLog.error("Exception in storeBurrito: "+e2.getMessage());
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
	public boolean deleteBurrito(Integer id) throws Exception {
		dLog.info("Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;

		File f = new File("Burrito_"+id+".txt");

		// Ensure the file exists
		if (!f.exists()) {
			throw new IllegalArgumentException("deleteBurrito: no such file or directory: Burrito_"+id+".txt");
		}

		// Ensure the file is not locked
		if (!f.canWrite()) {
			throw new IllegalArgumentException("deleteBurrito: write protected: Burrito_"+id+".txt");	
		}

		// Attempt to delete it
		result = f.delete();

		return result;
	}
}
