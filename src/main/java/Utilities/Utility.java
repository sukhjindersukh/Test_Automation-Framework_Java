package Utilities;

//======  START A BLOCK OF RELATED FUNCTIONALITIES
// ~~~~~  END A BLOCK OF RELATED FUNCTIONALITIES

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

public class Utility {
	public static String testCaseID = "";
	public static WebDriver driver = null;
	public static boolean isRun;
	public static String suiteName = Utility.getValue("suiteName");
	public static String logStep = "";
	public static int cellNumber = 0;
	public static boolean colFlag;
	

	// To create Zip
	public static String result_FolderName = System.getProperty("user.dir") + "\\ExecutionReports\\ExecutionReports";

	/*
	 * Property file handling functionalities
	 * ===========================================================
	 */
	public static Properties loadPropertyFile(String filePath) { 
		File file = new File(filePath);
		Properties prop = new Properties();
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static int getIntValue(String key) {
		Properties prop = loadPropertyFile("src\\main\\resources\\ConfigFiles\\config.properties");
		String strKey = prop.getProperty(key);
		return Integer.parseInt(strKey);
	}

	public static String getValue(String key) {
		Properties prop = loadPropertyFile("src\\main\\resources\\ConfigFiles\\config.properties");
		String strKey = prop.getProperty(key);
		return strKey;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	
	/*
	 * Initialize test case
	 * ===========================================================
	 */
	public static void initTest(String testCaseID) throws Throwable {
		// Set Test browser name for reporting
		//GlobalUtil.testData.setTestPlatformInfo((GlobalUtil.currentBrowser));
		// Initialize HTML Report
		HtmlReportUtil.startReport(GlobalUtil.currentSuiteName + "-" + testCaseID + "-" + GlobalUtil.currentBrowser,
				GlobalUtil.testData.getTestDesc(), GlobalUtil.currentSuiteName);

		// Check Y/N for this test from excel file based on suiteID
		// if (!ExcelDataUtil.getControls(GlobalUtil.testConfig.getSuiteId(),
		// testCaseID))
		// throw new SkipException("Skipping test - " + testCaseID);
	}// End initTest()

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/*
	 * Log Steps for report
	 * ===========================================================
	 */
	public static void logStep(String logStep) {
		LogUtil.infoLog(Utility.class, logStep);
		HtmlReportUtil.stepInfo(logStep);
	}

	public static void logResult(boolean status, String logStep) {
		if (status) {
			LogUtil.infoLog(Utility.class, logStep + "-PASS ");
			HtmlReportUtil.stepPass(logStep);
		} else {
			LogUtil.infoLog(Utility.class, logStep + "-FAIL ");
			HtmlReportUtil.stepFail(logStep);

		}

	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * Common functions for date time and file IO
	 * ===========================================================
	 */
	public static String getDateTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateOfExecution = dateFormat.format(date);
		return dateOfExecution;
	}

	public static void renameFile() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		String timeStamp = dateFormat.format(date);
		try {
			File oldFile = new File(System.getProperty("user.dir") + Utility.getValue("testResultExcelPath"));
			String newFilePath = oldFile.getAbsolutePath().replace(oldFile.getName(), "") + "\\ReportHistory\\"
					+ timeStamp + "-TestResult.xls";
			File newFile = new File(newFilePath);

			FileUtils.copyFile(oldFile, newFile);
			System.out.println("History File successfully created... ");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkFileOpen() {
		String fileName = System.getProperty("user.dir") + "\\ExecutionReports\\ExcelReport\\TestResult.xls";
		File file = new File(fileName);
		File sameFileName = new File(fileName);

		if (file.renameTo(sameFileName)) {

			LogUtil.infoLog(testCaseID, "**********TestResult.xls is closed**********");
		} else {
			JOptionPane.showMessageDialog(null, "TestResult.xls is opened");
			Thread.currentThread().stop();
		}
	}

	public static String createZipFile() throws IOException {
		result_FolderName = result_FolderName.replace("\\", "/");
		String outputFile = result_FolderName + ".zip";
		FileOutputStream fos = new FileOutputStream(outputFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		packCurrentDirectoryContents(result_FolderName, zos);
		zos.closeEntry();
		zos.close();
		fos.close();
		return outputFile;
	}

	public static void packCurrentDirectoryContents(String directoryPath, ZipOutputStream zos) throws IOException {
		for (String dirElement : new File(directoryPath).list()) {
			String dirElementPath = directoryPath + "/" + dirElement;
			if (new File(dirElementPath).isDirectory()) {
				packCurrentDirectoryContents(dirElementPath, zos);
			} else {
				ZipEntry ze = new ZipEntry(dirElementPath.replaceAll(result_FolderName + "/", ""));
				zos.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(dirElementPath);
				byte[] bytesRead = new byte[512];
				int bytesNum;
				while ((bytesNum = fis.read(bytesRead)) > 0) {
					zos.write(bytesRead, 0, bytesNum);
				}

				fis.close();
			}
		}
	}

	public static void delDirectory(File dir) {
		File[] currList;
		Stack<File> stack = new Stack<File>();
		stack.push(dir);
		while (!stack.isEmpty()) {
			if (stack.lastElement().isDirectory()) {
				currList = stack.lastElement().listFiles();
				if (currList.length > 0) {
					for (File curr : currList) {
						stack.push(curr);
					}
				} else {
					stack.pop().delete();
				}
			} else {
				stack.pop().delete();
			}
		}
		if (new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports.zip").exists()) {
			delDirectory(new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports.zip"));
		}
	}
	
	public static String takeScreenshot(WebDriver driver, String testCaseID) throws IOException {
		String path=ConfigReader.getValue("screenshotPath")+"\\"+testCaseID+".jpg";
		Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(100)).takeScreenshot(driver);
		File src = new File(System.getProperty("user.dir")+"\\ExecutionReports\\HtmlReport\\"+path);
		ImageIO.write(screenshot.getImage(), "PNG", src);
		return src.getPath();
	}
	
	
	
}//End of class
