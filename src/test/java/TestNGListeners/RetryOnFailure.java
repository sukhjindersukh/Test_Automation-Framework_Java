package TestNGListeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryOnFailure implements IRetryAnalyzer{
	//***************** NOTE- Must add line in your test annotation *********************************
	
	//	@Test(retryAnalyzer=Retry.class)
	
	//**************************************************
	
	// set counter to 0
	public static int minretryCount=0;
   
    //Change to 1 if you want to run each test case two time 
   public static int maxretryCount=0;
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		 // this will run until max count completes if test pass within this frame it will come out of for loop
		 
        if(minretryCount<maxretryCount)

        {
             // print the test name for log purpose   
               System.out.println("Following test is failing===="+result.getName());

             // print the counter value    
               System.out.println("Retrying the test Count is=== "+ (minretryCount+1));

             // increment counter each time by 1  
               minretryCount++;

               return true;
        }
		return false;
	}
	

}
