/*  INTORDUCTION
 * ----------------------------------------------------------------------------------
 *  Start date: 31-08-2016
 *  Last modified: 02-11-2016
 *  
 * ReportFactory_DB is responsible to save test execution result in the database.
 * That can be a Sqlite flat database file or MySql Server database
 *-------------------------------------------------------------------------------*/

/*
 * Usage
 * -------------------
 * 1. Set the Database url/path - setDbUrl(String)
 * 2. Set Switch setDbSwitch(1) -> Possible values On/Off or 0/1
 * 3. Update Results
 * 4. Close Connection
 * */

package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

/**
 * @author SUKHJINDER SINGH
 * @version 1.0
 * @category Save Test execution Result to Database
 * 
 */
public class ReportFactory_DB {

	private static Logger logger = Logger.getLogger(ReportFactory_DB.class.getSimpleName());
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://";

	// Database credentials
	private static String USER = "sql6133996";
	public static String PASS = "2vtM3QiUQM";
	private static String excelTemplatePath = "";
	private static String excelDestPath = "";
	// Database objects
	private static Connection cnxn = null;

	private static String getDB_URL() {
		return DB_URL;
	}

	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	public static String getPASS() {
		return PASS;
	}

	public static void setPASS(String pass) {
		PASS = pass;
	}

	public static String getExcelTemplatePath() {
		return excelTemplatePath;
	}

	public static void setExcelTemplatePath(String excelTemplatePath) {
		ReportFactory_DB.excelTemplatePath = excelTemplatePath;
	}

	public static String getExcelDestPath() {
		return excelDestPath;
	}

	private static void setExcelDestPath(String excelDestPath) {
		ReportFactory_DB.excelDestPath = excelDestPath;
	}

	Statement stmt = null;
	ResultSet rs = null;

	public enum Switch {
		OFF, ON
	}

	// Set initially Switch OFF
	private static Switch dbSwitch = Switch.OFF;

