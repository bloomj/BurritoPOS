package com.burritopos.service.net;

import java.io.*;
import java.net.*;
import java.util.Properties;

import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.domain.Manager;

/**
 *
 * @author james.bloom
 */
public class AuthenticationSvcSocketImpl implements IAuthenticationSvc {
    private static Logger dLog = Logger.getLogger(AuthenticationSvcSocketImpl.class);
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    
    private static String BURRITOPOS_SERVER_IP;
    private static int BURRITOPOS_SERVER_PORT;
    
    public AuthenticationSvcSocketImpl() throws IOException {
    	// manually load properties
        Properties propList = new Properties();
        dLog.trace("Loading burritopos.properties");

        propList.load(this.getClass().getClassLoader().getResourceAsStream("burritopos.properties"));
        BURRITOPOS_SERVER_IP = propList.getProperty("burritopos.server.ip");     
        dLog.trace("Got BURRITOPOS_SERVER_IP value: " + BURRITOPOS_SERVER_IP);
        BURRITOPOS_SERVER_PORT = Integer.parseInt(propList.getProperty("burritopos.server.port"));     
        dLog.trace("Got BURRITOPOS_SERVER_PORT value: " + BURRITOPOS_SERVER_PORT);
    }
    
    public AuthenticationSvcSocketImpl(String ip, String port) {
    	// allow Spring to inject properties
    	BURRITOPOS_SERVER_IP = ip;
    	BURRITOPOS_SERVER_PORT = Integer.parseInt(port);
    }
    
    /**
     * Logs in an Employee
     */
    public boolean login(Employee e, String password) throws Exception {
        dLog.trace("Login for Employee object");
        return(sendLoginRequest(e.getFirstName(), password));
    }

    /**
     * Logs in an Manager
     */
    public boolean login(Manager m, String password) throws Exception {
        dLog.trace("Login for Manager object");
        return(sendLoginRequest(m.getFirstName(), password));
    }

    private boolean sendLoginRequest(String userName, String password) throws Exception {
        boolean result = false;

        try {        	
            socket = new Socket(BURRITOPOS_SERVER_IP, BURRITOPOS_SERVER_PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String str = (String)in.readObject();
            dLog.trace("Got : " + str);
            out.writeObject(userName);
            str = (String)in.readObject();
            dLog.trace("Got : " + str);
            out.writeObject(password);
            str = (String)in.readObject();
            dLog.trace("Got : " + str);
            dLog.trace("good? " + str.split(" ")[0]);

            // check our input
            if(str.split(" ")[0].equals("OK")) {
                result = true;
            }
            
            out.writeObject("exit");
        }
        catch(Exception e1) {
        	dLog.error("Exception in sendLoginRequest", e1);
        }
        finally {
        	try {
        		dLog.trace("Trying to close input stream");
        		if(in != null) {
        			in.close();
        		}
        		dLog.trace("Trying to close output stream");
        		if(out != null) {
        			out.close();
        		}
        		dLog.trace("Trying to close socket");
        		if(socket != null) {
        			socket.close();
        		}
        	} catch (Exception e2) {
        		dLog.error("Exception closing socket", e2);
        		throw(e2);
        	}
        }

        dLog.trace("Returning result: " + result);
        return result;
    }
}
