/*			About CustomListner
 * ====================================
 * This class is a central point to handle test events.
 * It responsible to handle all the common events that are generated while test is started, running 
 * and going to stop.
 * Class handles all the basic setup and tear down functionalities as well as handle the updating test results
 * into HTML, Excel, Database and logs.
 * 
 * */


package TestNGListeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import TestingXperts.smartshow.pages.LoginPage;
import Utilities.ConfigReader;
import Utilities.DriverUtil;
import Utilities.ExcelDataUtil;
import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.LogUtil;
import Utilities.ReportFactory_DB;
import Utilities.TestData;
import Utilities.TestResults;
import Utilities.Utility;
import Utilities.sendMail;

public class CustomListeners  extends Utility implements ISuiteListener, IInvokedMethodListener2,ITestListener {
	LocalDateTime startTime, endTime;
	String testCaseID; 
	
		
	//Before Suite start. Once suite is going to start this function will trigger.
	public void onStart(ISuite suite) {
		//Get Driver
		//Start base URL
		try {
			// Get all the common setting from excel file that are required for reports. 
			GlobalUtil.commonSettings =ExcelDataUtil.getCommonSettings();
			
			//Current suite name extracted from the xml file. 
			GlobalUtil.currentSuiteName = suite.getName();
			LogUtil.infoLog(getClass(), GlobalUtil.currentSuiteName +" suite started" + " at " +new Date());
			//This driver coming from XML File from the defined parameter name Browser.
			
			//Parameter with name Browser
			 String browser="";
			 browser= suite.getParameter("Browser");
			 System.out.println("Suite browser: " +browser);
			 
			 //If parameter is not found in xml then default browser is always Firefox.
				if(browser==null)
					{
					browser="Firefox";
					GlobalUtil.testConfig.setBrowserName(browser);
					}

			//Set browser name for reporting purpose.
			GlobalUtil.currentBrowser =browser;
			
			//Init new driver object
			driver = DriverUtil.getBrowser(browser);
			
			driver.get(ConfigReader.getValue("BASE_URL"));
			
			//Switch on database reporting
			ReportFactory_DB.switchOn();
			
			//To start hosted db
			//ReportFactory_DB.initMySql("sql6.freemysqlhosting.net/sql6135035","sql6135035","ubg1YMgphQ");
			
			//Local flat file sqlite database
			ReportFactory_DB.initSqlite(Paths.get("").toAbsolutePath().toString()+"\\ExecutionReports\\DB");
		
			//Get Last Run Id from DB for reporting purpose.
			//Get run id only one time during all the run
			if(GlobalUtil.suitesRunStarted==false){
				GlobalUtil.lastRunId=(ReportFactory_DB.getLastRunID()+1);
			}
			GlobalUtil.suitesRunStarted=true;
			
			//Do login. Comment if this not required
			if(!LoginPage.isLogin(driver)){
				System.out.println("Login process started...");
				LoginPage.doLogin(driver, ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}//End onStart()
	
	
	//After Suite. Once suite run has finished this function will trigger.
	public void onFinish(ISuite suite) {
		System.out.println("Suite run has finished at - " + new Date());
		GlobalUtil.totalSuites--;
		LogUtil.infoLog(getClass(), GlobalUtil.currentSuiteName +" suite finished" + " at " +new Date());
		try {
			//ReportFactory_DB.getComparisonReport(GlobalUtil.testConfig.getProjectName());
			ReportFactory_DB.getComparisonReport(GlobalUtil.commonSettings.getProjectName());
			
			//Teardown only perform when all suites has finished
			if(GlobalUtil.totalSuites<=0){
				HtmlReportUtil.tearDownReport();
			}
			GlobalUtil.renameFile();
			ReportFactory_DB.closeDBConnection();
			DriverUtil.closeAllDriver();
			
			if( GlobalUtil.commonSettings.getEmailOutput().equalsIgnoreCase("Y"))
				sendMail.sendEmailToClient();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			String htmlReportFile = System.getProperty("user.dir")+"\\"+ Utility.getValue("HtmlReportFullPath");
			File f = new File(htmlReportFile);
			if(f.exists())
			{
			 
			try {
				Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" +htmlReportFile +"\"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
	}

	
	//Run before Test start belong to  IInvokedMethodListener2
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult arg1, ITestContext arg2) {
		// TODO Auto-generated method stub
		System.out.println("Before Test start configure...");
	   //System.out.println(arg0.getTestMethod().getTestClass().getName().substring(30));
		
		testCaseID = method.getTestMethod().getTestClass().getName().substring(30);
		
		startTime = LocalDateTime.now();
		
		GlobalUtil.testData =  new TestData();
		GlobalUtil.testResult = new TestResults(); 
		GlobalUtil.testData = ExcelDataUtil.getTestDataWithTestCaseID(testCaseID);
		
		
		//SetRun ID
		GlobalUtil.testResult.setRunId(GlobalUtil.lastRunId);
		
		//Reset the screenshot path required otherwise it will write same into test result files
		GlobalUtil.screenshotFilePath="";
		
		//Set current suite name for reporting
		GlobalUtil.testData.setSuiteName(GlobalUtil.currentSuiteName);
		//Set platform for reporting
		GlobalUtil.testData.setTestPlatformInfo(System.getProperty("os.name")+"-" +GlobalUtil.currentBrowser);
		//Set Project ID
		GlobalUtil.testData.setProjectID(GlobalUtil.commonSettings.getProjectName());
		GlobalUtil.testResult.setRunDateTime(new Date());
		
		LogUtil.infoLog(getClass(), "Test Started: "+ testCaseID );
		LogUtil.infoLog(getClass(), "Test description: "+GlobalUtil.testData.getTestDesc() );
	}

public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub
	
}
public void onTestSuccess(ITestResult result) {
	
	endTime = LocalDateTime.now();
	Duration.between(startTime, endTime).getSeconds();
	LogUtil.infoLog(getClass().getSimpleName(), "Total Time taken in(seconds):" + + Duration.between(startTime, endTime).getSeconds());
	
	GlobalUtil.testResult.setStatus("PASS");
	GlobalUtil.testResult.setActualTime(Duration.between(startTime, endTime).getSeconds());
	
	//Update results in excel
	ExcelDataUtil.updateTestResults(GlobalUtil.testData, GlobalUtil.testResult);
	
	//Update results in DB
	ReportFactory_DB.updateTestResultsDB(GlobalUtil.testData, GlobalUtil.testResult);
	
	//End HTML Report for current test
	HtmlReportUtil.endReport(true, GlobalUtil.testData.getTestId());
}


public void onTestFailure(ITestResult result) {
	endTime = LocalDateTime.now();
	Duration.between(startTime, endTime).getSeconds();
	LogUtil.infoLog(getClass().getSimpleName(), "Total Time taken in(seconds):" + + Duration.between(startTime, endTime).getSeconds());

	GlobalUtil.testResult.setStatus("FAIL");
	GlobalUtil.testResult.setActualTime(Duration.between(startTime, endTime).getSeconds());
	GlobalUtil.testResult.setRemarks(result.getThrowable().getMessage());
	
	LogUtil.errorLog(getClass().getSimpleName(), result.getThrowable().getMessage());	
	
	//Update results in excel
	ExcelDataUtil.updateTestResults(GlobalUtil.testData, GlobalUtil.testResult);
	
	//Update results in db
	ReportFactory_DB.updateTestResultsDB(GlobalUtil.testData, GlobalUtil.testResult);
	
	//End HTML Report for current test
	HtmlReportUtil.endReport(false, GlobalUtil.testData.getTestId());
	
}
public void onTestSkipped(ITestResult result) {
	endTime = LocalDateTime.now();
	Duration.between(startTime, endTime).getSeconds();
	LogUtil.infoLog(getClass().getSimpleName(), "Test Skipped - Total Time taken in(seconds):" +  Duration.between(startTime, endTime).getSeconds());

	GlobalUtil.testResult.setStatus("SKIPPED");
	GlobalUtil.testResult.setActualTime(Duration.between(startTime, endTime).getSeconds());
	
	ExcelDataUtil.updateTestResults(GlobalUtil.testData, GlobalUtil.testResult);
	ReportFactory_DB.updateTestResultsDB(GlobalUtil.testData, GlobalUtil.testResult);
	HtmlReportUtil.endReportSkipped(GlobalUtil.testData.getTestId(),GlobalUtil.testException);

}

//These methods are not used but required for Listner purpose
public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
}
public void onStart(ITestContext context) {
	// TODO Auto-generated method stub
	
}
public void onFinish(ITestContext context) {
	// TODO Auto-generated method stub
	
}


@Override
public void afterInvocation(IInvokedMethod arg0, ITestResult arg1, ITestContext arg2) {

}
@Override
public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
	// TODO Auto-generated method stub
	
}
@Override
public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
	// TODO Auto-generated method stub
	
}

	
	
}//End class
