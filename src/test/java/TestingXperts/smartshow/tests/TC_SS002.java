package TestingXperts.smartshow.tests;

import java.time.LocalDateTime;

import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import TestNGListeners.CustomListeners;
import TestingXperts.smartshow.pages.AgentPage;
import TestingXperts.smartshow.pages.PageMenu;
import TestingXperts.smartshow.pages.ShowingPage;
import Utilities.ConfigReader;
import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.KeywordUtil;
import Utilities.LogUtil;
import Utilities.ScreenshotUtil;

@Listeners(CustomListeners.class)
public class TC_SS002 extends KeywordUtil{
	

	String suiteName = ConfigReader.getValue("suiteName");
	String testCaseID = getClass().getSimpleName();
	boolean isRun;
	LocalDateTime startTime = LocalDateTime.now();
	LocalDateTime endTime;
	boolean check = true;
	boolean status = true;
	String expected = "NA", actual = "NA";
	String logStep;
	String browserName;
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

	@Test(groups={"All","Regression","Smoke"},description="Verify add address")
  public void TC_SS002_Test() throws Throwable {
		
		try{
			//======================BASIC SETTING FOR TEST==========================================================
			if(retryingNumber==1)
				initTest(testCaseID);
			
			//================== END BASIC SETTING ============================================================
			
			driver.navigate().refresh();
			PageMenu.clickShowing(driver);
			Thread.sleep(2000);
			
			//***************SCRIPT START****************
			GlobalUtil.currentUserEmail= PageMenu.getCurrentUser(driver);
			
			AgentPage.getCurrentUserInfo(driver);
			Thread.sleep(1000);
			
			LogUtil.infoLog(getClass(), "Getting Current user info");
			PageMenu.clickShowing(driver);
			Thread.sleep(1000);
			LogUtil.infoLog(getClass(), "User info captured");
			
			Thread.sleep(1000);
			
			logStep ="Verify Schduled List for Mine";
			
			check=ShowingPage.chekMineScheduledList(driver);
			Thread.sleep(2000);
			
			if (check) {
				LogUtil.infoLog(getClass(), logStep + "-PASS ");
				HtmlReportUtil.stepPass(logStep);

			} else {
				LogUtil.infoLog(getClass(), logStep + "-FAIL ");
				HtmlReportUtil.stepFail(logStep);

				status = false;
			}
			
			
			logStep ="Verify Completed List for Mine";
			
			check=ShowingPage.chekMineCompletedList(driver);
			Thread.sleep(2000);
			
			if (check) {
				LogUtil.infoLog(getClass(), logStep + "-PASS ");
				HtmlReportUtil.stepPass(logStep);

			} else {
				LogUtil.infoLog(getClass(), logStep + "-FAIL ");
				HtmlReportUtil.stepFail(logStep);

				status = false;
			}
			
			
			
			LogUtil.infoLog(getClass(), "Checking for TEAM only allowed to admin");
			HtmlReportUtil.stepInfo("Checking for TEAM only allowed to admin");
			
			if(GlobalUtil.currentUserType.equalsIgnoreCase("Admin"))
			{
				
				logStep ="Verify Schduled List for Team";
				
				check=ShowingPage.chekTeamScheduledList(driver);
				Thread.sleep(2000);
				
				if (check) {
					LogUtil.infoLog(getClass(), logStep + "-PASS ");
					HtmlReportUtil.stepPass(logStep);

				} else {
					LogUtil.infoLog(getClass(), logStep + "-FAIL ");
					HtmlReportUtil.stepFail(logStep);

					status = false;
				}
				
				
				logStep ="Verify Completed List for Team";
				
				check=ShowingPage.chekTeamCompletedList(driver);
				Thread.sleep(2000);
				
				if (check) {
					LogUtil.infoLog(getClass(), logStep + "-PASS ");
					HtmlReportUtil.stepPass(logStep);

				} else {
					LogUtil.infoLog(getClass(), logStep + "-FAIL ");
					HtmlReportUtil.stepFail(logStep);

					status = false;
				}
				
				logStep ="Verify Schduled List for All";
				
				check=ShowingPage.chekAllScheduledList(driver);
				Thread.sleep(2000);
				
				if (check) {
					LogUtil.infoLog(getClass(), logStep + "-PASS ");
					HtmlReportUtil.stepPass(logStep);

				} else {
					LogUtil.infoLog(getClass(), logStep + "-FAIL ");
					HtmlReportUtil.stepFail(logStep);

					status = false;
				}
				
				logStep ="Verify Completed List for All";
				
				check=ShowingPage.chekAllCompletedList(driver);
				Thread.sleep(2000);
				
				if (check) {
					LogUtil.infoLog(getClass(), logStep + "-PASS ");
					HtmlReportUtil.stepPass(logStep);

				} else {
					LogUtil.infoLog(getClass(), logStep + "-FAIL ");
					HtmlReportUtil.stepFail(logStep);

					status = false;
				}
				
				
			}else
			{
				LogUtil.infoLog(getClass(), "User is not admin");
				HtmlReportUtil.stepInfo("User is not admin");
			}
			
			
			

						
			
			//***************SCRIPT END****************
						
			
			
			//***************TEST RESULTS****************
			if(status){
				
			}else
			{
				//TEST IS FAILED THROW EXCEPTION
				throw new Exception("Test step has failed...");
				
			}
			
			
		}catch(SkipException skip){
			GlobalUtil.testException=skip;
			throw skip;
		}//END CATCH 1
		
		catch (Exception e){
			   if(retryCount>0)
			   {
			    String imagePath = takeScreenshot(driver, testCaseID+"_"+ retryingNumber);
			    GlobalUtil.testResult.setScreenshot_ref(imagePath);
			   // GlobalUtil.testException=e;
			    HtmlReportUtil.attachScreenshot(imagePath,false);
			       HtmlReportUtil.stepInfo("Trying to Rerun" + " "+testCaseID +" for " + retryingNumber + " time");
			    retryCount--;
			    retryingNumber++;
			    Utilities.LogUtil.infoLog(getClass(), "****************Waiting for " + getIntValue("retryDelayTime") +" Secs before retrying.***********");
			    delay(getIntValue("retryDelayTime"));
			    //Rerun same test
			    TC_SS002_Test();
			   }
			   
			   else{
			    Utilities.LogUtil.infoLog(getClass(),  KeywordUtil.logging_step+ "-FAIL ");
			    HtmlReportUtil.stepInfo(KeywordUtil.logging_step + "-FAIL");
			    String imagePath = takeScreenshot(driver, testCaseID);
			    GlobalUtil.testResult.setScreenshot_ref(imagePath);
			    HtmlReportUtil.stepError(testCaseID, e);
			    HtmlReportUtil.attachScreenshot(imagePath,false);
			    GlobalUtil.testException=e;
			    throw e;
			   }
		  }//END CATCH 2
		 
		

	}//END TEST METHOD 
}//END TEST CLASS
