package FrameworkUnitTests;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utilities.ReportFactory_DB;
import Utilities.TestData;
import Utilities.TestResults;

/**
 * Hello world!
 *
 */
public class App_DBTest 
{
	@SuppressWarnings("deprecation")
    public static void main( String[] args ) throws ParseException
    {
    
		ReportFactory_DB.switchOn();
		ReportFactory_DB.initMySql("sql6.freemysqlhosting.net/sql6135035","sql6135035","ubg1YMgphQ");
			
	// 	ReportFactory_DB.initSqlite(Paths.get("").toAbsolutePath().toString()+"\\ExecutionReports\\DB");
		
	 	int lastRunId =ReportFactory_DB.getLastRunID()+1;
	 	
	 	
	 	for(int i=2;i<=5;i++){
	 		TestData td =new TestData();
    		TestResults tr = new TestResults();
    		
    		td.setProjectID("DAMCO");
    		td.setSuiteName("Regression");
    		td.setTestId("TC40" +i);
    		td.setTestDesc("Verify Register page");
    		td.setTestPlatformInfo("Win7, Firefox");
    		td.setComplexity("HIGH");
    		td.setExpectedTime((25.50)+i);
    		
    		//---------------------------------------------------
    		
    		tr.setRunId(lastRunId);
//    		
    		
    		
    		Date d =new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		
    		String autodate = new String(String.format("2016-%d-%d",1,1));
    		
    		
    		d =df.parse(autodate);
    		
    		tr.setRunDateTime(d);
    		tr.setStatus("Fail");
    		tr.setRemarks("Element Not found");
    		tr.setActualTime(20.20);
    		tr.setScreenshot_ref("c:\\a.jpg");
    		//---------------------------------------------------
    		
    		
    		ReportFactory_DB.updateTestResultsDB(td, tr);
    		try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
    		
    		//ReportFactory_DB.selectResultsDB("SELECT * FROM `TBL_RESULTS` WHERE 1");
    		
    		//ReportFactory_DB.deleteRecordFromDB("delete from TBL_RESULTS where TEST_ID = 'TC002' ");
    	 	
	 		String query="";
    	 	//Select Record with date
    	 	// query="SELECT * FROM `TBL_RESULTS` WHERE RUN_DATE_TIME BETWEEN '2016/09/02 00:00:00.00' AND '2016/09/02 23:59:59.999'";
    	 	
    	 	String excelTemplatePath= Paths.get("").toAbsolutePath()+"\\src\\test\\resources\\ExcelFiles\\Template_TestResults_Automation.xls";
    	 	ReportFactory_DB.setExcelTemplatePath(excelTemplatePath);
    		query="SELECT * FROM `TBL_RESULTS` WHERE 1 Order By RUN_DATE_TIME DESC";
    		
    	 //	query="SELECT * FROM `TBL_RESULTS` WHERE `PROJECT_ID` = 'Freande' ORDER BY `RUN_ID` DESC LIMIT 0,1;";
    	     	 	 
    		ReportFactory_DB.printDataToScreen(query);
    		
    		
    		
    		//query="Select * from TBL_RESULTS where `PROJECT_ID`= 'TX' AND `RUN_ID`  In (SELECT `RUN_ID` FROM (Select RUN_ID from TBL_RESULTS Group By Run_ID order by RUN_ID desc Limit 0,3) as T )";
    	 	
    		String csvDestPath= Paths.get("").toAbsolutePath()+"\\ExecutionReports\\DB\\CSV\\TestResults.csv";
    		ReportFactory_DB.exportDataToCsv(query, csvDestPath);

    	 	String excelDestPath= Paths.get("").toAbsolutePath()+"\\ExecutionReports\\DB\\Excel\\TestResults.xls";
    	 	ReportFactory_DB.exportDataToExcel(query,excelDestPath);
    	 	
    		//Close DB Connection
    		ReportFactory_DB.closeDBConnection();
    				
    	}
}


