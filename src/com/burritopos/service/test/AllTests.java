package com.burritopos.service.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	AuthenticationSvcImplTestCase.class, 
	BCryptTestCase.class, 
	BurritoSvcImplTestCase.class, 
	CustomerSvcImplTestCase.class, 
	EmployeeSvcImplTestCase.class, 
	FactoryTestCase.class, 
	InventorySvcImplTestCase.class, 
	ManagerSvcImplTestCase.class, 
	OrderSvcImplTestCase.class
	})
    public class AllTests {
	
    @BeforeClass 
    public static void setUpClass() {    

    }
    
    @AfterClass 
    public static void tearDownClass() { 

    }

}