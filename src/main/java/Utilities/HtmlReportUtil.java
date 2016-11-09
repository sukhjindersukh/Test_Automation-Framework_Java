/**
 * @author SUKHJINDER
 *
 * 	About 
 * =====================
 * This class is responsible for creating HTML report for test run. It will create two different file.
 * One of them is overwritten every time when user run the tests.
 * Second keep the history.  
 */

/* We can use the following HTML code to modify the logs.O
 * <span class='success label'>Success</span> 
<span class='fail label'>Fail</span> 
<span class='warning label'>Warning</span> 
<span class='info label'>Info</span> 
<span class='skip label'>Skip</span>
 * */

package Utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class HtmlReportUtil {
	private static ExtentReports extentNoHistory = null;
	private static ExtentReports extentPreserverHistory = null;
	private static ExtentTest test = null;
	private static ExtentTest testHist = null;
	

	// Initialize reporter
	private synchronized static void init() {
		if (extentNoHistory == null) {
			extentNoHistory = new ExtentReports(ConfigReader.getValue("HtmlReport") + "\\TestReport.html", true,
					DisplayOrder.NEWEST_FIRST);
			// extentNoHistory = new
			// ExtentReports(System.getProperty("user.dir")+"\\ExecutionReports\\HtmlReport\\TestReport.html",true,DisplayOrder.NEWEST_FIRST);
			extentNoHistory.loadConfig(new File(ConfigReader.getValue("HtmlReportConfigFile")));
		}
		if (extentPreserverHistory == null) {
			extentPreserverHistory = new ExtentReports(ConfigReader.getValue("HtmlReport") + "\\TestReportHistory.html",
					false, DisplayOrder.NEWEST_FIRST);
			extentPreserverHistory.loadConfig(new File(ConfigReader.getValue("HtmlReportConfigFile")));
		}
	}// End init()

	// This function is used to start report when a new test is started.
	public synchronized static void startReport(String testName, String testInfo, String category) {
		init();
		test = extentNoHistory.startTest(testName, testInfo);
		testHist = extentPreserverHistory.startTest(testName, testInfo);
		test.assignCategory(category);
		testHist.assignCategory(category);
	}

	// This function is used to end report when a current test finished its
	// execution.
	public synchronized static void endReport(boolean status, String stepName) {
		if (status) {
			test.log(LogStatus.PASS, stepName);
			testHist.log(LogStatus.PASS, stepName);
		} else {
			test.log(LogStatus.FAIL, stepName);
			testHist.log(LogStatus.FAIL, stepName);
		}
		// End the current test
		extentNoHistory.endTest(test);
		extentPreserverHistory.endTest(testHist);

		// Write the information of the current test in HTML file.
		extentNoHistory.flush();
		extentPreserverHistory.flush();
	}// End endReport()

	public synchronized static void stepPass(String stepName) {
		String html = "<span class='success label'>Success</span> <span style='color:green;margin-left: 10px'>" + stepName
				+ "</span>";
		test.log(LogStatus.INFO, html);
		testHist.log(LogStatus.INFO, html);

	}

	public synchronized static void stepFail(String stepName) {
		String html = "<span class='fail label'>Fail</span> <span style='color:red;margin-left: 10px'>" + stepName + "</span>";
		test.log(LogStatus.INFO, html);
		testHist.log(LogStatus.INFO, html);
	}

	public synchronized static void stepInfo(String stepName) {
		test.log(LogStatus.INFO, stepName);
		testHist.log(LogStatus.INFO, stepName);
	}

	public synchronized static void stepError(String stepName) {

		String html = "<span class='warning label'>Error</span><span class='fatal'><b>" + stepName + "</b></span>";
		test.log(LogStatus.ERROR, html);
		testHist.log(LogStatus.ERROR, html);
	}

	public synchronized static void stepError(String stepName, Throwable t) {
		String html = "<span class='warning label'>Error</span><span class='fatal'>" + stepName + "</span>";
		test.log(LogStatus.ERROR, html, t);
		testHist.log(LogStatus.ERROR, html, t);
	}

	public synchronized static void endReportSkipped(String stepName, Throwable t) {
		String html = "<span style='color:#e59127'>" + stepName + "</span>";
		test.log(LogStatus.SKIP, html, t);
		testHist.log(LogStatus.SKIP, html, t);

		extentNoHistory.endTest(test);
		extentPreserverHistory.endTest(testHist);

		extentNoHistory.flush();
		extentPreserverHistory.flush();
	}

	//Attach screenshot to the report
	public synchronized static void attachScreenshot(String imagePath) {
		String image = test.addScreenCapture(imagePath);
		test.log(LogStatus.FAIL, "ScreenShot: " + GlobalUtil.testException, image);
		testHist.log(LogStatus.FAIL, "ScreenShot:" + GlobalUtil.testException, image);
	}

	//End Report 
	public synchronized static void tearDownReport() {
		extentNoHistory.flush();
		extentPreserverHistory.flush();
		if (extentNoHistory != null){
			extentNoHistory.close();
			extentNoHistory=null;
			}
		if (extentPreserverHistory != null){
			extentPreserverHistory.close();
			extentPreserverHistory=null;
		}

	}
	public static void attachScreenshot(String imagePath,Boolean flag){
		
		String image = test.addScreenCapture(imagePath);
			    
		if(flag)
		{
			test.log(LogStatus.FAIL, "ScreenShot: "+GlobalUtil.testException  ,image);
			    testHist.log(LogStatus.FAIL,"ScreenShot:"+GlobalUtil.testException, image);
		
		}
		else
		{
			
			test.log(LogStatus.INFO, "ScreenShot: "+GlobalUtil.testException  ,image);
		    testHist.log(LogStatus.INFO,"ScreenShot:"+GlobalUtil.testException, image);
			
			
			
		}
		
			      
		}
		
}
