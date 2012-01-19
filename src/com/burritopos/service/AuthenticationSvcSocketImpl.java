/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.burritopos.service;

import java.io.*;
import java.net.*;
import java.util.Date;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.domain.Manager;

/**
 *
 * @author james.bloom
 */
public class AuthenticationSvcSocketImpl implements IAuthenticationSvc {

    private static Logger dLog = Logger.getLogger(AuthenticationSvcSocketImpl.class);
    Socket socket = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

    public boolean login(Employee e, String password) throws Exception {
        dLog.trace(new Date() + " | Login cancel button has been clicked");
        boolean result = false;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8000);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            out.writeObject(e.getFirstName());
            str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            out.writeObject(password);
            str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            dLog.trace(new Date() + " | good? " + str.split(" ")[0]);

            // check our input
            if(str.split(" ")[0].equals("OK"))
                result = true;
            
            out.writeObject("exit");
        }
        catch(Exception e1) {
            dLog.trace(new Date() + " | Exception in login: " + e1.getMessage());
            result = false;
        }
        finally {
             try {
                in.close();
                out.close();
                socket.close();
             } catch (Exception e2) {
                System.out.println(new Date() + " | Exception " + e2.getMessage());
             }
        }

        return result;
    }

    public boolean login(Manager m, String password) throws Exception {
        dLog.trace(new Date() + " | Login cancel button has been clicked");
        boolean result = false;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8000);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            out.writeObject(m.getFirstName());
            str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            out.writeObject(password);
            str = (String)in.readObject();
            dLog.trace(new Date() + " | Got : " + str);
            dLog.trace(new Date() + " | good? " + str.split(" ")[0]);

            // check our input
            if(str.split(" ")[0].equals("OK"))
                result = true;

            out.writeObject("exit");
        }
        catch(Exception e1) {
            dLog.trace(new Date() + " | Exception in login: " + e1.getMessage());
            result = false;
        }
        finally {
             try {
                in.close();
                out.close();
                socket.close();
             } catch (Exception e2) {
                System.out.println(new Date() + " | Exception " + e2.getMessage());
             }
        }

        return result;
    }

}
