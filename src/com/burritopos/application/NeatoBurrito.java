/*
 * 
 * Copyright (c) 2011-2013, James Bloom
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer 
 *   in the documentation and/or other materials provided with the distribution.
 *  
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.burritopos.application;

import org.apache.log4j.*;

import com.burritopos.exception.ServiceLoadException;
import com.burritopos.presentation.LoginUI;
import com.burritopos.presentation.MainUI;
import com.burritopos.presentation.OrderUI;

import java.util.Date;

/**
 * 
 */

/**
 * @author james.bloom
 *
 */
@SuppressWarnings("unused")
public class NeatoBurrito {
    private static Logger dLog = Logger.getLogger(NeatoBurrito.class);

	public static void main(String [ ] args)
	{
		try {
            String propertiesFile = "log4j.properties";
            PropertyConfigurator.configure(propertiesFile);

            dLog.info("Starting Neato Burrito POS execution");
			
			//main entry point into our POS system here
			// week 6
			//OrderUI oUI = new OrderUI("Create Order");
			//oUI.setBounds(0, 0, 500, 500);
			//oUI.setVisible(true);
			
			// 
			/*MainUI mainUI = new MainUI();
			mainUI.setBounds(0, 0, 750, 750);
			mainUI.setVisible(true);*/

            // This is the entry point as part of client/server app
            LoginUI logUI = new LoginUI();
			logUI.setBounds(0, 0, 400, 250);
			logUI.setVisible(true);
			
			dLog.info("Finishing Neato Burrito POS execution");
		}
		/*catch(ServiceLoadException e1) {
			dLog.error("Exception in NeatoBurrito", e1);
		}*/
		catch(Exception e2) {
			dLog.error("Exception in NeatoBurrito", e2);
		}
	}
}
