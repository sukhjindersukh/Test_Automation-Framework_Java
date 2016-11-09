package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
 
public class GlobalUtil {
public static String BASE_URL="https://testing.smartshow.com/login";	
public static TestData testData;
public static TestResults  testResult;
public static String screenshotFilePath="";
public static TestConfig testConfig=new TestConfig();
public static CommonSettings commonSettings=new CommonSettings(); 
public static int totalSuites=0;
public static boolean suitesRunStarted=false;
public static int lastRunId=0;
public static Exception  testException;
public static String currentBrowser;
public static String currentSuiteName;
public static String currentUserEmail;
public static String currentUserType;
public static String currentUserFirstName;
public static String currentUserLastName;
public static String currentUserFullName;
public static HashMap <String,String> propertyCurrentRecord = new HashMap<String, String>();
public static HashMap <String,String> propertyDeletedRecord = new HashMap<String, String>();
public static HashMap <String,String> updatesScheduleRecord = new HashMap<String, String>();
public static HashMap <String,String> propertyRestoredRecord = new HashMap<String, String>();
//HashMap client = new HashMap();
public static  ArrayList<String> listOfClients = new ArrayList<String>();
public static  ArrayList<String> listOfProperties = new ArrayList<String>();

public static HashMap <String,String> client = new HashMap<String, String>();
public static String clientFullName="FullName";
public static String clientEmail="Email";

//For HashMap
public static String propertyAddressKey="Address";
public static String propertyDateTimeKey="DateTime";
public static String propertyClientKey="Client";
public static String propertyAgentKey="Agent";
public static String propertyNoteKey="Note";

public static HashMap<String, String> popupCurrentData = new HashMap<String, String>();
	public static String getDateTime(){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateOfExecution= dateFormat.format(date);
		return dateOfExecution;
	}
	
	public static void renameFile(){
		
//		Path source = Paths.get(PropertyFileReader.getValue("testResultExcelPath"));
//		
//		Files.move(source, source.resolveSibling("newname"));

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		String timeStamp= dateFormat.format(date);
		
		
		  try {
			  
			  File oldFile = new File(System.getProperty("user.dir")+ConfigReader.getValue("testResultExcelPath"));
			  //File newFile  = new File(PropertyFileReader.getValue("testResultExcelPath"));
			  String newFilePath = oldFile.getAbsolutePath().replace(oldFile.getName(), "") +"\\ReportHistory\\"+ timeStamp+"-TestResult.xls";
			  
			  
			  
			  File newFile  = new File (newFilePath);
				
			    
					    
		    FileUtils.copyFile(oldFile, newFile);
		    System.out.println("History File successfully created... ");
		    
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
	}
	 
	
}
