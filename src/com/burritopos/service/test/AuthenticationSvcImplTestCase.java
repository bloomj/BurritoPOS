/**
 * 
 */
package com.burritopos.service.test;

//import java.util.UUID;
import org.apache.log4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.burritopos.service.net.IAuthenticationSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AuthenticationSvcImplTestCase extends BurritoPOSTestCase {
	@Autowired
	private IAuthenticationSvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(AuthenticationSvcImplTestCase.class);

	public AuthenticationSvcImplTestCase() {
		super();
	}

	/**
	 * Unit Tests for Authentication service
	 * @throws Exception 
	 */
    @Test
	public void testAuthenticationSvc() throws Exception {
    	// TODO: write the test case for this class
	}

}
