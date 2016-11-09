package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import TestingXperts.smartshow.pages.ShowingPage;

public class KeywordUtil extends Utility {
	private static int DEFAULT_WAIT_SECONDS = 30;
	protected static int fail = 0;
	static WebElement webElement;
	protected static String url = "";
	private static String userDir = "user.dir";
	public static String logging_step;

	/*
	 * Web driver waiter
	 * ===========================================================
	 */

	public static WebElement waitForClickable(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_SECONDS);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement waitForPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_SECONDS);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static WebElement waitForVisibile(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_SECONDS);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement findWithWait(By locator, int seconds) throws Exception {
		WebElement element = null;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(seconds, TimeUnit.SECONDS)
					.pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class);

			element = fluentWait.until(

					ExpectedConditions.visibilityOfElementLocated(locator)

			);
			return element;

		} catch (Exception e) {

			throw new Exception("Timeout reached when searching for element! Time: " + seconds + " seconds " + "\n"
					+ e.getMessage());

		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * Web driver common functions
	 * ===========================================================
	 */

	public static boolean click(By locator) {
		WebElement elm = waitForClickable(locator);
		if (elm == null) {
			return false;
		} else {
			elm.click();
			return true;
		}
	}
	
	public static boolean clickLink(String linkText) {
		WebElement elm = waitForClickable(By.linkText(linkText));
		if (elm == null) {
			return false;
		} else {
			elm.click();
			return true;
		}
	}
	
	public static String getElementText(By locator){
		WebElement elm = waitForVisibile(locator);
		String text=""; 		
		if(elm.getAttribute("type").equalsIgnoreCase("text")){
			text=elm.getAttribute("value");
		}else{
			text=elm.getText();
		}
		return text.trim();
	}

	public static boolean isWebElementVisible(By locator) {
		WebElement elm = waitForVisibile(locator);
		return elm.isDisplayed();
	}

	public static boolean isWebElementPresent(By locator) {
		List<WebElement> elements = (new WebDriverWait(driver, DEFAULT_WAIT_SECONDS))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		if (!elements.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isWebElementNotPresent(By locator) {
		List<WebElement> elements = (new WebDriverWait(driver, DEFAULT_WAIT_SECONDS))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		if (elements.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean inputText(By locator, String data) {
		WebElement elm = waitForVisibile(locator);
		if (elm == null) {
			return false;
		} else {
			elm.clear();
			elm.sendKeys(data);
			return true;
		}
	}

	public static boolean inputTextJS(By locator, String data) {
		WebElement element = waitForVisibile(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", element, data);
		if (element.getText().equalsIgnoreCase(data)) {
			return true;
		} else
			return false;
	}
	
	public static boolean isRadioSelected(By locator) {
		WebElement element = waitForVisibile(locator);
		return element.isSelected();
	}

	public static boolean isRadioNotSelected(By locator) {
		boolean check = isRadioSelected(locator);
		return (!check);
	}

	public static boolean clearInput(By locator) {
		WebElement element = waitForVisibile(locator);
		element.clear();

		element = waitForVisibile(locator);
		// Check whether it is cleared or not
		return element.getAttribute("value").isEmpty();
	}

	public boolean verifyCssProperty(By locator, String data) {

		String[] property = data.split(":", 2);
		String expProp = property[0];
		String expValue = property[1];
		boolean flag = false;
		String prop = (waitForPresent(locator)).getCssValue(expProp);
		if (prop.trim().equals(expValue.trim())) {
			flag = true;
			return flag;
		} else {
			return flag;
		}
	}
	
	public static boolean verifyInputText(By locator, String data) {
		WebElement element = waitForVisibile(locator);
		return element.getAttribute("value").equalsIgnoreCase(data);

	}


	public static boolean verifyInputText_JS(By locator, String data) {
		WebElement element = waitForVisibile(locator);
		
		String actual_text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", element);
		// String actual_text=element.getAttribute("value");
		return data.equalsIgnoreCase(actual_text);
	}
	
	public static boolean verifyText(By locator, String data) {
		WebElement element = waitForVisibile(locator);
		String message = new String(String.format("Verified text expected \"%s\" actual \"%s\" ",data, element.getText()));
		LogUtil.infoLog(Utility.class, message);
		
		message = new String(String.format("Verified text expected <b>%s</b> actual <b>%s</b> ",data, element.getText()));
		HtmlReportUtil.stepInfo(message);
		
		return element.getText().equalsIgnoreCase(data);
		// String actual_text=element.getAttribute("value");
	}
	
	public static boolean verifyDisplayAndEnable(By locator) {
		WebElement element = waitForVisibile(locator);
		return element.isDisplayed() && element.isEnabled();
		// String actual_text=element.getAttribute("value");
	}
	
	//String actual = driver.findElement(By.cssSelector(".page-heading")).getText().trim();
	

	public static boolean click_JS(By locator, String data) {
		WebElement element = waitForVisibile(locator);
		String actual_text = (String) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		// String actual_text=element.getAttribute("value");
		return data.equalsIgnoreCase(actual_text);

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/*
	 * Handling selects
	 * ===========================================================
	 */

	public static boolean selectByIndex(By locator, int index) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByIndex(index);

		// Check whether element is selected or not
		sel = new Select(driver.findElement(locator));
		if (sel.getFirstSelectedOption().isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean selectByValue(By locator, String value) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByValue(value);

		// Check whether element is selected or not
		sel = new Select(driver.findElement(locator));
		if (sel.getFirstSelectedOption().isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean selectByVisibleText(By locator, String value) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(value);

		// Check whether element is selected or not
		sel = new Select(driver.findElement(locator));
		if (sel.getFirstSelectedOption().getText().equalsIgnoreCase(value)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean verifyAllValuesOfDropDown(By locator, String data) throws Throwable {
		boolean flag = false;
		WebElement element = findWithWait(locator, DEFAULT_WAIT_SECONDS);
		List<WebElement> options = element.findElements(By.tagName("option"));
		String[] allElements = data.split(",");
		String actual;
		for (int i = 0; i < allElements.length; i++) {
			LogUtil.infoLog(KeywordUtil.class, options.get(i).getText());
			LogUtil.infoLog(KeywordUtil.class, allElements[i].trim());

			actual = options.get(i).getText().trim();
			if (actual.equalsIgnoreCase(allElements[i].trim())) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}
	public boolean verifyDropdownSelectedValue(By locator, String data) {
		Select sel = new Select(waitForVisibile(locator));
		String defSelectedVal = sel.getFirstSelectedOption().getText();
		return defSelectedVal.trim().equals(data.trim());
	}

	public static boolean verifyElementSize(By locator, int size) {
		List<WebElement> elements = driver.findElements(locator);
		if (elements.size() == size) {
			LogUtil.infoLog(KeywordUtil.class, "Element is Present " + size + "times");
			return true;
		} else {
			LogUtil.infoLog(KeywordUtil.class, "Element is not Present with required size");
			LogUtil.infoLog(KeywordUtil.class, "Expected size:" + size + " but actual size: " + elements.size());
			return false;
		}
	}

	public static boolean writeInInputCharByChar(By locator, String data) throws InterruptedException {
		WebElement element = waitForVisibile(locator);
		element.clear();
		String[] b = data.split("");
		for (int i = 0; i < b.length; i++) {
			element.sendKeys(b[i]);
			Thread.sleep(500);
		}
		return true;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static void executeStep(Boolean check, Class className, String logstep) throws Exception {
		logging_step = logstep;
		if (check) {
			HtmlReportUtil.stepInfo(logstep + " - Pass");
		} else {
			LogUtil.infoLog(className, logstep + " - Fail");
			HtmlReportUtil.stepInfo(logstep + " - Fail");
			throw new Exception("Step failed - " +logstep);
		}
	}
	
	public static void checkStep(Boolean check, Class className, String logstep) throws Exception {
		logging_step = logstep;
		if (check) {
			LogUtil.infoLog(className, logstep + " - Pass ");
			HtmlReportUtil.stepPass(logstep);
		} else {
			LogUtil.infoLog(className, logstep + " - Fail ");
			HtmlReportUtil.stepFail(logstep);
			throw new Exception("Test step has failed-->" + logstep);
		}
	}
	

	public static void delay(long time) throws InterruptedException {
		Thread.sleep(time);
	}
	public boolean verifyCurrentDateInput(By locator) {
		boolean flag = false;
		WebElement element = waitForVisibile(locator);
		String actual = element.getAttribute("value").trim();
		DateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		dtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
		String expected = dtFormat.format(date).trim();
		if (actual.trim().contains(expected)) {
			flag = true;

		}
		return flag;
	}

	public static boolean uploadFilesUsingSendKeys(By locator, String data) throws InterruptedException {
		WebElement element = waitForVisibile(locator);
		element.clear();
		element.sendKeys(System.getProperty(userDir) + "\\src\\test\\resources\\uploadFiles\\" + data);
		return true;
	}

	public static boolean verifyPDFData(String data, int page, String fileName) throws IOException {
		FileInputStream fis = null;
		String dwnFile = null;
		try {

			String dirName = System.getProperty(userDir) + "\\src\\test\\resources\\downloadFile\\";
			File dir = new File(dirName);
			File[] path1 = dir.listFiles();

			for (int k = 0; k < path1.length; k++) {
				dwnFile = path1[k].toString();
				if (dwnFile.contains(fileName)) {
					break;
				}

				continue;
			}
			File file = new File(dwnFile);
			fis = new FileInputStream(file.getAbsolutePath());
			PdfReader text = new PdfReader(fis);
			String expected = PdfTextExtractor.getTextFromPage(text, page);

			String[] b = data.split(",");
			fis.close();
			for (int i = 0; i < b.length; i++) {
				if (expected.contains(b[i]))
					continue;
			}
		} catch (Exception e) {
			LogUtil.errorLog(KeywordUtil.class, e.getMessage(), e);
		}
		return true;
	}

	public boolean delDirectory() {
		File delDestination = new File(System.getProperty(userDir) + "\\src\\test\\resources\\downloadFile");
		if (delDestination.exists()) {
			File[] files = delDestination.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delDirectory();
				} else {
					files[i].delete();
				}
			}
		}
		return delDestination.delete();
	}
	
}
