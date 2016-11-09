package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.internal.annotations.TestOrConfiguration;

public class ExcelDataUtil {

	private static FileInputStream fs = null;
	private static Workbook workbook = null;
	private static Sheet sheet;
	private static Row row;
	private static int columnToLookTestCaseID = Integer.parseInt(ConfigReader.getValue("columnToLookTestCaseID"));
	private static String testDatafilePath = ConfigReader.getValue("testDataExcelPath");
	private static String testDataSheetName = ConfigReader.getValue("testDataSheet");
	private static boolean IsCopyTemplate = false;
	private static String filePath = "";
	public static List<String> testsList = new ArrayList<String>();

	public static void init(String filePath, String sheetName) {
		String fileExtensionName = filePath.substring(filePath.indexOf("."));
		try {
			fs = new FileInputStream(filePath);
			if (fileExtensionName.equals(".xlsx")) {
				// If it is xlsx file then create object of XSSFWorkbook class
				workbook = new XSSFWorkbook(fs);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(".xls")) {
				// If it is xls file then create object of XSSFWorkbook class
				workbook = new HSSFWorkbook(fs);
			}
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public static TestData getTestDataWithTestCaseID(String testCaseID) {
		boolean found = false;
		TestData testdata = new TestData();
		// Initialize class
		// Get Path and Sheet Name from Property File
		init(testDatafilePath, testDataSheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		try {
			while (rowIterator.hasNext()) {
				row = (Row) rowIterator.next();
				if (row.getCell(columnToLookTestCaseID).getStringCellValue().equalsIgnoreCase(testCaseID)) {
					Iterator<Cell> cellIterator = row.cellIterator();
					ArrayList<String> currentRowData = new ArrayList<String>();
					found = true;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String CellData = "";

						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_FORMULA:
							CellData = "" + cell.getCellFormula();
							break;

						case Cell.CELL_TYPE_NUMERIC:
							CellData = "" + cell.getNumericCellValue();
							break;

						case Cell.CELL_TYPE_STRING:
							CellData = "" + cell.getStringCellValue();
							break;

						default:
						}
						currentRowData.add(CellData);

					} // Cell Iterator

					// dataMap.put(tcID, currentRowData);

					testdata.setSuiteName(currentRowData.get(0));
					testdata.setTestId(currentRowData.get(1));
					testdata.setTestDesc(currentRowData.get(2));
					testdata.setComplexity(currentRowData.get(3));
					testdata.setExpectedTime(Double.parseDouble(currentRowData.get(4)));
					// testdata.setTestPlatformInfo("NA");
					// testdata.setProjectID("");
					break;
				} // End if Found an row

			} //// Row Iterator

			fs.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		if (!found)
			System.out.println("No data found with given key!!");

		return testdata;

	}// End of getSheetData

	public static File getCopyOfTemplateFile() throws Exception {

		// Copy a fresh Result ExcelFile from Master
		File dest = null;
		File source = null;
		try {
			String workingDir = System.getProperty("user.dir").toString();
			String SourceFilePath = ConfigReader.getValue("Template_testResultExcelPath");
			source = new File(SourceFilePath);
			String fileName = "";

			String fileExtensionName = SourceFilePath.substring(SourceFilePath.indexOf("."));

			if (fileExtensionName.equals(".xlsx")) {
				// If it is xlsx file then create object of XSSFWorkbook class
				fileName = "TestResult" + ".xlsx";
			}

			// Check condition if the file is xls file
			else if (fileExtensionName.equals(".xls")) {
				// If it is xls file then create object of XSSFWorkbook class
				fileName = "TestResult" + ".xls";
			}
			String DestFilePath = workingDir + "\\ExecutionReports\\ExcelReport\\" + fileName;
			dest = new File(DestFilePath);
			FileUtils.copyFile(source, dest);
			return dest;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	public static void updateTestResults(TestData testData, TestResults testResult) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// Now format the date
		String executionDate = dateFormat.format(date);
		// long totalExecTime;
		// totalExecTime = tcEndTime - tcStartTime;
		// System.out.println("I am in update result fun");
		// System.out.println("******Test Data******");
		// System.out.println("Set" + testData.getTestSet() );
		// System.out.println("Set" +testData.getTestCaseSummary() );
		// System.out.println("Set" +testData.getComplexity() );
		// System.out.println("Set" +testData.getEstimatedExecutionTime() );
		// System.out.println("******Test Result******");
		// System.out.println("******Test Data******");
		// System.out.println("Set " + testResult.getDateOfExecution());
		// System.out.println("Set " +testResult.getFailedScreenShotReference()
		// );
		// System.out.println("Set " +testResult.getReasonForFailure() );
		// System.out.println("Set " +testResult.getTotalTimeTaken() );
		//

		FileInputStream fis = null;
		Workbook wb = null;
		FileOutputStream fos = null;
		String sheetName;

		try {
			if (!IsCopyTemplate) {
				// This method will get the Result Excel file
				filePath = getCopyOfTemplateFile().getAbsolutePath();
				IsCopyTemplate = true;
			}

			fis = new FileInputStream(filePath);
			wb = WorkbookFactory.create(fis);
			fos = new FileOutputStream(filePath);
			sheetName = ConfigReader.getValue("testResultSheet");
			Sheet sheet = wb.getSheet(sheetName);
			// styleFail.setFillPattern(CellStyle.ALIGN_FILL);
			int startRow;

			System.out.println(ConfigReader.getValue("testResultSheet"));
			startRow = sheet.getLastRowNum();
			startRow++;

			// Fill in a row of Result Set
			// Set test suite name
			sheet.createRow(startRow).createCell(0).setCellValue(testData.getSuiteName());

			// Set Test ID
			sheet.getRow(startRow).createCell(1).setCellValue(testData.getTestId());

			// Set Test description
			sheet.getRow(startRow).createCell(2).setCellValue(testData.getTestDesc());
			
			//Set Enviornment info
			sheet.getRow(startRow).createCell(3).setCellValue(testData.getTestPlatformInfo());
			
			// Set Date of Excecution
			sheet.getRow(startRow).createCell(4).setCellValue(executionDate);

			// Set Complexity
			sheet.getRow(startRow).createCell(5).setCellValue(testData.getComplexity());

			// Set Status of test
			sheet.getRow(startRow).createCell(6).setCellValue(testResult.getStatus());

			// Set Expected time
			sheet.getRow(startRow).createCell(7).setCellValue(testData.getExpectedTime());

			// Set total time taken
			sheet.getRow(startRow).createCell(8).setCellValue(testResult.getActualTime());

			// Set remarks
			sheet.getRow(startRow).createCell(9).setCellValue(testResult.getRemarks());

			//Set Screenshot ref
			sheet.getRow(startRow).createCell(10).setCellValue(testResult.getScreenshot_ref());

			fos.flush();
			wb.write(fos);

			wb.close();
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	public static CommonSettings getCommonSettings() {
		// 1. Read Excel File
		CommonSettings commonSettings = new CommonSettings();
		
		String sheetName = ConfigReader.getValue("AutomationControlSheet");

		FileInputStream fis = null;
		Workbook wb = null;
		try {
			fis = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			wb = WorkbookFactory.create(fis);

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				System.out.println("Error! No such sheet available in Excel file: " + sheetName);
				throw new Exception("No such sheet available in Excel file: " + sheetName);
			}

			Sheet sheet = wb.getSheet(sheetName);

			// Set Project name from Column B1
			commonSettings.setProjectName(sheet.getRow(0).getCell(1).getStringCellValue());

			// Set Fixed Common Settings

			// 1. Set Application type Column[B17] Row =16, Column =1
			String val = sheet.getRow(16).getCell(1).getStringCellValue();
			commonSettings.setAppType(val);

			// 2. Set Application environment type Column[B18] Row =18, Column =1
			val = sheet.getRow(17).getCell(1).getStringCellValue();
			commonSettings.setAppEnviornment(val);
			
			
			// 3. Set Email output(Y/N)  Column[B20] Row =19, Column =1
			val = sheet.getRow(19).getCell(1).getStringCellValue();
			commonSettings.setEmailOutput(val);
			
			// 4. Set Email Id Comma List  Column[B25] Row =24, Column =1
			val = sheet.getRow(24).getCell(1).getStringCellValue();
			commonSettings.setEmailIds(val);
			
			
			// 5. Set Html report (Y/N)  Column[B26] Row =25, Column =1
			val = sheet.getRow(25).getCell(1).getStringCellValue();
			commonSettings.setHtmlReport(val);
						
			// 6. Set XLS report (Y/N)  Column[B27] Row =26, Column =1
			val = sheet.getRow(26).getCell(1).getStringCellValue();
			commonSettings.setXlsReport(val);
			
			// 7. Set Test Logs (Y/N)  Column[B28] Row =27, Column =1
			val = sheet.getRow(27).getCell(1).getStringCellValue();
			commonSettings.setTestLogs(val);
			
			// 8. Set Test management (Y/N)  Column[B31] Row =30, Column =1
			val = sheet.getRow(30).getCell(1).getStringCellValue();
			commonSettings.setTestMgmt(val);
			
			// 8. Set Defect management (Y/N)  Column[B32] Row =31, Column =1
			val = sheet.getRow(31).getCell(1).getStringCellValue();
			commonSettings.setDefectMgmt(val);
						

			

		} // End try
		catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		return commonSettings;

	}
	
	
	
	
	public static List<TestConfig> getSuitesToRun() {
		// 1. Read Excel File
		List<TestConfig> listTestConfigs = new ArrayList<TestConfig>();
				
		int startRowNum = ConfigReader.getIntValue("rowToStart");
		int columnToLookFlag = ConfigReader.getIntValue("columnToLookFlag");
		String sheetName = ConfigReader.getValue("AutomationControlSheet");

		FileInputStream fis = null;
		Workbook wb = null;
		try {
			fis = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			wb = WorkbookFactory.create(fis);

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				System.out.println("Error! No such sheet available in Excel file: " + sheetName);
				throw new Exception("No such sheet available in Excel file: " + sheetName);
			}

			Sheet sheet = wb.getSheet(sheetName);

						while (true) {
				if (sheet.getRow(startRowNum) == null)
					break;
				else {
					 //System.out.println("YN field " +sheet.getRow(startRowNum).getCell(columnToLookFlag).getStringCellValue());

					 if (sheet.getRow(startRowNum).getCell(columnToLookFlag).getStringCellValue()
								.equalsIgnoreCase(""))
					 {
						 break;
					 }
					 
					 
					if (sheet.getRow(startRowNum).getCell(columnToLookFlag).getStringCellValue()
							.equalsIgnoreCase("Y")) {
						
						TestConfig testConfigs= new TestConfig();
						testConfigs.setSuiteName(sheet.getRow(startRowNum).getCell(0).getStringCellValue());
						testConfigs.setBrowserName(sheet.getRow(startRowNum).getCell(2).getStringCellValue());
						testConfigs.setSuiteId(sheet.getRow(startRowNum).getCell(3).getStringCellValue());
						
						if(testConfigs.getSuiteId().isEmpty())
							break;
						
						
						//Get Test List based on Suite Id that is enabled (Y)
						
						
						if (wb.getSheetIndex(wb.getSheet(sheet.getRow(startRowNum).getCell(3).getStringCellValue())) == -1) {
							System.out.println("Error! No such sheet available in Excel file: " + testConfigs.getSuiteId());
							throw new Exception("No such sheet available in Excel file: " + testConfigs.getSuiteId());
						}
						
						
						Sheet suiteSheet = wb.getSheet(testConfigs.getSuiteId());
						
						//Loop Through Rows and get All enabled tests
						Iterator rowItr = suiteSheet.rowIterator();
						List<String> testsList = new ArrayList<String>();
						
						while(rowItr.hasNext()){
							Row r =(Row) rowItr.next();
							
							if(r.getCell(1).getStringCellValue().equalsIgnoreCase("Y")){
								//If Any Test has Yes Flag Then add to List
								String testId;
								testId = r.getCell(0).getStringCellValue();
								testsList.add(testId);
							}
							
						}//End while
						
						//Add TestId to testConfig
						testConfigs.setTestsList(testsList);
						
						//Add testConfig To Lis of configs
						listTestConfigs.add(testConfigs);
						
					}
				}
				// Move to next Row
				startRowNum++;
			} // End while

		} // End try
		catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		return listTestConfigs;

	}

	public static String getFlagExcel(String sheetName, String searchValue) {
		int sheetSize, rowNum = 1;
		String strVal, strflag = "NA";
		try {
			FileInputStream fis = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			Workbook wb = WorkbookFactory.create(fis);

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				System.out.println("Error! No such sheet available in Excel file: " + sheetName);
				throw new Exception("No such sheet available in Excel file: " + sheetName);
			}

			Sheet sheet = wb.getSheet(sheetName);
			sheetSize = sheet.getLastRowNum();

			for (int i = rowNum; i <= sheetSize; i++) {
				// System.out.println("Current TestID:"
				// +sheet.getRow(i).getCell(0).getStringCellValue() );
				strVal = sheet.getRow(i).getCell(0).getStringCellValue();

				if (searchValue.equalsIgnoreCase(strVal)) {
					strflag = wb.getSheet(sheetName).getRow(i).getCell(1).getStringCellValue();
					break;
				}
			}

			if (fis != null)
				fis.close();

			if (wb != null)
				wb.close();
		} catch (Exception e) {
			strflag = "NA";
			e.printStackTrace();
		}

		return strflag;
	}

	// Read Data from Excel File AutomationControlSheet.xls(SuiteControlSheet),
	// Reading Y/N for including a test case in suite to run.
	public static boolean isSuiteRunnable(String suiteName) {
		boolean isRunnable = false;
		String strVal;
		try {
			strVal = getFlagExcel("SuiteControlSheet", suiteName);
			if (strVal.equalsIgnoreCase("Y")) {
				isRunnable = true;
			} else {
				isRunnable = false;
			}
		} catch (Exception e) {
			isRunnable = false;
			e.printStackTrace();
		}

		return isRunnable;
	}

	// Read Excel file for Script to run. Like Regression, Smoke, Functional
	public static boolean isScriptRunnable(String suiteName, String scriptName) {
		boolean isRunnable = false;
		String strVal = null;
		try {
			strVal = getFlagExcel(suiteName, scriptName);
			if (strVal.equalsIgnoreCase("Y")) {
				isRunnable = true;
			} else {
				isRunnable = false;
			}
		} catch (Exception e) {
			isRunnable = false;
			e.printStackTrace();
		}
		return isRunnable;
	}

	public void printTestStatus(String suiteName, String searchValue, String testStatus) throws Exception {
		int nsheetSize, rowNum = 2;
		String strVal, sheetName = null;

		if (suiteName.equals("Regression")) {
			sheetName = "RegressionSuite";
		} else if (suiteName.equals("Smoke")) {
			sheetName = "SmokeSuite";
		} else if (suiteName.equals("Functional")) {
			sheetName = "FunctionalSuite_HI";
		}
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Workbook wb = null;
		try {
			// FileInputStream fis=new FileInputStream(getValue("sheetPath"));
			fis = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			wb = WorkbookFactory.create(fis);
			fos = new FileOutputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			nsheetSize = wb.getSheet(sheetName).getLastRowNum();

			for (int i = rowNum; i <= nsheetSize; i++) {
				strVal = wb.getSheet(sheetName).getRow(i).getCell(0).getStringCellValue();
				if (searchValue.equals(strVal)) {
					wb.getSheet(sheetName).getRow(i).createCell(2).setCellValue(testStatus);
					wb.write(fos);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// *********************************************************************
		// Start
		finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
					wb.close();

				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		} // End
			// *********************************************************************
	}

	public static boolean getControls(String suiteName, String testCaseID) throws Exception {

		if (suiteName.trim().isEmpty()) {
			System.out.println("Suite name not found!!!");
			return false;
		}

		if (testCaseID.trim().isEmpty()) {
			System.out.println("Test name is not specified!!!");
			return false;
		}

		// System.out.println("Suite to run:" +suiteName);
		// System.out.println("Test to run:" +testCaseID);

		return ExcelDataUtil.isScriptRunnable(suiteName.trim(), testCaseID.trim());
	}

	// Get browsers List
	public static Object[][] getBrowsersList() throws Exception {
		String[][] tabArray = null;
		List<String> br = new ArrayList<String>();
		try {

			FileInputStream ExcelFile = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			// Access the required test data sheet
			new WorkbookFactory();
			Workbook wb = WorkbookFactory.create(ExcelFile);
			Sheet sheet = wb.getSheet(ConfigReader.getValue("BrowserControlSheet"));
			Row row;

			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0;
			while (rowIterator.hasNext()) {
				row = (Row) rowIterator.next();

				if (i > 0)
					br.add(row.getCell(0).getStringCellValue());
				i++;
			} // End while
			tabArray = new String[br.size()][1];
			int r = 0;
			for (String s : br) {
				tabArray[r][0] = s;
				r++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public String getBrowser() throws Exception, Exception {
		String[][] tabArray = null;
		List<String> br = new ArrayList<String>();
		try {
			FileInputStream ExcelFile = new FileInputStream(ConfigReader.getValue("AutomationControlExcelPath"));
			// Access the required test data sheet
			new WorkbookFactory();
			Workbook wb = WorkbookFactory.create(ExcelFile);
			Sheet sheet = wb.getSheet(ConfigReader.getValue("BrowserControlSheet"));
			Row row;

			Iterator<Row> rowIterator = sheet.iterator();

			int i = 0;
			while (rowIterator.hasNext()) {
				row = (Row) rowIterator.next();
				if (i > 0)
					br.add(row.getCell(0).getStringCellValue());
				i++;
			} // End while
			tabArray = new String[br.size()][1];
			int r = 0;
			for (String s : br) {
				tabArray[r][0] = s;
				r++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return tabArray[0][0];
	}

}// End Excel class
