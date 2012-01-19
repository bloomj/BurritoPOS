package com.burritopos.business;

import java.security.Key;
import java.security.Security;
import java.util.Date;

import javax.crypto.KeyGenerator;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.*;

// A simple example that uses the Bouncy Castle
// lightweight cryptography API to perform AES
// encryption of arbitrary data.

public class Encryptor {
    
	private static Logger dLog = Logger.getLogger(Encryptor.class);
    private PaddedBufferedBlockCipher cipher;
    private KeyParameter key;
    
    // Initialize the cryptographic engine.
    public Encryptor( byte[] key ){    
        cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        
        this.key = new KeyParameter( key );
    }
    
    // Initialize the cryptographic engine.
    // The string should be at least 8 chars long.
    public Encryptor( String key ){
        this(key.getBytes());
    }
    
    // Private routine that does the gritty work.
    private byte[] callCipher( byte[] data ) throws CryptoException {
        int size = cipher.getOutputSize( data.length );
        byte[] result = new byte[ size ];
        int olen = cipher.processBytes( data, 0, data.length, result, 0 );
        olen += cipher.doFinal( result, olen );
        
        if( olen < size ){
            byte[] tmp = new byte[ olen ];
            System.arraycopy(result, 0, tmp, 0, olen );
            result = tmp;
        }
        
        return result;
    }
    
    // Encrypt arbitrary byte array, returning the
    // encrypted data in a different byte array.
    public synchronized byte[] encrypt( byte[] data ) throws CryptoException {
        if( data == null || data.length == 0 ){
            return new byte[0];
        }
        
        cipher.init( true, key );
        return callCipher( data );
    }
    
    // Encrypts a string.
    public byte[] encryptString( String data ) throws CryptoException {
        if( data == null || data.length() == 0 ){
            return new byte[0];
        }
        
        return encrypt( data.getBytes() );
    }
    
    // Decrypts arbitrary data. 
    public synchronized byte[] decrypt( byte[] data ) throws CryptoException {
        if( data == null || data.length == 0 ){
            return new byte[0];
        }
        
        cipher.init( false, key );
        return callCipher( data );
    }
    
    // Decrypts a string that was previously encoded
    // using encryptString. 
    public String decryptString( byte[] data ) throws CryptoException {
        if( data == null || data.length == 0 ){
            return "";
        }
        
        return new String( decrypt( data ) );
    }
    
    // Generate random AES Key
    public static String generateRandomKey(int size) {
    	String retVal = "";
    	
    	try {
    		if(size != 128 && size != 192 && size != 256)
    			size = 256;
    		
    		// If exception encountered: "JCE cannot authenticate the provider BC"
    		// need to edit java.security file with this line (where n is next sequential number):
    		// security.provider.n=org.bouncycastle.jce.provider.BouncyCastleProvider
    		if(Security.getProvider("BC") == null)
    			dLog.trace(new Date() + " | BouncyCastleProvider not installed");
    		else
    			dLog.trace(new Date() + " | BouncyCastleProvider installed");
	    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	    	// removed provider param "BC" for now
	        KeyGenerator generator = KeyGenerator.getInstance("AES");
	        generator.init(size);
	        Key keyToBeWrapped = generator.generateKey();
	        retVal = new String(keyToBeWrapped.getEncoded());
    	}
    	catch(Exception e) {
    		dLog.error(new Date() + " | Exception in generateRandomKey: "+e.getMessage());
    	}
        return retVal;
    }
    
    // default random key size to 256 if not provided
    public static String generateRandomKey() {
    	return generateRandomKey(256);
    }
}
