package TestingXperts.smartshow.tests;

import java.time.LocalDateTime;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import TestNGListeners.CustomListeners;
import TestNGListeners.RetryOnFailure;
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
public class TC_SS003 extends KeywordUtil{
	String testCaseID = getClass().getSimpleName();
	String stepInfo;
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

  @Test(groups={"All","Regression","Smoke"},description="Verify add address", retryAnalyzer=RetryOnFailure.class)
  public void TC_SS003_Test() throws Throwable {
		
		try{
			//======================BASIC SETTING FOR TEST==========================================================
			if(retryingNumber==1)
				initTest(testCaseID);
			driver.navigate().refresh();
			//================== END BASIC SETTING ============================================================
			
			//Precondition- User should be on showing page
			executeStep(click(By.partialLinkText("Showings")), getClass(), "Click on showing");
			//================== END BASIC SETTING ============================================================
			
			//***************SCRIPT START****************
			stepInfo ="Verify delete scheduled";
			checkStep(ShowingPage.checkDeletedScheduled(driver), getClass(), stepInfo);
			
			stepInfo ="Verify delete Completed";
			checkStep(ShowingPage.checkDeletedCompleted(driver), getClass(), stepInfo);
			
			//***************SCRIPT END****************
			
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
			    TC_SS003_Test();
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
