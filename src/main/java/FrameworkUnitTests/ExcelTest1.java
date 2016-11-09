package FrameworkUnitTests;

import java.util.Date;

import org.testng.SkipException;
import org.testng.annotations.Test;

import Utilities.ExcelDataUtil;
import Utilities.LogUtil;
import Utilities.TestData;
import Utilities.TestResults;

public class ExcelTest1 {
	String suiteName="Functional";
	String testCaseID="TC_SS001";
	boolean isRun;
	public static TestResults testResult;
	public static TestData testData;
	@Test()
	public void test1() throws Throwable  {
		// TODO Auto-generated method stub
	isRun=ExcelDataUtil.getControls(suiteName, testCaseID);	
	
	try{
	if(!isRun)
		throw new SkipException("Skipping this exception");
		
			
		LogUtil.infoLog(getClass(),"Test is Started and looging start");
		TestData testData =  ExcelDataUtil.getTestDataWithTestCaseID(testCaseID);
		System.out.println(testData.getSuiteName() );
		System.out.println(testData.getTestDesc() );
		System.out.println(testData.getComplexity() );
		//System.out.println(testData.getEstimatedExecutionTime() );
		 
		TestResults testResult = new TestResults();
		testResult.setRunDateTime(new Date());
		testResult.setStatus("FAIL");
		testResult.setActualTime(22);
		testResult.setRemarks("WebDriver Exception");
		testResult.setScreenshot_ref("c:\\a.jpg");
		ExcelDataUtil.updateTestResults(testData,testResult);
		
	}//End try
		catch(SkipException se){
			//Cath this is just for logging and reporting
			System.out.println(se.getMessage());
			LogUtil.errorLog(getClass(),"Test has following errors --> "+se.getMessage(),se );
			throw new SkipException("Skipping this exception");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
