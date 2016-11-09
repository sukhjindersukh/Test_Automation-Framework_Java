package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonFunctions {

public static boolean checkVisible(WebDriver driver , By by){
		
		return	driver.findElement(by).isDisplayed() && driver.findElement(by).isEnabled();  
			
		}

		public static void logResult(boolean status, String logStep ){
			if (status) {
				LogUtil.infoLog(Utility.class, logStep + "-PASS ");
				HtmlReportUtil.stepPass(logStep);
			} else {
				LogUtil.infoLog(Utility.class, logStep + "-FAIL ");
				HtmlReportUtil.stepFail(logStep);
			}
			}

		public static void logStep(String logStep ){
				LogUtil.infoLog(Utility.class, logStep );
				HtmlReportUtil.stepInfo(logStep);
			}
		
	

}
