package com.burritopos.service.net;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.domain.Manager;

/**
 *
 * @author james.bloom
 */
public class SecureAuthenticationSvcSocketImpl implements IAuthenticationSvc {
    private static Logger dLog = Logger.getLogger(SecureAuthenticationSvcSocketImpl.class);
    private SSLSocketFactory factory = null;
    private SSLSocket sslsocket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    
    private static String BURRITOPOS_SERVER_IP;
    private static int BURRITOPOS_SERVER_PORT;
    private static String KEYSTORE_PATH;
    private static String KEYSTORE_PASSWORD;
    
    public SecureAuthenticationSvcSocketImpl() throws IOException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
    	// manually load properties
        Properties propList = new Properties();
        dLog.trace("Loading burritopos.properties");

        propList.load(this.getClass().getClassLoader().getResourceAsStream("burritopos.properties"));
        BURRITOPOS_SERVER_IP = propList.getProperty("burritopos.server.ip");     
        dLog.trace("Got BURRITOPOS_SERVER_IP value: " + BURRITOPOS_SERVER_IP);
        BURRITOPOS_SERVER_PORT = Integer.parseInt(propList.getProperty("burritopos.server.port"));     
        dLog.trace("Got BURRITOPOS_SERVER_PORT value: " + BURRITOPOS_SERVER_PORT);
        KEYSTORE_PATH = propList.getProperty("ssl.keystore.path");     
        dLog.trace("Got KEYSTORE_PATH value: " + KEYSTORE_PATH);
        KEYSTORE_PASSWORD = propList.getProperty("ssl.keystore.password");     
        dLog.trace("Got KEYSTORE_PASSWORD value: " + KEYSTORE_PASSWORD);
        
        initSSL();
    }
    
    public SecureAuthenticationSvcSocketImpl(String ip, String port, String keystorePath, String keystorePassword) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException, IOException {
    	// allow Spring to inject properties
    	BURRITOPOS_SERVER_IP = ip;
    	BURRITOPOS_SERVER_PORT = Integer.parseInt(port);
    	KEYSTORE_PATH = keystorePath;
    	KEYSTORE_PASSWORD = keystorePassword;
    	
    	initSSL();
    }
    
    private void initSSL() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
    	KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    	dLog.trace("Loading keystore");
        keyStore.load(new FileInputStream(KEYSTORE_PATH), KEYSTORE_PASSWORD.toCharArray());

        dLog.trace("Initializing KeyManagerFactory");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        dLog.trace("Initializing SSL Context");
        System.setProperty("https.protocols", "TLSv1");
        SSLContext ctx = SSLContext.getInstance("TLS");
        // Note: only care about encryption at this time (Confidentiality), will add trust later (Integrity)
        ctx.init(kmf.getKeyManagers(), trustAllCerts, new java.security.SecureRandom());
        dLog.trace("Retreiving SSL socket factory");
        factory = ctx.getSocketFactory();
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
            sslsocket = (SSLSocket) factory.createSocket(BURRITOPOS_SERVER_IP,BURRITOPOS_SERVER_PORT);
            in = new ObjectInputStream(sslsocket.getInputStream());
            out = new ObjectOutputStream(sslsocket.getOutputStream());

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
        		if(sslsocket != null) {
        			sslsocket.close();
        		}
        	} catch (Exception e2) {
        		dLog.error("Exception closing socket", e2);
        		throw(e2);
        	}
        }

        dLog.trace("Returning result: " + result);
        return result;
    }
    
    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[] { 
        new X509TrustManager() {     
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
                return null;
            } 
            public void checkClientTrusted( 
                java.security.cert.X509Certificate[] certs, String authType) {
                } 
            public void checkServerTrusted( 
                java.security.cert.X509Certificate[] certs, String authType) {
            }
        } 
    }; 
}
