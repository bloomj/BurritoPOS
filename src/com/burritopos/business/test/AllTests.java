package com.burritopos.business.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	BurritoManagerTestCase.class, 
	InventoryManagerTestCase.class, 
	OrderManagerTestCase.class
	})
    public class AllTests {
	
    @BeforeClass 
    public static void setUpClass() {    

    }
    
    @AfterClass 
    public static void tearDownClass() { 

    }

}
