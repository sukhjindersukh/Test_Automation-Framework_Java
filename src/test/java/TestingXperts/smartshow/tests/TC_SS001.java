package TestingXperts.smartshow.tests;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import TestNGListeners.CustomListeners;
import TestNGListeners.RetryOnFailure;
import TestingXperts.smartshow.pages.ShowingPage;
import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.KeywordUtil;
import Utilities.ScreenshotUtil;

@Listeners(CustomListeners.class)
public class TC_SS001 extends KeywordUtil{
	String testCaseID = getClass().getSimpleName();
	String stepInfo;
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

	@Test(groups={"All","Regression","Login"}, description="Verify display text and ui controls and Logo with Appropirate heading")
	public void TC_SS001_Test() throws Throwable {
		try{
			//======================BASIC SETTING FOR TEST==========================================================
			if(retryingNumber==1)
				initTest(testCaseID);
			//================== END BASIC SETTING ============================================================
			driver.navigate().refresh();
			
			stepInfo="Check SmartShow Logo";
			executeStep(clickLink("Showings"), getClass(), "Click showing link");
			checkStep(isWebElementVisible(By.xpath("html/body/section/div[2]/div/div[1]/div[2]/img")), getClass(), stepInfo);
			
			stepInfo = "Check Heading of Showing page";
			if(retryingNumber==1)
			checkStep(verifyText(By.cssSelector(".page-heading"), "Showing"), getClass(), stepInfo);
			else
				checkStep(verifyText(By.cssSelector(".page-heading"), "Showings"), getClass(), stepInfo);
				
			stepInfo = "Check Mine UI control";
			checkStep(verifyDisplayAndEnable(By.cssSelector("button.mine.btn")), getClass(), stepInfo);
						
			stepInfo = "Check Deleted UI control";
			checkStep(verifyDisplayAndEnable(By.cssSelector("button[class='trash btn btn-default']")), getClass(), stepInfo);
			
			stepInfo = "Check Schedule a new showing + button";
			checkStep(verifyDisplayAndEnable(By.cssSelector("button.btn.btn-default.add")), getClass(), stepInfo);
			
			stepInfo = "Check Edit this Showing UI control";
			checkStep(ShowingPage.checkEditThisShowingUI(driver), getClass(), stepInfo);
			
			stepInfo = "Check Delete this showing UI control";
			checkStep(ShowingPage.checkScheduled_DeleteThisShowingUI(driver), getClass(), stepInfo);

		}catch(SkipException skip){
			GlobalUtil.testException=skip;
			throw skip;
		}
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
			    TC_SS001_Test();
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
		  }
}
}
