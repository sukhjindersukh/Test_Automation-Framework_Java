package FrameworkUnitTests;

import java.nio.file.Paths;

import Utilities.ConfigReader;
import Utilities.ReportFactory_DB;

public class DBTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String query = "SELECT * FROM `TBL_RESULTS` WHERE 1 Order By RUN_DATE_TIME DESC";
		ReportFactory_DB.switchOn();
		ReportFactory_DB.initSqlite(System.getProperty("user.dir") + "\\ExecutionReports\\DB");

		ReportFactory_DB.setExcelTemplatePath(ConfigReader.getValue("Template_testResultExcelPath"));

		System.out.println(ReportFactory_DB.getLastRunID());

		
		String excelDestPath= Paths.get("").toAbsolutePath()+"\\ExecutionReports\\DB\\Excel\\TestResults.xls";
		ReportFactory_DB.exportDataToExcel(query, excelDestPath);

		
		String csvDestPath= Paths.get("").toAbsolutePath()+"\\ExecutionReports\\DB\\CSV\\TestResults.csv";
		 ReportFactory_DB.exportDataToCsv(query, csvDestPath);

		ReportFactory_DB.closeDBConnection();
	}

}