	public synchronized static void initSqlite(String dbDir) {
		if (isSwitchOn()) {
			try {
				// Register Driver
				Class.forName("org.sqlite.JDBC");
				String dbUrl = "jdbc:sqlite:" + dbDir + "\\TestResults.db";

				// Open data base connection
				cnxn = DriverManager.getConnection(dbUrl);
				Statement stmt = null;
				if (cnxn != null) {
					logger.log(Level.FINEST, "Database successfully connected.");

					DatabaseMetaData dm = (DatabaseMetaData) cnxn.getMetaData();
					System.out.println("+=============Database started=============+");
					System.out.println("Database driver name: " + dm.getDriverName());
					String dbInfo = new String(String.format("Product name %s, Version %s", dm.getDatabaseProductName(),
							dm.getDatabaseProductVersion()));
					System.out.println(dbInfo);
					System.out.println("+==========================================+\n");
					// deleteTable();

					stmt = cnxn.createStatement();
					
					// Create table
					String sql = "CREATE TABLE IF NOT EXISTS TBL_RESULTS ("
							+ "RECORD_ID  INTEGER PRIMARY KEY   AUTOINCREMENT," + "PROJECT_ID  varchar(25) NOT NULL,"
							+ "SUITE_NAME  varchar(50) NOT NULL," + "TEST_ID  varchar(25) NOT NULL,"
							+ "TEST_DESC  varchar(500) NULL," + "TEST_ENVIRONMENT_INFO   varchar(50) NULL,"
							+ "COMPLEXITY  varchar(6) NULL DEFAULT 'Low'," + "RUN_ID  int NOT NULL DEFAULT 1,"
							+ "RUN_DATE_TIME  DATE NOT NULL," + "STATUS  varchar(10) NOT NULL,"
							+ "REMARKS  varchar(500) NULL," + "EXPECTED_TIME double(4,2) NULL DEFAULT 0.0,"
							+ "ACTUAL_TIME double(4,2) NULL DEFAULT 0.0," + "SCREENSHOT_REF varchar(200) NULL"
							// +"CONSTRAINT RECORD_ID PRIMARY KEY (RECORD_ID)"
							+ ");";
					stmt.executeUpdate(sql);
					stmt.close();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				ReportFactory_DB.switchOff();

			} // end finally try

		} else {
			System.err.println("Database reporter is switched off");
		}
	}

	public synchronized static void initMySql(String dbUrl, String user, String password) {
		if (isSwitchOn()) {
			try {
				// Register Driver
				Class.forName("com.mysql.jdbc.Driver");
				ReportFactory_DB.DB_URL += dbUrl;

				// Open data base connection
				cnxn = DriverManager.getConnection(ReportFactory_DB.DB_URL, user, password);
				if (cnxn != null) {
					logger.log(Level.FINEST, "Database successfully connected.");

					DatabaseMetaData dm = (DatabaseMetaData) cnxn.getMetaData();
					System.out.println("+=============Database started=============+\n");
					System.out.println("Database driver name: " + dm.getDriverName());
					String dbInfo = new String(String.format("Product name %s, Version %s", dm.getDatabaseProductName(),
							dm.getDatabaseProductVersion()));
					System.out.println(dbInfo);
					System.out.println("+==========================================+\n");
					// deleteTable();
					createTable();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				ReportFactory_DB.switchOff();
			} // end finally try
		} // End if Switch on
		else {
			System.err.println("Database reporter is switched off");
		}
	}// End init

	private static boolean isSwitchOn() {
		if (dbSwitch.equals(Switch.ON))
			return true;
		else
			return false;
	}

	/*
	 * START - All Getter And Setter
	 * /*-----------------------------------------------------------------------
	 * --------
	 */
	public String getDbSwitch() {
		return dbSwitch.toString();
	}
	public static void switchOn() {
		ReportFactory_DB.dbSwitch = ReportFactory_DB.Switch.ON;
		logger.log(Level.INFO, "Database reporter is switched on!");
	}
	public static void switchOff() {
		ReportFactory_DB.dbSwitch = ReportFactory_DB.Switch.OFF;
		logger.log(Level.INFO, "Database reporter is switched off!!");
	}
	private static void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS TBL_RESULTS ("
				+ "	RECORD_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" + " PROJECT_ID nvarchar(25) NOT NULL,\n"
				+ "	SUITE_NAME nvarchar(75) NOT NULL,\n" + "	TEST_ID nvarchar(50) NOT NULL,\n"
				+ " TEST_DESC nvarchar(500) NOT NULL,\n" + " TEST_ENVIRONMENT_INFO nvarchar(50) NOT NULL,\n"
				+ " COMPLEXITY NVARCHAR(6) NOT NULL DEFAULT 'LOW',\n" + " RUN_ID INT NOT NULL,\n"
				+ " RUN_DATE_TIME DATE NOT NULL,\n" + " STATUS NVARCHAR(10) NOT NULL,\n" + " REMARKS NVARCHAR(1000),\n"
				+ "EXPECTED_TIME double(4,2) DEFAULT 0.0,\n" + "ACTUAL_TIME double(4,2) DEFAULT 0.0,\n"
				+ "SCREENSHOT_REF nvarchar(100) DEFAULT 'NA'" + ");";
		try {
			Statement stmt = cnxn.createStatement();
			boolean b = stmt.execute(sql);

			if (b = true) {
				logger.log(Level.INFO, "Table ready for data!");

			} else {
				logger.log(Level.SEVERE, "New Table creation Failed");
			}
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
			System.err.println("Table creation error -->" + ex);
		}
	}//End createTable()

	private static void deleteTable() {
		String sql = "DROP TABLE IF EXISTS TBL_RESULTS; ";
		try {
			Statement stmt = cnxn.createStatement();
			boolean b = stmt.execute(sql);
			if (b = true) {
				logger.log(Level.INFO, "Table deleted!");

			} else {
				logger.log(Level.SEVERE, "Table delete Failed");
			}
			if (stmt != null)
				stmt.close();

		} catch (Exception ex) {
			System.err.println("Table delete error -->" + ex);
		}
	}//End deleteTable()

	public static synchronized void updateTestResultsDB(TestData td, TestResults tr) {
		/*
		 * Table Schema-Columns RECORD_ID PROJECT_ID SUITE_NAME TEST_ID
		 * TEST_DESC TEST_PLATFORM_INFO COMPLEXITY RUN_ID RUN_DATE_TIME
		 * LAST_UPDATE STATUS REMARKS EXPECTED_TIME ACTUAL_TIME SCREENSHOT_REF
		 */

		/*
		 * This is working fine String sql =
		 * "INSERT INTO  `TBL_RESULTS` (  `PROJECT_ID` ,  `SUITE_NAME` ,  `TEST_ID` ,  `TEST_DESC` ,  `TEST_ENVIRONMENT_INFO` ,  `COMPLEXITY` ,  `RUN_ID` , `RUN_DATE_TIME` ,  `STATUS` ,  `REMARKS` ,  `EXPECTED_TIME` ,  `ACTUAL_TIME` ) "
		 * +
		 * "VALUES ('P4',  'SUITE',  'TC003',  'THIS IS DB TEST',  'WIN7',  'LOW', 1,  '2016-02-09 12:40',  'PASS',  'EXECUTED SUCCESSFULLY', 10.00, 25.25);"
		 * ;
		 */

		if (isSwitchOn()) {
			if (cnxn != null) {
				String sql = "INSERT INTO  `TBL_RESULTS` ( " + "`PROJECT_ID` , " + "`SUITE_NAME` ," + "`TEST_ID` , "
						+ " `TEST_DESC` , " + " `TEST_ENVIRONMENT_INFO` ," + " `COMPLEXITY` ," + "`RUN_ID` , "
						+ "`RUN_DATE_TIME` ," + "`STATUS` ,  " + "`REMARKS` ,  " + "`EXPECTED_TIME` ,  "
						+ "`ACTUAL_TIME`,`SCREENSHOT_REF`) " + "VALUES (?,  ?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?, ?, ?,?);";
				try (PreparedStatement pstmt = cnxn.prepareStatement(sql);) {
					pstmt.setString(1, td.getProjectID());
					pstmt.setString(2, td.getSuiteName());
					pstmt.setString(3, td.getTestId());
					pstmt.setString(4, td.getTestDesc());
					pstmt.setString(5, td.getTestPlatformInfo());
					pstmt.setString(6, td.getComplexity());
					pstmt.setInt(7, tr.getRunId());

					SimpleDateFormat date_format = new SimpleDateFormat("YYYY-MM-dd");
					pstmt.setString(8, date_format.format(tr.getRunDateTime()).toString());

					pstmt.setString(9, tr.getStatus());
					pstmt.setString(10, tr.getRemarks());
					pstmt.setDouble(11, td.getExpectedTime());
					pstmt.setDouble(12, tr.getActualTime());
					pstmt.setString(13, tr.getScreenshot_ref());
					// System.out.println(pstmt.toString());
					pstmt.execute();

					int rowEffected = pstmt.getUpdateCount();
					if (rowEffected > 0) {
						logger.log(Level.INFO, "New record inserted successfully. Total row inserted# " + rowEffected);
					}
					logger.log(Level.FINEST, "--Total rows inserted:" + rowEffected);
					if (!pstmt.isClosed())
						pstmt.close();
				} // End try
				catch (SQLException e) {

					logger.log(Level.SEVERE, e.getMessage());
				} // End catch
			} // Connection is ready
			else {
				logger.log(Level.SEVERE, "Database switch is on but there is no Connection with database");
			}
		} // Database switch is on
	}// End updateTestResults()

	public static synchronized ResultSet selectResultsDB(String sql) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				// SELECT * FROM `TBL_RESULTS` WHERE 1
				try (PreparedStatement pstmt = cnxn.prepareStatement(sql);) {
					return pstmt.executeQuery();
				} catch (Exception ex) {
					logger.log(Level.SEVERE, "Error while fetching records-->" + ex.getMessage());
				}
			} // End if cnxn
		} // End is switch on
		else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
		return null;
	}// End selectResultsDB()

	public static synchronized int getLastRunID() {
		int id = 0;
		if (isSwitchOn()) {
			if (cnxn != null) {
				// SELECT * FROM `TBL_RESULTS` WHERE 1
				try (PreparedStatement pstmt = cnxn
						.prepareStatement("SELECT MAX(RUN_ID) AS HighestID FROM TBL_RESULTS");) {
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("HighestID");
						// System.out.println(rs.getInt("HighestID"));
					}
				} catch (Exception ex) {
					logger.log(Level.SEVERE, "Error while fetching records-->" + ex.getMessage());
				}
			} // End if cnxn
		} // End is switch on
		else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
		return id;
	}//End getLastRunID()

	public static synchronized void exportDataToCsv(String sql, String filename) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					if (!filename.toLowerCase().endsWith(".csv")) {
						throw new Exception("Invalid csv file type");
					}
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();

					OutputStreamWriter out;
					final CSVFormat format = CSVFormat.DEFAULT.withHeader(rs);
					final CSVPrinter printer = new CSVPrinter(new FileWriter(filename), format);
					printer.printRecords(rs);
					printer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
	}//End exportDataToCsv()

	public static void exportDataToExcel(String sql, String excelFilePath) {
		ReportFactory_DB.setExcelDestPath(excelFilePath);
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					if (ReportFactory_DB.getExcelTemplatePath().isEmpty()) {
						throw new IllegalArgumentException("Excel template is not specified");
					}
					if (ReportFactory_DB.getExcelDestPath().isEmpty()) {
						throw new IllegalArgumentException("Excel destination is not specified");
					}
					if (!(ReportFactory_DB.getExcelDestPath().toLowerCase().endsWith("xls")
							|| ReportFactory_DB.getExcelDestPath().toLowerCase().endsWith("xlsx"))) {
						throw new IllegalArgumentException("Correct Excel file not specified");
					}
					// Copy File for results
					File srcFile = new File(ReportFactory_DB.getExcelTemplatePath());
					File destFile = new File(ReportFactory_DB.getExcelDestPath());
					FileUtils.copyFile(srcFile, destFile);
					if (!FileUtils.getFile(destFile).exists()) {
						throw new IllegalArgumentException("Excel destination file not found...");
					}
					logger.log(Level.INFO, "Excel file is ready to write...");

					// Start Writing data to excel
					FileInputStream fis = null;
					Workbook wb = null;
					FileOutputStream fos = null;
					String sheetName;

					fis = new FileInputStream(destFile);
					wb = WorkbookFactory.create(fis);
					Sheet sheet = wb.getSheetAt(0);

					// styleFail.setFillPattern(CellStyle.ALIGN_FILL);
					int startRow;
					startRow = sheet.getLastRowNum();
					//System.out.println("Last Row is " + startRow);
					startRow++;
					// sheet.createRow(startRow).createCell(0).setCellValue(testData.getTestSet());
					Row row = null;
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					/*
					 * String sql = "INSERT INTO  `TBL_RESULTS` ( " +
					 * "`PROJECT_ID` , " + "`SUITE_NAME` ," + "`TEST_ID` , " +
					 * " `TEST_DESC` , " + " `TEST_ENVIRONMENT_INFO` ," +
					 * " `COMPLEXITY` ," + "`RUN_ID` , " + "`RUN_DATE_TIME` ," +
					 * "`STATUS` ,  " + "`REMARKS` ,  " + "`EXPECTED_TIME` ,  "
					 * + "`ACTUAL_TIME` ) " +
					 * "VALUES (?,  ?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?, ?, ?);";
					 */

					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					style.setFont(font);

					boolean firstRun = false;
					SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-YYYY");
					
					while (rs.next()) {
						if (sheet.getRow(startRow) == null)
							row = sheet.createRow(startRow);
						if (firstRun == false) {
							sheet.getRow(0).createCell(5).setCellStyle(style);
							sheet.getRow(0).getCell(5).setCellValue("Project Id - " + rs.getString("PROJECT_ID"));
							sheet.getRow(0).createCell(6).setCellStyle(style);
							sheet.getRow(0).getCell(6)
									.setCellValue("Test Env - " + rs.getString("TEST_ENVIRONMENT_INFO"));
							sheet.getRow(0).createCell(8).setCellStyle(style);
							sheet.getRow(0).getCell(8)
									.setCellValue("Dated - " + date_format.format(new Date()).toString());
							firstRun = true;
						}
						row.createCell(0).setCellValue(rs.getString("SUITE_NAME"));
						row.createCell(1).setCellValue(rs.getString("TEST_ID"));
						row.createCell(2).setCellValue(rs.getString("TEST_DESC"));

						DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat destFormat = new SimpleDateFormat("dd-MM-yyyy");

						row.createCell(3)
								.setCellValue(destFormat.format(sourceFormat.parse(rs.getString("RUN_DATE_TIME"))));
						row.createCell(4).setCellValue(rs.getString("COMPLEXITY"));
						row.createCell(5).setCellValue(rs.getString("STATUS"));
						row.createCell(6).setCellValue(rs.getDouble("EXPECTED_TIME"));
						row.createCell(7).setCellValue(rs.getDouble("ACTUAL_TIME"));
						row.createCell(8).setCellValue(rs.getString("REMARKS"));
						row.createCell(9).setCellValue(rs.getString("SCREENSHOT_REF"));

						// Move to next row
						startRow++;
					} // End while

					fos = new FileOutputStream(destFile);
					wb.write(fos);
					fos.flush();
					if (wb != null)
						wb.close();
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // if Cnxn not null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}
		} // End isSwitch

		System.out.println("DB is switched off...");
	}//End exportDataToExcel()

	public static synchronized void printDataToScreen(String sql) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();

					System.out.println("Column Numbers:" + columnsNumber);

					int i = 1;
					while (rs.next()) {
						System.out.format("\n---------------RECORD_ID# %s -------------------------+\n",
								rs.getString(1));
						System.out.println("PROJECT_ID   :" + rs.getString(2) + "\tSUITE_NAME :" + rs.getString(3));
						System.out.println("TEST_ID      :" + rs.getString(4) + "\t\tTEST_DESC  :" + rs.getString(5));
						System.out.println(
								"COMPLEXITY   :" + rs.getString(7) + "\t\tTEST_ENVIRONMENT_INFO : " + rs.getString(6));
						System.out
								.println("RUN_ID       :" + rs.getString(8) + "\t\tRUN_DATE_TIME : " + rs.getString(9));
						System.out
								.println("EXPECTED_TIME:" + rs.getString(12) + "\t\tACTUAL_TIME : " + rs.getString(13));
						System.out.println(
								"REMARKS      : " + rs.getString(11) + "\tSCREENSHOT_REF :" + rs.getString(14));
						System.out.format("+---------------STATUS: %s -------------------------+\n", rs.getString(10));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
	}

	public static void deleteRecordFromDB(String sql) {

		// String query = "delete from users where id = ?";
		if (isSwitchOn()) {
			if (cnxn != null) {
				try (PreparedStatement preparedStmt = cnxn.prepareStatement(sql);) {
					preparedStmt.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // cnxn not null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}

		} else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
	}//End deleteRecordFromDB()

	
	//This function is responsible to create a comparison report based on last three run
	public static void getComparisonReport(String projectId) {
		SetMultimap<String, String> testMap = HashMultimap.create();
		String query = "Select * from TBL_RESULTS where `PROJECT_ID`= '" + projectId + "' AND `RUN_ID`  In "
				+ "(SELECT `RUN_ID` FROM (Select RUN_ID from TBL_RESULTS Group By Run_ID order by RUN_ID desc Limit 0,3) as T )Order By RUN_ID desc";
		// Template file path
		String template = ConfigReader.getValue("ComparisonTemplatePath");
		
		//New Excel file path 
		String excelFilePath = Paths.get("").toAbsolutePath()
				+ "\\ExecutionReports\\DB\\Excel\\ComparisonTestResults.xls";
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					// Copy Template
					FileUtils.copyFile(FileUtils.getFile(template), FileUtils.getFile(excelFilePath));

					PreparedStatement pstmt = cnxn.prepareStatement(query);
					ResultSet rs = pstmt.executeQuery();

					Workbook workbook = null;
					FileInputStream fis = null;
					fis = new FileInputStream(excelFilePath);
					workbook = WorkbookFactory.create(fis);
					if (workbook.getSheetIndex(workbook.getSheet("TestResults")) == -1) {
						throw new Exception("No sheet found with name - TestResults");
					}

					Sheet sheet = workbook.getSheet("TestResults");
					Row hRow = sheet.getRow(0);
					if (hRow == null) {
						System.out.println("Header row is not present... ");
						hRow = sheet.createRow(0);
						hRow.createCell(0).setCellValue("ProjectID");
						hRow.createCell(1).setCellValue("TestID");
						hRow.createCell(2).setCellValue("Test_Desc");
						hRow.createCell(3).setCellValue("RunDate");
						hRow.createCell(4).setCellValue("Status");
						hRow.createCell(5).setCellValue("Run_ID");
					}

					SimpleDateFormat sourceDateFormat = new SimpleDateFormat("YYYY-MM-dd");
					SimpleDateFormat destDateFormat = new SimpleDateFormat("dd/MM/YYYY");

					String lastprojectId = "";
					while (rs.next()) {
						// Create Unique Test ID And Run ID
						testMap.put(rs.getString("RUN_ID"), rs.getString("TEST_ID"));
						Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

						if (!lastprojectId.equalsIgnoreCase(rs.getString("PROJECT_ID"))) {
							dataRow.createCell(0).setCellValue(rs.getString("PROJECT_ID"));
						}
						lastprojectId = rs.getString("PROJECT_ID");

						dataRow.createCell(1).setCellValue(rs.getString("TEST_ID"));
						dataRow.createCell(2).setCellValue(rs.getString("TEST_DESC"));

						String strDate = rs.getString("RUN_DATE_TIME").split(" ")[0].trim();
						DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate date = LocalDate.parse(strDate, fromFormatter);
						// Convert to dd-mm-yyyy
						strDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();

						dataRow.createCell(3).setCellValue(strDate);
						dataRow.createCell(4).setCellValue(rs.getString("STATUS"));
						dataRow.createCell(5).setCellValue(rs.getString("RUN_ID"));
					}
					// Write data to Excel file
					FileOutputStream fileOut = new FileOutputStream(excelFilePath);
					workbook.write(fileOut);
					fileOut.flush();
					workbook.close();
					fileOut.close();
					ReportFactory_DB.writeToExcelFile(excelFilePath, "TestComparisonSheet", testMap);
					System.out.println("Data to excel written successfully");
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Write Test ID to New Excel file
				// Write All TestID to Excel
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, "DB is switched off! ");
		}
	}// End getComparisonReport()

	@SuppressWarnings("resource")
	public static void writeToExcelFile(String destFilePath, String sheetName, SetMultimap<String, String> testMap) {
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fileOut = null;
		try {
			fis = new FileInputStream(destFilePath);
			workbook = WorkbookFactory.create(fis);
			fileOut = new FileOutputStream(destFilePath);
			// sheetName="TestCaseID";
			Sheet sheetComparison = null;
			if (sheetName.isEmpty())
				sheetComparison = workbook.createSheet();

			else if (workbook.getSheetIndex(workbook.getSheet(sheetName)) == -1)
				sheetComparison = workbook.createSheet(sheetName);
			else
				sheetComparison = workbook.getSheet(sheetName);

			// Create Header
			System.out.println("Last Row number-: " + sheetComparison.getLastRowNum());
			Row row = sheetComparison.getRow(2);
			if (row == null) {
				throw new Exception("Row not available!!! ");
			}

			Sheet sheetTestResult = workbook.getSheet("TestResults");
			int columnsStart = 2;
			Set<String> keysTestMap = testMap.keySet();
			int keysize = keysTestMap.size();
			//System.out.println("Key Size " + keysize);

			Map<String, Integer> columnIndexForUniqueRunId = new HashMap<String, Integer>();
			int idCount = 1;
			for (String key : keysTestMap) {
				// System.out.println("RunID -: " + key);
				row.getCell(columnsStart).setCellValue("Run" + idCount + "(" + key + ")");
				columnIndexForUniqueRunId.put(key, columnsStart);
				columnsStart++;
				idCount++;
			}
			// Write Test Ids
			// Set<String> testIds = new HashSet<String>();

			SortedSet<String> testIds = new TreeSet<String>();
			// Get Unique sorted Test
			for (String key : keysTestMap) {
				Stream<String> tid = testMap.get(key).stream();
				tid.forEach(id -> testIds.add(id));
			} // End for

			// Write Unique TestID to Comparison sheet
			for (String testId : testIds) {
				sheetComparison.createRow(sheetComparison.getLastRowNum() + 1).createCell(0).setCellValue(testId);
			}

			// Get Date based on the run id and Store it in the comparison sheet
			Map<Integer, String> dateFromRunId = new HashMap<Integer, String>();
			for (String key : keysTestMap) {
				int rowIndex = findRow(sheetTestResult, key);
				if (rowIndex != -1) {
					// Get Dates from
					dateFromRunId.put(rowIndex, sheetTestResult.getRow(rowIndex).getCell(3).getStringCellValue());
					// Set Dates
					sheetComparison.getRow(3).getCell(columnIndexForUniqueRunId.get(key))
							.setCellValue(dateFromRunId.get(rowIndex));
				}
			}

			// Get descriptions and store in comparison sheet
			Map<String, String> descFromTestId = new HashMap<String, String>();
			for (String testId : testIds) {
				int rowIndex = findRow(sheetTestResult, testId);

				if (rowIndex != -1) {
					// Get Descrption
					String desc = sheetTestResult.getRow(rowIndex).getCell(2).getStringCellValue();
					// Find Row in Test Comparison and Store Desc
					int rowIndexComp = findRow(sheetComparison, testId);
					if (rowIndexComp != -1) {

						Row r = sheetComparison.getRow(rowIndexComp);
						if (r == null)
							r = sheetComparison.createRow(rowIndexComp);
						r.createCell(1).setCellValue(desc);
					}
				}
			}
			int lastRowOfComShet = sheetComparison.getLastRowNum();
			for (int rowIndex = 4; rowIndex <= lastRowOfComShet; rowIndex++) {
				Row r = sheetComparison.getRow(rowIndex);
				System.out.println("Row Number " + rowIndex);
				for (int CellIndex = 2; CellIndex <= 4; CellIndex++) {
					if (r.getCell(CellIndex) == null) {
						r.createCell(CellIndex).setCellValue("NA");
					}
				}
			}
			// Get all the information for the test
			Multimap<String, List<String>> multimapRecords = ArrayListMultimap.create();
			for (String key : keysTestMap) {
				List<Integer> rowIndexs = findRows(sheetTestResult, key);
				// System.out.println("Run ID " + key + " has " +
				// rowIndexs.size() + " records");
				for (int idx : rowIndexs) {
					List<String> record = new ArrayList<>();
					// Get TestID
					String testID = sheetTestResult.getRow(idx).getCell(1).getStringCellValue();
					record.add(testID);

					// Get TestDesc
					String testDesc = sheetTestResult.getRow(idx).getCell(2).getStringCellValue();
					record.add(testDesc);
				
					// Get TestStatus
					String testStatus = sheetTestResult.getRow(idx).getCell(4).getStringCellValue();

					record.add(testStatus);
					multimapRecords.put(key, record);
				}//End for loop inner
			}//End for loop outer

			// Write records for further processing
			workbook.write(fileOut);
			fileOut.flush();
			workbook.close();
			fileOut.close();
			fis.close();

			// Open Updated File again
			fis = new FileInputStream(destFilePath);
			workbook = WorkbookFactory.create(fis);
			fileOut = new FileOutputStream(destFilePath);
			sheetTestResult = workbook.getSheet("TestResults");
			sheetComparison = workbook.getSheet(sheetName);

			// Get a TestID's based on RunID from TestResult sheet
			for (String key : keysTestMap) {
				// System.out.println("RunID -: " + key);
				Iterator rowItIndex = findRows(sheetTestResult, key).iterator();
				while (rowItIndex.hasNext()) {
					// Get status from of test based on the current run id
					int rowIndex = (int) rowItIndex.next();
					String status = sheetTestResult.getRow(rowIndex).getCell(4).getStringCellValue();
					String tId = sheetTestResult.getRow(rowIndex).getCell(1).getStringCellValue();
					int rowIndexComp = findRow(sheetComparison, tId);
					// System.out.println("Current Run Id is " + key + " has
					// Test id " + tId + " with status " + status);
					// Set status for the test id in comparison sheet for the
					// current run id
					if (rowIndexComp != -1) {
						Row r = sheetComparison.getRow(rowIndexComp);
						if (r == null)
							r = sheetComparison.createRow(rowIndexComp);
						// Cell Index for current run id
						int cellIndex = columnIndexForUniqueRunId.get(key);
						r.createCell(cellIndex).setCellValue(status);
					}
				}
			}
			if (keysize < 3) {
				row.getCell(4).setCellValue("Run3(NA)");
				sheetComparison.getRow(3).getCell(4).setCellValue("NA");
			}
			if (keysize < 2) {
				row.getCell(3).setCellValue("Run2(NA)");
				sheetComparison.getRow(3).getCell(3).setCellValue("NA");
			}
			if (keysize < 1) {
				row.getCell(2).setCellValue("Run1(NA)");
				sheetComparison.getRow(3).getCell(2).setCellValue("NA");
			}
			// Write Records
			workbook.write(fileOut);
			fileOut.flush();
			workbook.close();
			fileOut.close();
			System.out.println("Comparison sheet written successfully");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}//End writeToExcelFile()

	private static int findRow(Sheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						return row.getRowNum();
					}
				}
			}
		}
		return -1;
	}//End findRow()

	private static List<Integer> findRows(Sheet sheet, String cellContent) {
		List<Integer> rows = new ArrayList<>();
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						rows.add(row.getRowNum());
					}
				}
			}
		}
		return rows;
	}//End findRows()
	
	//Close database connection
	public static void closeDBConnection() {
		try {
			if (cnxn != null) {
				cnxn.close();
				dbSwitch = Switch.OFF;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}//End closeDBConnection()

}//End of class
