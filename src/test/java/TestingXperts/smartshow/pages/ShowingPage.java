package TestingXperts.smartshow.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sqlite.date.DateFormatUtils;


import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.LogUtil;

/**
 * Sample page
 */
public class ShowingPage {

	public static boolean checkMineUI(WebDriver driver) {

		return driver.findElement(By.cssSelector("button.mine.btn")).isDisplayed()
				&& driver.findElement(By.cssSelector("button.mine.btn")).isEnabled();
	}

	public static void clickMineUI(WebDriver driver) {

		if (checkMineUI(driver)) {
			driver.findElement(By.cssSelector("button.mine.btn")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Mine UI Button ");
			HtmlReportUtil.stepInfo("Clicked Mine UI Button");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Mine UI Button ");
			HtmlReportUtil.stepInfo("Error! Not Clicked Mine UI Button");

		}

	}

	public static boolean chekMineScheduledList(WebDriver driver) throws Exception {

		clickMineUI(driver);

		Thread.sleep(2000);
		boolean status = true;
		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {

				if (agent.getText().contains((GlobalUtil.currentUserFullName))) {

				} else {
					status = false;
					break;
				}
			}

		}

		return status;
	}

	public static boolean chekMineCompletedList(WebDriver driver) throws Exception {

		clickMineUI(driver);

		Thread.sleep(2000);
		boolean status = true;
		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath(" //*[@class='list-group completed-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {

				if (agent.getText().contains((GlobalUtil.currentUserFullName))) {

				} else {
					status = false;
					break;
				}
			}

		}

		return status;
	}

	public static boolean checkTeamUI(WebDriver driver) {

		return driver.findElement(By.cssSelector("button.team.btn.btn-default")).isDisplayed()
				&& driver.findElement(By.cssSelector("button.team.btn.btn-default")).isEnabled();
	}

	public static void clickTeamUI(WebDriver driver) {
		if (checkTeamUI(driver)) {
			driver.findElement(By.cssSelector("button.team.btn.btn-default")).click();

		}
	}

	public static boolean checkInviteLink(WebDriver driver) {

		boolean status = false;
		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			status = driver
					.findElement(By
							.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default invite textual']"))
					.isDisplayed();
		}
		return status;
	}

	public static void clickInviteLink(WebDriver driver) {

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			driver.findElement(By
					.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default invite textual']"))
					.click();

			LogUtil.infoLog(ShowingPage.class, "Clicked Invite Link for Scheduled");
			HtmlReportUtil.stepInfo("Clicked Invite Link for Scheduled");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Invite Link for Scheduled");
			HtmlReportUtil.stepInfo("Error! Not Clicked Invite Link for Scheduled");

		}

	}

	public static void clickResendInviteLink(WebDriver driver) {

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			List<WebElement> resend = driver.findElements(By.xpath("//*[text() [contains(.,'Resend invite') ] ]"));
			resend.get(0).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Resend Invite Link for Scheduled");
			HtmlReportUtil.stepInfo("Clicked Resend Link for Scheduled");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Resend Invite Link for Scheduled");
			HtmlReportUtil.stepInfo("Error! Not Clicked Resend Link for Scheduled");

		}

	}

	public static boolean checkResendInviteMessage(WebDriver driver) {
		String message = "Success: Resent showing invitation to Test Client";
		String actual = "";
		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			List<WebElement> resend = driver.findElements(By.xpath(".//*[@id='alerts']/div/div"));

			for (WebElement msg : resend) {
				if (msg.getText().contains(message)) {
					actual = msg.getText().trim();
					break;
				}
			}

			LogUtil.infoLog(ShowingPage.class, "Expected:" + message);
			HtmlReportUtil.stepInfo("Expected:" + message);

			LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
			HtmlReportUtil.stepInfo("Actual:" + actual);

		}

		return actual.contains(message);

	}

	public static boolean chekTeamScheduledList(WebDriver driver) throws Exception {

		clickTeamUI(driver);

		Thread.sleep(2000);
		boolean status = true;
		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {

				if (agent.getText().contains(("TEAM"))) {

				} else {
					status = false;
					break;
				}
			}

		}

		return status;
	}

	public static boolean chekTeamCompletedList(WebDriver driver) throws Exception {

		clickTeamUI(driver);

		Thread.sleep(2000);
		boolean status = true;
		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath("//*[@class='list-group completed-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {

				if (agent.getText().contains(("TEAM"))) {

				} else {
					status = false;
					break;
				}
			}

		}

		return status;
	}

	public static boolean checkAllUI(WebDriver driver) {

		return driver.findElement(By.cssSelector("button.all.btn.btn-default")).isDisplayed()
				&& driver.findElement(By.cssSelector("button.all.btn.btn-default")).isEnabled();
	}

	public static void clickAllUI(WebDriver driver) {
		if (checkAllUI(driver))
			driver.findElement(By.cssSelector("button.all.btn.btn-default")).click();
	}

	public static boolean chekAllScheduledList(WebDriver driver) throws Exception {

		clickAllUI(driver);

		HashSet<String> setAgents = new HashSet();

		Thread.sleep(2000);

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {
				setAgents.add(agent.getText());

			}

		}

		LogUtil.infoLog(ShowingPage.class, "Agents:" + setAgents);
		HtmlReportUtil.stepInfo("Agents:" + setAgents);
		return setAgents.size() > 1;

	}

	public static boolean chekAllCompletedList(WebDriver driver) throws Exception {

		clickAllUI(driver);

		HashSet<String> setAgents = new HashSet();

		Thread.sleep(2000);
		boolean status = true;
		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 0) {
			// List<WebElement> mineScheduledRecords
			// =driver.findElements(By.xpath("//*[@class='list-group
			// scheduled-showings']/li"));

			List<WebElement> agentNames = driver
					.findElements(By.xpath("//*[@class='list-group completed-showings']/li//div/div[2]/small[3]"));
			for (WebElement agent : agentNames) {
				setAgents.add(agent.getText());

			}

		}

		LogUtil.infoLog(ShowingPage.class, "Agents:" + setAgents);
		HtmlReportUtil.stepInfo("Agents:" + setAgents);
		return setAgents.size() > 1;

	}

	public static boolean checkViewDeletedUI(WebDriver driver) {

		return driver.findElement(By.cssSelector("button[class='trash btn btn-default']")).isDisplayed()
				&& driver.findElement(By.cssSelector("button[class='trash btn btn-default']")).isEnabled();
	}

	public static void clickViewDeletedUI(WebDriver driver) {

		if (checkViewDeletedUI(driver)) {
			driver.findElement(By.cssSelector("button[class='trash btn btn-default']")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked View Deleted UI");
			HtmlReportUtil.stepInfo("Clicked View Deleted UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked View Deleted UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked View Deleted UI");
		}

	}

	public static boolean checkInviteUI_ScheduledList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Invite Link for Scheduled");
		HtmlReportUtil.stepInfo("Checking Invite Link for Scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {

			if (driver
					.findElement(By
							.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default invite textual']"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default invite textual']"))
							.isEnabled()) {

			} else {
				status = false;
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in scheduled!");

			HtmlReportUtil.stepInfo("There is no record in scheduled!");
		}

		return status;
	}

	public static boolean checkShowingUI_ScheduledList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Showing Link for Scheduled");
		HtmlReportUtil.stepInfo("Checking Showing Link for Scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			if (driver
					.findElement(By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[2]"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[2]"))
							.isEnabled()) {

			} else {
				status = false;
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in scheduled!");

			HtmlReportUtil.stepInfo("There is no record in scheduled!");
		}

		return status;
	}

	public static boolean checkShowQRUI_ScheduledList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Show QR Link for Scheduled");
		HtmlReportUtil.stepInfo("Checking Show QR Link for Scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			if (driver
					.findElement(By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[1]"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[1]"))
							.isEnabled()) {

			} else {
				status = false;
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in scheduled!");

			HtmlReportUtil.stepInfo("There is no record in scheduled!");
		}

		return status;
	}

	public static void capatureScheduledFirstRecord(WebDriver driver) {

		String value = "";
		String strXpath = "";

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// Property Address
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[1]/h3";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[1]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[2]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[3]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAgentKey, value);

			// Note
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div[2]/div/div/small";

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			if (driver.findElements(By.xpath(strXpath)).size() > 0) {

				value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase().toUpperCase();
				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, value);
			} else {
				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

			}

			LogUtil.infoLog(ShowingPage.class, "Captured first record from scheduled list");
			HtmlReportUtil.stepInfo("Captured first record from scheduled list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Captured first record from scheduled list");
			HtmlReportUtil.stepInfo("Error! Not Captured first record from scheduled list");

		}
		// Reset Timeout
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void capatureRestore_ScheduledFirstRecord(WebDriver driver) {

		String value = "";
		String strXpath = "";

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// Property Address
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[1]/h3";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[1]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[2]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div/div[2]/small[3]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyAgentKey, value);

			// Note
			strXpath = "//*[@class='list-group scheduled-showings']/li[1]/div[2]/div/div/small";

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			if (driver.findElements(By.xpath(strXpath)).size() > 0) {

				value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase().toUpperCase();
				GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyNoteKey, value);
			} else {
				GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

			}

			LogUtil.infoLog(ShowingPage.class, "Captured first record from Restore scheduled list");
			HtmlReportUtil.stepInfo("Captured first record from Restore scheduled list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Captured first record from scheduled list");
			HtmlReportUtil.stepInfo("Error! Not Captured first record from scheduled list");

		}
		// Reset Timeout
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void capatureRestore_CompletedFirstRecord(WebDriver driver) {

		String value = "";
		String strXpath = "";

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			// Property Address
			strXpath = "//*[@class='list-group completed-showings']/li[1]/div/div[1]/h3";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "//*[@class='list-group completed-showings']/li[1]/div/div[2]/small[1]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "//*[@class='list-group completed-showings']/li[1]/div/div[2]/small[2]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			value = value.replace("CLIENT:", "").trim();
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "//*[@class='list-group completed-showings']/li[1]/div/div[2]/small[3]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			
			value = value.replace("AGENT:", "").trim();
			
			GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyAgentKey, value);

			// Note
			strXpath = "//*[@class='list-group completed-showings']/li[1]/div[2]/div/div/small";

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			if (driver.findElements(By.xpath(strXpath)).size() > 0) {

				value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase().toUpperCase();
				value = value.replace("NOTE:", "").trim();
				GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyNoteKey, value);
			} else {
				GlobalUtil.propertyRestoredRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

			}

			LogUtil.infoLog(ShowingPage.class, "Captured first record from Restore completed list");
			HtmlReportUtil.stepInfo("Captured first record from Restore completed list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Captured first record from completed list");
			HtmlReportUtil.stepInfo("Error! Not Captured first record from completed list");

		}
		// Reset Timeout
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void capatureScheduledRecordOneByOne(WebDriver driver, int i) {

		String value = "";
		String strXpath = "";
		GlobalUtil.propertyCurrentRecord.clear();

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			// Property Address
			strXpath = "//*[@class='list-group scheduled-showings']/li[" + i + "]/div/div[1]/h3";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "//*[@class='list-group scheduled-showings']/li[" + i + "]/div/div[2]/small[1]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "//*[@class='list-group scheduled-showings']/li[" + i + "]/div/div[2]/small[2]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "//*[@class='list-group scheduled-showings']/li[" + i + "]/div/div[2]/small[3]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAgentKey, value);

			// Note
			strXpath = "//*[@class='list-group scheduled-showings']/li[" + i + "]/div[2]/div/div/small";

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			if (driver.findElements(By.xpath(strXpath)).size() > 0) {

				value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();

				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, value);
			} else {
				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

			}

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Captured record from scheduled list");
			HtmlReportUtil.stepInfo("Error! Not Captured record from scheduled list");

		}
		// Reset Timeout
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void capatureCompletedRecordOneByOne(WebDriver driver, int i) {

		String value = "";
		String strXpath = "";
		GlobalUtil.propertyCurrentRecord.clear();

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			// Property Address
			strXpath = "//*[@class='list-group completed-showings']/li[" + i + "]/div/div[1]/h3";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "//*[@class='list-group completed-showings']/li[" + i + "]/div/div[2]/small[1]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "//*[@class='list-group completed-showings']/li[" + i + "]/div/div[2]/small[2]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			value = value.replace("CLIENT:", "").trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "//*[@class='list-group completed-showings']/li[" + i + "]/div/div[2]/small[3]";
			value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			value = value.replace("AGENT:", "").trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAgentKey, value);

			// Note
			strXpath = "//*[@class='list-group completed-showings']/li[" + i + "]/div[2]/div/div/small";

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			if (driver.findElements(By.xpath(strXpath)).size() > 0) {

				value = driver.findElement(By.xpath(strXpath)).getText().trim().toUpperCase().toUpperCase();
				value = value.replace("NOTE:", "").trim();
				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, value);
			} else {
				GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

			}

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Captured record from scheduled list");
			HtmlReportUtil.stepInfo("Error! Not Captured record from scheduled list");

		}
		// Reset Timeout
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static void capatureCompletedFirstRecord(WebDriver driver) {

		String value = "";
		String strXpath = "";

		LogUtil.infoLog(ShowingPage.class, "Capturing first record from completed list");
		HtmlReportUtil.stepInfo("Capturing first record from completed list");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			List<WebElement> completedRecords = driver
					.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

			// Address
			strXpath = "div/div/h3";
			value = completedRecords.get(0).findElement(By.xpath(strXpath)).getText().trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAddressKey, value);

			// DateTime
			strXpath = "div/div[2]/small[1]";
			value = completedRecords.get(0).findElement(By.xpath(strXpath)).getText().trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyDateTimeKey, value);

			// Client
			strXpath = "div/div[2]/small[2]";
			value = completedRecords.get(0).findElement(By.xpath(strXpath)).getText().trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyClientKey, value);

			// Agent

			strXpath = "div/div[2]/small[3]";
			value = completedRecords.get(0).findElement(By.xpath(strXpath)).getText().trim();
			GlobalUtil.propertyCurrentRecord.put(GlobalUtil.propertyAgentKey, value);

			LogUtil.infoLog(ShowingPage.class, "Captured First record from completed list");
			HtmlReportUtil.stepInfo("Captured First record from completed list");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not found First record from completed list");
			HtmlReportUtil.stepInfo("Error! Not found First record from completed list");

		}
		// Property Address

	}

	public static void clickScheduled_ShowQRUI_FirstRecord(WebDriver driver) throws Exception {

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			driver.findElement(By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[1]"))
					.click();

			LogUtil.infoLog(ShowingPage.class, "Clicked SHOW QR UI");
			HtmlReportUtil.stepInfo("Clicked SHOW QR UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked SHOW QR UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked SHOW QR UI");
		}

	}

	public static void clickScheduled_ShowingUI_FirstRecord(WebDriver driver) throws Exception {

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			driver.findElement(By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/a[2]"))
					.click();

			LogUtil.infoLog(ShowingPage.class, "Clicked SHOWING UI");
			HtmlReportUtil.stepInfo("Clicked SHOWING UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked SHOWING UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked SHOWING UI");
		}

	}

	public static boolean switchToShowingWindow(WebDriver driver) {
		boolean status = true;
		String parentWindow = driver.getWindowHandle();

		LogUtil.infoLog(ShowingPage.class, "Switching to New Window for SHOWING UI");
		HtmlReportUtil.stepInfo("Switching to New Window for SHOWING UI");

		for (String window : driver.getWindowHandles()) {
			if (!parentWindow.equals(window))
				driver.switchTo().window(window);
		}

		if (driver.findElement(By.cssSelector(".logo")).isDisplayed()) {
			LogUtil.infoLog(ShowingPage.class, "Switched to New Window Successfull");
			HtmlReportUtil.stepInfo("Switched to New Window Successfull");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Switched to New Window Unsuccessfull");
			HtmlReportUtil.stepInfo("Error! Switched to New Window Unsuccessfull");
			status = false;

		}

		return status;
	}

	public static boolean verifyShowingNewWindow(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking New Window for SHOWING UI");
		HtmlReportUtil.stepInfo("Checking New Window for SHOWING UI");

		if (!driver.findElement(By.cssSelector(".logo")).isDisplayed())
			status = false;

		return status;

	}

	public static boolean verifyLogoForShowingLink(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Logo for SHOWING UI");
		HtmlReportUtil.stepInfo("Checking Logo for SHOWING UI");

		if (!driver.findElement(By.cssSelector(".logo")).isDisplayed())
			status = false;

		return status;

	}

	public static boolean verifyLoggedUserInfo(WebDriver driver) {
		boolean status = true;
		String agetntName = driver.findElement(By.cssSelector("#name")).getText().trim();
		String agentEmail = driver.findElement(By.cssSelector("#phone-email")).getText().trim();

		// GlobalUtil.currentUserFullName
		// GlobalUtil.currentUserEmail

		LogUtil.infoLog(ShowingPage.class, "Checking agent name and email");
		HtmlReportUtil.stepInfo("Checking agent name and email ");

		LogUtil.infoLog(ShowingPage.class,
				"Expected :" + GlobalUtil.currentUserFullName + "," + GlobalUtil.currentUserEmail);
		HtmlReportUtil.stepInfo("Expected :" + GlobalUtil.currentUserFullName + "," + GlobalUtil.currentUserEmail);

		LogUtil.infoLog(ShowingPage.class, "Actual :" + agetntName + "," + agentEmail);
		HtmlReportUtil.stepInfo("Actual :" + agetntName + "," + agentEmail);

		if (GlobalUtil.currentUserFullName.equalsIgnoreCase(agetntName)
				&& GlobalUtil.currentUserEmail.equalsIgnoreCase(agentEmail)

		) {
		} else
			status = false;

		return status;

	}

	public static boolean verifyPropertyInfo_ScheduledShowing(WebDriver driver) {
		boolean status = true;
		String actualProperty = driver.findElement(By.cssSelector(".front>h2")).getText().trim().toUpperCase();

		String actualDateTime = driver.findElement(By.cssSelector("#schedule-msg>time")).getText().trim().toUpperCase();

		// GlobalUtil.currentUserFullName
		// GlobalUtil.currentUserEmail

		LogUtil.infoLog(ShowingPage.class, "Checking property info");
		HtmlReportUtil.stepInfo("Checking property info");

		LogUtil.infoLog(ShowingPage.class,
				"Expected Property:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey));
		HtmlReportUtil
				.stepInfo("Expected Property:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey));

		LogUtil.infoLog(ShowingPage.class, "Actual Property:" + actualProperty);
		HtmlReportUtil.stepInfo("Actual Property:" + actualProperty);

		int size = GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey).length();

		String repProp = actualProperty.substring(0, size);
		System.out.println("Replaced String: " + repProp);

		if (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey).equalsIgnoreCase(repProp)) {
			System.out.println("Property matched");

		} else {
			System.out.println("Property Not matched");
			status = false;

		}

		LogUtil.infoLog(ShowingPage.class,
				"Expected Date & Time:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey));
		HtmlReportUtil.stepInfo(
				"Expected Date & Time:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey));

		LogUtil.infoLog(ShowingPage.class, "Actual Date & Time:" + actualDateTime);
		HtmlReportUtil.stepInfo("Actual Date & Time:" + actualDateTime);

		if (!actualDateTime.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey))) {
			System.out.println("DateTime not matched");
			status = false;
		} else {
			System.out.println("DateTime  matched");
		}

		LogUtil.infoLog(ShowingPage.class, "Checking property image");
		HtmlReportUtil.stepInfo("Checking property image");

		if (driver.findElement(By.cssSelector("#scheduledBox>img")).isDisplayed()) {
			LogUtil.infoLog(ShowingPage.class, "Property image is displaying");
			HtmlReportUtil.stepInfo("Property image is displaying");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Property image is not displaying");
			HtmlReportUtil.stepInfo("Property image is not displaying");

			status = false;
		}

		return status;

	}

	public static boolean verifyEmailTextFieldForShowing(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking email text field for display and enabled");
		HtmlReportUtil.stepInfo("Checking email text field for display and enabled");

		if (driver.findElement(By.cssSelector("#email")).isDisplayed()
				&& driver.findElement(By.cssSelector("#email")).isEnabled()) {
		} else {
			status = false;
		}

		return status;

	}

	public static boolean verifySignupButtonForShowing(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking SignUp button display and enabled");
		HtmlReportUtil.stepInfo("Checking SignUp button display and enabled");

		if (driver.findElement(By.cssSelector(".btn.btn-default")).isDisplayed()
				&& driver.findElement(By.cssSelector(".btn.btn-default")).isEnabled()) {

			String buttonText = driver.findElement(By.cssSelector(".btn.btn-default")).getText();
			String actual = "Sign up";
			if (buttonText.equalsIgnoreCase(actual)) {
				LogUtil.infoLog(ShowingPage.class, "Sign up button is diplaying");
				HtmlReportUtil.stepInfo("Sign up button is diplaying");

			} else {
				LogUtil.infoLog(ShowingPage.class, "Error! Sign up button is not diplaying");
				HtmlReportUtil.stepInfo("Error! Sign up button is not diplaying");
				status = false;
			}

		} else {
			status = false;
		}

		return status;

	}

	public static boolean checkShowingUI_CompletedList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Showing Link for Completed");
		HtmlReportUtil.stepInfo("Checking Showing Link for Completed");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			if (driver
					.findElement(By.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/a[2]"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/a[2]"))
							.isEnabled()) {

			} else {
				status = false;
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in completed!");

			HtmlReportUtil.stepInfo("There is no record in completed!");
		}

		return status;
	}

	public static boolean checkShowQRUI_CompletedList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Show QR Link for Scheduled");
		HtmlReportUtil.stepInfo("Checking Show QR Link for Scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			if (driver
					.findElement(By.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/a[1]"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/a[1]"))
							.isEnabled()) {

			} else {
				status = false;
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in completed!");

			HtmlReportUtil.stepInfo("There is no record in completed!");
		}

		return status;
	}

	public static boolean checkInviteUI_CompletedList(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Invite Link for Completed");
		HtmlReportUtil.stepInfo("Checking Invite Link for Completed");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {

			if (driver
					.findElements(By
							.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default invite textual']"))
					.size() > 0

			) {

				LogUtil.infoLog(ShowingPage.class, "Invite Link is present in Completed");
				HtmlReportUtil.stepInfo("Invite Link is present in Completed");
				status = false;
			} else {

				LogUtil.infoLog(ShowingPage.class, "Invite Link is not present in Completed");
				HtmlReportUtil.stepInfo("Invite Link is not present in Completed");
			}

		} else {
			HtmlReportUtil.stepInfo("There is no record in scheduled!");

			HtmlReportUtil.stepInfo("There is no record in scheduled!");
		}

		return status;
	}

	public static boolean checkCompleted_EditThisShowingUI(WebDriver driver) {
		boolean status = true;
		// Return only first from the list
		LogUtil.infoLog(ShowingPage.class, "Checking Edit this showing for completed");
		HtmlReportUtil.stepInfo("Checking Edit this showing for completed");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			if (driver
					.findElements(By
							.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default edit']"))
					.size() > 0) {
				status = false;
				LogUtil.infoLog(ShowingPage.class, "Edit this showing UI is present in completed");
				HtmlReportUtil.stepInfo("Edit this showing UI is present in completed");

			} else {
				LogUtil.infoLog(ShowingPage.class, "Edit this showing UI is not present in completed");
				HtmlReportUtil.stepInfo("Edit this showing UI is not present in completed");
			}

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! There is no completed record");

			HtmlReportUtil.stepInfo("Error! There is no completed record");
			status = false;
		}

		return status;
	}

	public static void clickCompleted_ShowingUI_FirstRecord(WebDriver driver) throws Exception {

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			driver.findElement(By.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/a[2]"))
					.click();

			LogUtil.infoLog(ShowingPage.class, "Clicked Completed SHOWING UI");
			HtmlReportUtil.stepInfo("Clicked Completed SHOWING UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Completed SHOWING UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked Completed SHOWING UI");
		}

	}

	public static void clickRestoreScheduled_FirstRecord(WebDriver driver) throws Exception {

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			driver.findElement(
					By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[1]")).click();

			LogUtil.infoLog(ShowingPage.class, "Clicked Scheduled Restore UI");
			HtmlReportUtil.stepInfo("Clicked Scheduled Restore UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Scheduled Restore UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked Scheduled Restore UI");
		}

	}

	public static void clickRestoreCompleted_FirstRecord(WebDriver driver) throws Exception {

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
			driver.findElement(
					By.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/button[1]")).click();

			LogUtil.infoLog(ShowingPage.class, "Clicked completed Restore UI");
			HtmlReportUtil.stepInfo("Clicked completed Restore UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked completed Restore UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked completed Restore UI");
		}

	}

	public static boolean verifyPropertyInfo_CompletedShowing(WebDriver driver) {
		boolean status = true;
		String actualProperty = driver.findElement(By.cssSelector(".front>h2")).getText().trim();

		// String actualDateTime =
		// driver.findElement(By.cssSelector("#schedule-msg>time")).getText().trim();

		GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey);

		// GlobalUtil.currentUserFullName
		// GlobalUtil.currentUserEmail

		LogUtil.infoLog(ShowingPage.class, "Checking property info");
		HtmlReportUtil.stepInfo("Checking property info");

		LogUtil.infoLog(ShowingPage.class,
				"Expected Property:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey));
		HtmlReportUtil
				.stepInfo("Expected Property:" + GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey));

		LogUtil.infoLog(ShowingPage.class, "Actual Property:" + actualProperty);
		HtmlReportUtil.stepInfo("Actual Property:" + actualProperty);

		if (!actualProperty.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey))) {
			status = false;
		}

		// LogUtil.infoLog(ShowingPage.class,
		// "Expected Date & Time:" +
		// GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey));
		// HtmlReportUtil.stepInfo(
		// "Expected Date & Time:" +
		// GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey));
		//
		// LogUtil.infoLog(ShowingPage.class, "Actual Date & Time:" +
		// actualDateTime);
		// HtmlReportUtil.stepInfo("Actual Date & Time:" + actualDateTime);
		//
		// if
		// (!actualDateTime.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey)))
		// {
		// status = false;
		// }
		//
		//

		return status;

	}

	public static boolean verifyDescrptionLink_CompletedShowing(WebDriver driver) {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Checking Descrption Link");
		HtmlReportUtil.stepInfo("Checking Descrption Link");

		status = driver.findElement(By.cssSelector(".btn.btn-default.textual.description")).isDisplayed()
				&& driver.findElement(By.cssSelector(".btn.btn-default.textual.description")).isEnabled();

		return status;

	}

	public static boolean verifyStreetMapLink_CompletedShowing(WebDriver driver) throws Exception {
		boolean status = true;

		status = driver.findElement(By.cssSelector(".btn.btn-default.textual.map")).isDisplayed()
				&& driver.findElement(By.cssSelector(".btn.btn-default.textual.map")).isEnabled();

		// #map-canvas > .no-data

		driver.findElement(By.cssSelector(".btn.btn-default.textual.map")).click();
		Thread.sleep(3000);

		if (driver.findElements(By.cssSelector("#map-canvas > .no-data")).size() > 0) {
			String expected = "There is no location data available for this video.";
			String actual = driver.findElement(By.cssSelector("#map-canvas > .no-data")).getText().trim();

			if (expected.equalsIgnoreCase(actual)) {

				LogUtil.infoLog(ShowingPage.class, "Street Map Link is working, But there is no data");
				HtmlReportUtil.stepInfo("Street Map Link is working, But there is no data");

			}

		}

		return status;

	}

	public static boolean verifyStreetViewLink_CompletedShowing(WebDriver driver) throws Exception {
		boolean status = true;

		status = driver.findElement(By.cssSelector(".btn.btn-default.textual.street")).isDisplayed()
				&& driver.findElement(By.cssSelector(".btn.btn-default.textual.street")).isEnabled();

		// #map-canvas > .no-data

		driver.findElement(By.cssSelector(".btn.btn-default.textual.street")).click();
		Thread.sleep(3000);

		if (driver.findElements(By.cssSelector("#map-canvas > .no-data")).size() > 0) {
			String expected = "There is no location data available for this video.";
			String actual = driver.findElement(By.cssSelector("#map-canvas > .no-data")).getText().trim();

			if (expected.equalsIgnoreCase(actual)) {

				LogUtil.infoLog(ShowingPage.class, "Street View Link is working, But there is no data");
				HtmlReportUtil.stepInfo("Street View Link is working, But there is no data");

			}

		}

		return status;

	}

	public static boolean verifyVideoSegments_CompletedShowing(WebDriver driver) throws Exception {
		boolean status = true;

		// .jcarousel-pagination>a

		LogUtil.infoLog(ShowingPage.class, "Checking video segment");
		HtmlReportUtil.stepInfo("Checking video segment");

		status = driver.findElement(By.cssSelector(".jcarousel-item>video")).isDisplayed();

		// #map-canvas > .no-data

		if (driver.findElements(By.cssSelector(".jcarousel-pagination>a")).size() < 1)

		{
			status = false;
			LogUtil.infoLog(ShowingPage.class, "No video is available.");
			HtmlReportUtil.stepInfo("No video is available.");

		} else {

			LogUtil.infoLog(ShowingPage.class,
					"Total videos# " + driver.findElements(By.cssSelector(".jcarousel-pagination>a")).size());
			HtmlReportUtil
					.stepInfo("Total videos# " + driver.findElements(By.cssSelector(".jcarousel-pagination>a")).size());
		}

		return status;

	}

	public static boolean checkSaveDataMessage(WebDriver driver, String message) {

		LogUtil.infoLog(ShowingPage.class, "Verifing Save data message");
		HtmlReportUtil.stepInfo("Verifing Save data message");

		String actual = "";

		List<WebElement> messages = driver.findElements(By.xpath(".//*[@id='alerts']/div/div"));

		for (WebElement msg : messages) {

			if (msg.getText().contains(message)) {
				actual = msg.getText();

				break;
			}
		}

		LogUtil.infoLog(ShowingPage.class, "Expeced: " + message);
		HtmlReportUtil.stepInfo("Expeced: " + message);

		LogUtil.infoLog(ShowingPage.class, "Actual: " + actual);
		HtmlReportUtil.stepInfo("Actual: " + actual);

		return actual.equalsIgnoreCase(message);
	}

	public static boolean checkShowQRPageMessage(WebDriver driver) {

		boolean status = true;
		// page-heading">Congratulations!

		String expected = "Congratulations!";
		String actual = driver.findElement(By.cssSelector(".page-heading")).getText().trim();

		LogUtil.infoLog(ShowingPage.class, "Verify page heading of QR page");
		HtmlReportUtil.stepInfo("Verify page heading of QR page");

		LogUtil.infoLog(ShowingPage.class, "Expected:" + expected);
		HtmlReportUtil.stepInfo("Expected: " + expected);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		HtmlReportUtil.stepInfo("Actual: " + actual);

		if (!expected.equalsIgnoreCase(actual)) {
			status = false;
		}

		return status;
	}

	public static boolean checkShowQRPageEmailLinks(WebDriver driver) throws Exception {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Verify Email Link is working");
		HtmlReportUtil.stepInfo("Verify Email Link is working");

		if (!driver.findElement(By.id("email-details")).isDisplayed())
			status = false;

		LogUtil.infoLog(ShowingPage.class, "Click Email Link ");
		HtmlReportUtil.stepInfo("Click Email Link");

		driver.findElement(By.id("email-details")).click();
		Thread.sleep(3000);

		String ecpected = "Success: An email with the details for this showing has been sent to the showing's assigned agent(s)."
				.trim();

		String actual = driver.findElement(By.id("alerts")).getText().trim();

		LogUtil.infoLog(ShowingPage.class, "Expected message:" + ecpected);
		HtmlReportUtil.stepInfo("Expected message:" + ecpected);

		LogUtil.infoLog(ShowingPage.class, "Actual message:" + actual);
		HtmlReportUtil.stepInfo("Actual message:" + actual);

		if (!actual.equalsIgnoreCase(ecpected))
			status = false;

		return status;
	}

	public static boolean checkShowQRPagePrintLinks(WebDriver driver) throws Exception {
		boolean status = true;

		LogUtil.infoLog(ShowingPage.class, "Verify Print Link is working");
		HtmlReportUtil.stepInfo("Verify Print Link is working");

		if (!driver.findElement(By.id("link-to-printable")).isDisplayed())
			status = false;

		LogUtil.infoLog(ShowingPage.class, "Click Print Link ");
		HtmlReportUtil.stepInfo("Click Print Link");

		// driver.findElement(By.id("link-to-printable")).click();
		// Thread.sleep(3000);
		//
		// Robot r = new Robot();
		// r.keyPress(KeyEvent.VK_ESCAPE);
		//
		// LogUtil.infoLog(ShowingPage.class, "Closing Print Window");
		// HtmlReportUtil.stepInfo("Closing Print Window");
		Thread.sleep(1000);

		return status;
	}

	public static boolean checkShowQRPagePropertyInfo(WebDriver driver) {

		boolean status = true;
		// page-heading">Congratulations!

		// Check Property Info

		LogUtil.infoLog(ShowingPage.class, "Verify Property info of QR page");
		HtmlReportUtil.stepInfo("Verify Property info of QR page");

		// GlobalUtil.propertyCurrentRecord

		String actualPropertyAddress = driver.findElement(By.id("showingAddressHeading")).getText().trim()
				.toUpperCase();
		String expectedPropertyAddress = GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey)
				.toUpperCase();

		String actualDateTime = driver.findElement(By.id("showingDateHeading")).getText().trim().toUpperCase();
		String expectedDateTime = GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey).toUpperCase();

		LogUtil.infoLog(ShowingPage.class, "Expected:" + expectedPropertyAddress + "," + expectedDateTime);
		HtmlReportUtil.stepInfo("Expected: " + expectedPropertyAddress + "," + expectedDateTime);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actualPropertyAddress + "," + actualDateTime);
		HtmlReportUtil.stepInfo("Actual : " + actualPropertyAddress + "," + actualDateTime);

		if (actualPropertyAddress.contains(expectedPropertyAddress)

				&& actualDateTime.contains(expectedDateTime)

		) {

		} else {
			status = false;
		}

		return status;
	}

	public static void clickScheduled_DeleteThisShowingUI(WebDriver driver) throws Exception {

		if (checkScheduled_DeleteThisShowingUI(driver)) {
			driver.findElement(
					By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[2]")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Scheduled Delete This Showing UI");
			HtmlReportUtil.stepInfo("Clicked Scheduled Delete This Showing UI");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Scheduled Delete This Showing UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked Scheduled Delete This Showing UI");
		}
	}

	public static void clickCompleted_DeleteThisShowingUI(WebDriver driver) {

		if (checkCompleted_DeleteThisShowingUI(driver)) {
			driver.findElement(
					By.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/button[2]")).click();
			// driver.findElement(By.xpath("html/body/section/div[4]/div/div[2]/div/ul/li[1]/div/div[3]/div/div/button[2]")).click();

			LogUtil.infoLog(ShowingPage.class, "Clicked Completed Delete UI(X)");
			HtmlReportUtil.stepInfo("Clicked Completed Delete UI(X)");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Completed Delete UI(X)");
			HtmlReportUtil.stepInfo("Error! Not Clicked Completed Delete UI(X)");
		}

	}

	// deleteCompletedFirstRecord

	public static void deleteScheduledFirstRecord(WebDriver driver) throws Exception {
		clickScheduled_DeleteThisShowingUI(driver);
		Thread.sleep(1000);

	}

	public static void deleteCompletedFirstRecord(WebDriver driver) throws Exception {
		Thread.sleep(1000);
		clickCompleted_DeleteThisShowingUI(driver);
		Thread.sleep(1000);

	}

	public static void restoreScheduledFirstRecored(WebDriver driver) {
		if (driver
				.findElements(
						By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[1]"))
				.size() > 0)
			driver.findElement(
					By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[1]")).click();
	}

	public static boolean checkDeletedScheduled(WebDriver driver) throws Throwable {

		boolean status = false;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {
			// You are on Showing page

			// Click Mine
			clickMineUI(driver);

			// There is at least one recored to delete
			if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
				LogUtil.infoLog(ShowingPage.class, "Delting first record from schduled list");
				HtmlReportUtil.stepInfo("Delting first record from schduled list");

				capatureScheduledFirstRecord(driver);

				// Delete First Record
				deleteScheduledFirstRecord(driver);

				// Click View Deleted record
				clickViewDeletedUI(driver);

				// Capture all records
				List<WebElement> deletedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

				int size = deletedScheduleds.size();
				System.out.println("Total records:" + (size - 1));

				if (size > 1) {
					for (int j = 0; j < size - 1; j++) {

						String value = "";
						String strXpath = "";

						// Address
						strXpath = "div/div/h3";
						value = deletedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyAddressKey, value);

						// Date Time
						strXpath = "div/div[2]/small[1]";
						value = deletedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyDateTimeKey, value);

						// Client
						strXpath = "div/div[2]/small[2]";
						value = deletedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyClientKey, value);

						// Agent

						strXpath = "div/div[2]/small[3]";
						value = deletedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyAgentKey, value);

						// Note
						strXpath = "div/div[@class='col-sm-12']/div/small";

						driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

						if (deletedScheduleds.get(j).findElements(By.xpath(strXpath)).size() > 0) {
							System.out.println("Note found..");

							value = deletedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim()
									.toUpperCase();
							GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyNoteKey, value);
						} else {
							System.out.println("No Note found..");
							GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

						}

						if (GlobalUtil.propertyCurrentRecord.equals(GlobalUtil.propertyDeletedRecord)) {
							LogUtil.infoLog(ShowingPage.class, "Record matched");
							status = true;
							break;
						} else {
							System.out.println(
									GlobalUtil.propertyCurrentRecord + "With" + GlobalUtil.propertyDeletedRecord);

							System.out.println("Record not matched");
							GlobalUtil.propertyDeletedRecord.clear();
							status = false;
						}

					} // end for
				} // End if

				LogUtil.infoLog(ShowingPage.class, "Expected:" + GlobalUtil.propertyDeletedRecord);
				LogUtil.infoLog(ShowingPage.class, "Actual:" + GlobalUtil.propertyCurrentRecord);

				HtmlReportUtil.stepInfo("Expected:" + GlobalUtil.propertyDeletedRecord);
				HtmlReportUtil.stepInfo("Actual:" + GlobalUtil.propertyCurrentRecord);

			} else {
				LogUtil.infoLog(ShowingPage.class, "There is no record to delete for scheduled");
				HtmlReportUtil.stepInfo("There is no record to delete for scheduled");
				status = false;
			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}

		// Delete First Scheduled Record

		return status;
	}

	public static boolean checkUpdateScheduled_FromAll(WebDriver driver) throws Throwable {

		boolean status = true;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {

			if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {

				String popupDate = GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey).trim();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

				GlobalUtil.popupCurrentData.remove(GlobalUtil.propertyDateTimeKey, popupDate);

				Date d = formatter.parse(popupDate);
				String newDateFormat = DateFormatUtils.format(d, "HH:mm, MMMM dd, yyyy").trim().toUpperCase();

				// newDateFormat
				GlobalUtil.popupCurrentData.put(GlobalUtil.propertyDateTimeKey, newDateFormat);

				HtmlReportUtil.stepInfo("Record to matched:" + GlobalUtil.popupCurrentData);
				LogUtil.infoLog(ClientsPage.class, "Record to matched:" + GlobalUtil.popupCurrentData);

				List<WebElement> updatedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

				int size = updatedScheduleds.size();

				System.out.println("Total Record to check :" + (size - 1));
				for (int i = 1; i < size; i++) {
					status = true;

					capatureScheduledRecordOneByOne(driver, i);

					int strSize = GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey).length();

					String newProp = GlobalUtil.popupCurrentData.get(GlobalUtil.propertyAddressKey).substring(0,
							strSize);
					System.out.println("After substr :" + newProp);

					// //Check Property
					// if(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyAddressKey).substring(0,
					// strSize).contains
					// (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey))
					// )
					// {
					// System.out.println("Property matched");
					//
					// }else{
					// status =false;
					//
					//
					// }
					//
					// //Check Client
					// if(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyClientKey).contains
					// (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyClientKey).toUpperCase())
					// ){
					//
					// System.out.println("Client matched");
					//
					// }else{
					// status = false;
					//
					// }
					//
					// //Check DateTime

					if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey))) {
						System.out.println("Date matched");

					} else {
						status = false;

					}

					if (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase("Note=") ||

							GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey).isEmpty()
							|| GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey)
									.equalsIgnoreCase("BLANK")

					) {
						LogUtil.infoLog(ShowingPage.class, "Note is blank");
						HtmlReportUtil.stepInfo("Note is blank");
					} else {
						// Check Note

						// int
						// noteLen=GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).length();
						//
						// String[] strNote =
						// GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey).split(":");
						//
						// for(String s:strNote)
						// {
						// s=s.trim();
						// System.out.println(s);
						// if(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase(s))
						// {
						// System.out.println("Note matched");
						// }else{status=false;}
						// }

						//
						// if(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey).contains
						// (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey))
						// ){
						//
						//
						//
						// } else{
						// status=false;
						//
						//
						// }

					} // End Note check

					if (status) {

						break;
					} else {

						GlobalUtil.propertyCurrentRecord.clear();
					}

				} // End For

				if (status) {
					HtmlReportUtil.stepInfo("Record matched:" + GlobalUtil.propertyCurrentRecord);
					LogUtil.infoLog(ClientsPage.class, "Record matched:" + GlobalUtil.propertyCurrentRecord);
				} else {
					HtmlReportUtil.stepInfo("Record not matched");
					LogUtil.infoLog(ClientsPage.class, "Record not matched");
				}

			} else {
				LogUtil.errorLog(ShowingPage.class, "There is no record");
				HtmlReportUtil.stepError("There is no record");

			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}
		// capatureScheduledRecordOneByOne

		return status;
	}// End function

	public static boolean checkUpdatedScheduled_FromAll(WebDriver driver) throws Throwable {

		boolean status = true;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {

			if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {

				String popupDate = GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey).trim();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

				GlobalUtil.popupCurrentData.remove(GlobalUtil.propertyDateTimeKey, popupDate);

				Date d = formatter.parse(popupDate);
				String newDateFormat = DateFormatUtils.format(d, "HH:mm, MMMM dd, yyyy").trim().toUpperCase();

				// newDateFormat
				GlobalUtil.popupCurrentData.put(GlobalUtil.propertyDateTimeKey, newDateFormat);

				List<WebElement> updatedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

				int size = updatedScheduleds.size();

				for (int i = 1; i < size; i++) {
					status = true;

					capatureScheduledRecordOneByOne(driver, i);

					// Check all data

					// Check Property
					if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyAddressKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey)))

					{

					} else {
						status = false;

					}

					// Check Client
					if (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyClientKey)
							.contains(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyClientKey).toUpperCase())) {

					} else {
						status = false;

					}

					// Check DateTime
					if (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey)
							.contains(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey))) {

					} else {
						status = false;

					}

					if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase("Note=") ||

							GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).isEmpty()
							|| GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase("BLANK")

					) {
						LogUtil.infoLog(ShowingPage.class, "Note is blank");
						HtmlReportUtil.stepInfo("Note is blank");
					} else {
						// Check Note

						if (GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey)
								.contains(GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey))) {

						} else {
							status = false;

						}

					} // End Note check

					if (status) {

						break;
					} else {

						GlobalUtil.propertyCurrentRecord.clear();
					}

				} // End For

				if (status) {
					HtmlReportUtil.stepInfo("Record matched");
					LogUtil.infoLog(ClientsPage.class, "Record matched");
				} else {
					HtmlReportUtil.stepInfo("Record not matched");
					LogUtil.infoLog(ClientsPage.class, "Record not matched");
				}

			} else {
				LogUtil.errorLog(ShowingPage.class, "There is no record");
				HtmlReportUtil.stepError("There is no record");

			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}
		// capatureScheduledRecordOneByOne

		return status;
	}

	public static boolean checkDeletedScheduled_FromAll(WebDriver driver) throws Throwable {

		boolean status = true;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {

			if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {

				HtmlReportUtil.stepInfo("Record to matched:" + GlobalUtil.propertyRestoredRecord);
				LogUtil.infoLog(ClientsPage.class, "Record to matched:" + GlobalUtil.propertyRestoredRecord);

				List<WebElement> updatedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

				int size = updatedScheduleds.size();

				System.out.println("Total Record to check :" + (size - 1));
				for (int i = 1; i < size; i++) {
					status = true;

					capatureScheduledRecordOneByOne(driver, i);

					// Check all data

					// Check Property
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyAddressKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey)))

					{

					} else {
						status = false;

					}

					// Check Client
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyClientKey).contains(
							GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyClientKey).toUpperCase())) {

					} else {
						status = false;

					}

					// Check DateTime
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyDateTimeKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey))) {

					} else {
						status = false;

					}

					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase("Note=") ||

							GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey).isEmpty()
							|| GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey)
									.equalsIgnoreCase("BLANK")

					) {
						LogUtil.infoLog(ShowingPage.class, "Note is blank");
						HtmlReportUtil.stepInfo("Note is blank");
					} else {
						// Check Note

						if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey)
								.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey))) {

						} else {
							status = false;

						}

					} // End Note check

					if (status) {

						break;
					} else {

						GlobalUtil.propertyCurrentRecord.clear();
					}

				} // End For

				if (status) {
					HtmlReportUtil.stepInfo("Record matched:" + GlobalUtil.propertyCurrentRecord);
					LogUtil.infoLog(ClientsPage.class, "Record matched:" + GlobalUtil.propertyCurrentRecord);
				} else {
					HtmlReportUtil.stepInfo("Record not matched");
					LogUtil.infoLog(ClientsPage.class, "Record not matched");
				}

			} else {
				LogUtil.errorLog(ShowingPage.class, "There is no record");
				HtmlReportUtil.stepError("There is no record");

			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}
		// capatureScheduledRecordOneByOne

		return status;
	}// End function

	public static boolean checkDeletedCompleted_FromAll(WebDriver driver) throws Throwable {

		boolean status = true;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {

			if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {

				HtmlReportUtil.stepInfo("Record to matched:" + GlobalUtil.propertyRestoredRecord);
				LogUtil.infoLog(ClientsPage.class, "Record to matched:" + GlobalUtil.propertyRestoredRecord);

				List<WebElement> updatedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

				int size = updatedScheduleds.size();

				System.out.println("Total Record to check :" + (size - 1));
				for (int i = 1; i < size; i++) {
					status = true;

					capatureCompletedRecordOneByOne(driver, i);

					// Check all data

					// Check Property
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyAddressKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyAddressKey)))

					{

					} else {
						status = false;

					}

					// Check Client
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyClientKey).contains(
							GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyClientKey).toUpperCase())) {

					} else {
						status = false;

					}

					// Check DateTime
					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyDateTimeKey)
							.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyDateTimeKey))) {

					} else {
						status = false;

					}

					if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey).equalsIgnoreCase("Note=") ||

							GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey).isEmpty()
							|| GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey)
									.equalsIgnoreCase("BLANK")

					) {
//						LogUtil.infoLog(ShowingPage.class, "Note is blank");
//						HtmlReportUtil.stepInfo("Note is blank");
					} else {
						// Check Note

						if (GlobalUtil.propertyRestoredRecord.get(GlobalUtil.propertyNoteKey)
								.contains(GlobalUtil.propertyCurrentRecord.get(GlobalUtil.propertyNoteKey))) {

						} else {
							status = false;

						}

					} // End Note check

					if (status) {

						break;
					} else {

						
					}

				} // End For

				if (status) {
					HtmlReportUtil.stepInfo("Record matched:" + GlobalUtil.propertyCurrentRecord);
					LogUtil.infoLog(ClientsPage.class, "Record matched:" + GlobalUtil.propertyCurrentRecord);
				} else {
					HtmlReportUtil.stepInfo("Record not matched");
					LogUtil.infoLog(ClientsPage.class, "Record not matched");
				}

			} else {
				LogUtil.errorLog(ShowingPage.class, "There is no record");
				HtmlReportUtil.stepError("There is no record");

			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}
		// capatureScheduledRecordOneByOne

		return status;
	}// End function

	public static boolean checkUpdatedScheduled(WebDriver driver) throws Throwable {

		boolean status = false;
		boolean dateConversion = false;

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {
			// You are on Showing page

			// Click Mine
			clickMineUI(driver);
			Thread.sleep(3000);

			// There is at least one recored to delete
			if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
				LogUtil.infoLog(ShowingPage.class, "Checking updated record from schduled list");
				HtmlReportUtil.stepInfo("Checking updated record from schduled list");

				// Capture all records
				List<WebElement> updatedScheduleds = driver
						.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

				if (!dateConversion) {
					String popupDate = GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey).trim();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

					GlobalUtil.popupCurrentData.remove(GlobalUtil.propertyDateTimeKey, popupDate);

					Date d = formatter.parse(popupDate);
					String newDateFormat = DateFormatUtils.format(d, "HH:mm, MMMM dd, yyyy").trim().toUpperCase();

					// newDateFormat
					GlobalUtil.popupCurrentData.put(GlobalUtil.propertyDateTimeKey, newDateFormat);

					dateConversion = true;
				}
				
//				GlobalUtil.popupCurrentData.put(GlobalUtil.propertyAgentKey,
//						 GlobalUtil.currentUserFullName.toUpperCase());
				

				System.out.println("Total records to look: " + (updatedScheduleds.size() - 1));

				HtmlReportUtil.stepInfo("Total records to look: " + (updatedScheduleds.size() - 1));

				LogUtil.infoLog(ShowingPage.class, "Record to match:" + GlobalUtil.popupCurrentData);
				HtmlReportUtil.stepInfo("Record to match:" + GlobalUtil.popupCurrentData);

				int size = updatedScheduleds.size();
				if (size > 1) {
					for (int j = 0; j < size - 1; j++) {

						String value = "";
						String strXpath = "";
						status = true;
						GlobalUtil.updatesScheduleRecord.clear();

						// Current Record to check
						// Address
						strXpath = "div/div/h3";
						value = updatedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						
						GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyAddressKey, value);
						

						// Date Time
						strXpath = "div/div[2]/small[1]";
						value = updatedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyDateTimeKey, value);

						// Convert date to Scheduled format

						// Client
						strXpath = "div/div[2]/small[2]";
						value = updatedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
						value=value.replace("CLIENT:", "");
						
						
						GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyClientKey, value.trim());

						// Agent
//						strXpath = "div/div[2]/small[3]";
//						value = updatedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
//						value= value.replace("AGENT:", "");
//						
//						GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyAgentKey, value.trim());
//						

						// Note
						strXpath = "div[@class='row'][2]/div/div/small";
						// *[@class='list-group
						// scheduled-showings']/li[2]/div[@class='row'][2]/div/div/small
						if (updatedScheduleds.get(j).findElements(By.xpath(strXpath)).size() > 0) {

							value = updatedScheduleds.get(j).findElement(By.xpath(strXpath)).getText().trim()
									.toUpperCase();
							value = value.replace("NOTE:", "").trim();
							
							GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyNoteKey, value);
						}else
							GlobalUtil.updatesScheduleRecord.put(GlobalUtil.propertyNoteKey, "BLANK");

						

						if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyAddressKey)
								.contains(GlobalUtil.updatesScheduleRecord.get(GlobalUtil.propertyAddressKey)))

						{
							System.out.println("Property address matched");

						} else {
							status = false;
							System.out.println("Property address not matched");

						}

						if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyClientKey)
								.contains(GlobalUtil.updatesScheduleRecord.get(GlobalUtil.propertyClientKey)))

						{

							System.out.println("Client matched");

						} else {
							status = false;
							System.out.println("Client not matched");

						}

						if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyDateTimeKey)
								.contains(GlobalUtil.updatesScheduleRecord.get(GlobalUtil.propertyDateTimeKey)))

						{

							System.out.println("Date time matched");

						} else {
							status = false;
							System.out.println("Date time not matched");

						}

						if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey)
								.contains(GlobalUtil.updatesScheduleRecord.get(GlobalUtil.propertyNoteKey)))

						{

							System.out.println("Note matched");

						} else {
							status = false;
							System.out.println("Note not matched");

						}
						
						if(status){
							break;
						}
						
						System.out.println("Current Record:" + GlobalUtil.updatesScheduleRecord);

					} // end for
				} // End if

				LogUtil.infoLog(ShowingPage.class, "Expected:" + GlobalUtil.popupCurrentData);
				LogUtil.infoLog(ShowingPage.class, "Actual:" + GlobalUtil.updatesScheduleRecord);

				HtmlReportUtil.stepInfo("Expected:" + GlobalUtil.popupCurrentData);
				HtmlReportUtil.stepInfo("Actual:" + GlobalUtil.updatesScheduleRecord);

			} else {
				LogUtil.infoLog(ShowingPage.class, "There is no record to check for scheduled");
				HtmlReportUtil.stepInfo("There is no record to check for scheduled");
				status = false;
			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}

		return status;
	}// End check ScheduledAddedRecord

	public static boolean checkDeletedCompleted(WebDriver driver) throws Throwable {

		boolean status = false;
		LogUtil.infoLog(ShowingPage.class, "Checking Deleted Completed ");
		HtmlReportUtil.stepInfo("Checking Deleted Completed");

		if ("Showings".equalsIgnoreCase(driver.findElement(By.cssSelector(".page-heading")).getText().trim())) {
			// You are on Showing page

			// Click Mine
			clickMineUI(driver);
			Thread.sleep(3000);

			// There is at least one recored to delete
			if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1) {
				clickMineUI(driver);
				Thread.sleep(2000);

				LogUtil.infoLog(ShowingPage.class, "Delting first record from completed list");
				HtmlReportUtil.stepInfo("Delting first record from completed list");

				capatureCompletedFirstRecord(driver);

				// Delete First Record
				deleteCompletedFirstRecord(driver);

				// Click View Deleted record
				clickViewDeletedUI(driver);
				Thread.sleep(3000);

				// Capture all records
				List<WebElement> deletedCompleteds = driver
						.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

				int size = deletedCompleteds.size();
				if (size > 1) {
					for (int j = 0; j <= size - 1; j++) {

						String value = "";
						String strXpath = "";

						// Address
						strXpath = "div/div/h3";
						value = deletedCompleteds.get(j).findElement(By.xpath(strXpath)).getText().trim();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyAddressKey, value);

						// Date Time
						strXpath = "div/div[2]/small[1]";
						value = deletedCompleteds.get(j).findElement(By.xpath(strXpath)).getText().trim();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyDateTimeKey, value);

						// Client
						strXpath = "div/div[2]/small[2]";
						value = deletedCompleteds.get(j).findElement(By.xpath(strXpath)).getText().trim();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyClientKey, value);

						// Agent

						strXpath = "div/div[2]/small[3]";
						value = deletedCompleteds.get(j).findElement(By.xpath(strXpath)).getText().trim();
						GlobalUtil.propertyDeletedRecord.put(GlobalUtil.propertyAgentKey, value);

						if (GlobalUtil.propertyCurrentRecord.equals(GlobalUtil.propertyDeletedRecord)) {
							LogUtil.infoLog(ShowingPage.class, "Record matched");
							status = true;
							break;
						} else {
							GlobalUtil.propertyDeletedRecord.clear();
							status = false;
						}

					} // end for
				} // End if

				LogUtil.infoLog(ShowingPage.class, "Expected:" + GlobalUtil.propertyDeletedRecord);
				LogUtil.infoLog(ShowingPage.class, "Actual:" + GlobalUtil.propertyCurrentRecord);

				HtmlReportUtil.stepInfo("Expected:" + GlobalUtil.propertyDeletedRecord);
				HtmlReportUtil.stepInfo("Actual:" + GlobalUtil.propertyCurrentRecord);

			} else {
				LogUtil.infoLog(ShowingPage.class, "There is no record to delete for completed");
				HtmlReportUtil.stepInfo("There is no record to delete for completed");
				status = false;
			}

		} else {
			LogUtil.errorLog(ShowingPage.class, "User is not on Showing page");
			HtmlReportUtil.stepError("User is not on Showing page");

		}

		// Delete First Scheduled Record

		return status;
	}

	public static boolean checkScheduleANewShowingUI(WebDriver driver) {

		return driver.findElement(By.cssSelector("button.btn.btn-default.add")).isDisplayed()
				&& driver.findElement(By.cssSelector("button.btn.btn-default.add")).isEnabled();
	}

	public static void clickScheduleANewShowingUI(WebDriver driver) {

		// Css = .showings>div>div>button
		// button.btn.btn-default.add
		if (checkScheduleANewShowingUI(driver)) {
			driver.findElement(By.cssSelector(".showings>div>div>button")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Schedule A New Showing UI");
			HtmlReportUtil.stepInfo("Clicked Schedule A New Showing UI");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Schedule A New Showing UI");
			HtmlReportUtil.stepInfo("Error! Not Clicked Schedule A New Showing UI");
		}
	}

	public static boolean checkScheduleANewShowingPopUp(WebDriver driver) throws Exception {
		// Click + button

		String expected = "SCHEDULE SHOWING";

		String actual = driver.findElement(By.cssSelector("#ssModalLabel")).getText().trim();

		LogUtil.infoLog(ShowingPage.class, "Expected: " + expected);
		HtmlReportUtil.stepInfo("Expected: " + expected);

		LogUtil.infoLog(ShowingPage.class, "Actual: " + actual);
		HtmlReportUtil.stepInfo("Actual: " + actual);

		return actual.equalsIgnoreCase(expected);

	}

	public static boolean checkEditThisShowingUI(WebDriver driver) {

		// Return only first from the list
		LogUtil.infoLog(ShowingPage.class, "Checking Edit this showing for scheduled");
		HtmlReportUtil.stepInfo("Checking Edit this showing for scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			return driver.findElements(By.cssSelector(".btn.btn-default.edit")).get(0).isDisplayed()
					&& driver.findElements(By.cssSelector(".btn.btn-default.edit")).get(0).isEnabled();

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! There is no scheduled record");

			HtmlReportUtil.stepInfo("Error! There is no scheduled record");
			return false;
		}
		
		

	}

	public static void clickEditThisShowingUI(WebDriver driver) {

		// Return only first from the list
		LogUtil.infoLog(ShowingPage.class, "Click on Edit this showing for scheduled");
		HtmlReportUtil.stepInfo("Click on Edit this showing for scheduled");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1) {
			driver.findElements(By.cssSelector(".btn.btn-default.edit")).get(0).click();

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! There is no scheduled record");

			HtmlReportUtil.stepInfo("Error! There is no scheduled record");

		}

	}

	public static boolean checkSaveShowingButton(WebDriver driver) {

		return driver.findElement(By.cssSelector(".btn.btn-success")).isDisplayed();
	}

	public static boolean checkPropertyEmpty(WebDriver driver) {

		boolean status = false;
		String expected = "propertyPicker";
		String actual = "";
		List<WebElement> dropDowns = driver.findElements(By.cssSelector(".form-control.invalid"));
		for (WebElement element : dropDowns) {
			actual = element.getAttribute("id");
			if (actual.equalsIgnoreCase(expected)) {
				status = true;
			}

		}

		return status;
	}

	public static void selectFirstPropertyFromDropDown(WebDriver driver) {

		Select dd = new Select(driver.findElement(By.id("propertyPicker")));
		if (dd.getOptions().size() > 1) {
			dd.selectByIndex(1);
			LogUtil.infoLog(ShowingPage.class, "Selected first property from list");
			HtmlReportUtil.stepInfo("Selected first property from list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Selected first property from list");
			HtmlReportUtil.stepInfo("Error! Not Selected first property from list");

		}

	}

	public static String getPropertyFromDropDown(WebDriver driver) {
		String property = "";
		Select dd = new Select(driver.findElement(By.id("propertyPicker")));
		if (dd.getOptions().size() > 1) {
			property = dd.getFirstSelectedOption().getText().trim();
			LogUtil.infoLog(ShowingPage.class, "Getting property from list");
			HtmlReportUtil.stepInfo("Gettting property from list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Get property from list");
			HtmlReportUtil.stepInfo("Error! Not Get property from list");

		}
		return property;

	}

	public static void selectAnyPropertyFromDropDown(WebDriver driver, int i) {

		Select dd = new Select(driver.findElement(By.id("propertyPicker")));

		if (dd.getOptions().size() > 1) {
			dd.selectByIndex(i);
			LogUtil.infoLog(ShowingPage.class, "Selected given property from list");
			HtmlReportUtil.stepInfo("Selected given property from list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Selected given property from list");
			HtmlReportUtil.stepInfo("Error! Not Selected given property from list");

		}

	}

	public static void selectFirstClientFromDropDown(WebDriver driver) throws Exception {
		String client = "";
		Select dd = new Select(driver.findElement(By.id("buyerPicker")));

		if (dd.getOptions().size() > 1) {
			dd.selectByIndex(1);
			Thread.sleep(1000);
			LogUtil.infoLog(ShowingPage.class, "Selected first client from list");
			HtmlReportUtil.stepInfo("Selected first client from list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Selected first client from list");
			HtmlReportUtil.stepInfo("Error! Not Selected first client from list");

		}

	}

	public static String getClientFromDropDown(WebDriver driver) {
		String client = "";
		Select dd = new Select(driver.findElement(By.id("buyerPicker")));

		if (dd.getOptions().size() > 1) {
			client = dd.getFirstSelectedOption().getText().trim();
			LogUtil.infoLog(ShowingPage.class, "Getting client from list");
			HtmlReportUtil.stepInfo("Getting client from list");
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Get client from list");
			HtmlReportUtil.stepInfo("Error! Not Get client from list");

		}

		return client;
	}

	public static void selectAnyClientFromDropDown(WebDriver driver, int i) {

		Select dd = new Select(driver.findElement(By.id("buyerPicker")));

		if (dd.getOptions().size() > 1) {
			dd.selectByIndex(i);
			LogUtil.infoLog(ShowingPage.class, "Selected given client from list");
			HtmlReportUtil.stepInfo("Selected given client from list");

		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Selected given client from list");
			HtmlReportUtil.stepInfo("Error! Not Selected given client from list");

		}

	}

	public static void enterScheduleDate(WebDriver driver) throws Exception {
		Date d = new Date();

		String newDateFormat = DateFormatUtils.format(d, "yyyy/MM/dd HH:mm").trim();

		// newDateFormat

		Thread.sleep(500);
		driver.findElement(By.id("datetimepicker")).clear();
		Thread.sleep(1000);
		driver.findElement(By.id("datetimepicker")).sendKeys(newDateFormat);
		Thread.sleep(1000);
		driver.findElement(By.id("datetimepicker")).sendKeys(Keys.TAB);
		Thread.sleep(500);

		LogUtil.infoLog(ShowingPage.class, "Date entered");
		HtmlReportUtil.stepInfo("Date entered");

	}

	public static String getScheduleDate(WebDriver driver) {

		LogUtil.infoLog(ShowingPage.class, "Gettting Schedule date");
		HtmlReportUtil.stepInfo("Gettting Schedule date");
		return driver.findElement(By.id("datetimepicker")).getAttribute("value").trim();

	}

	public static void enterNote(WebDriver driver, String note) throws Exception {

		Thread.sleep(1000);

		driver.findElement(By.xpath(".//*[@id='showing']/div[3]/div/textarea")).clear();
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='showing']/div[3]/div/textarea")).sendKeys(note);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='showing']/div[3]/div/textarea")).sendKeys(Keys.TAB);
		LogUtil.infoLog(ShowingPage.class, "Note entered:" + note);
		HtmlReportUtil.stepInfo("Note entered" + note);

	}

	public static String getNote(WebDriver driver) {
		String noteText = "";
		LogUtil.infoLog(ShowingPage.class, "Getting note");
		HtmlReportUtil.stepInfo("Getting Note");

		if (driver.findElement(By.xpath("//*[@name='note']")).getText().isEmpty()) {

		} else {
			noteText = driver.findElement(By.xpath("//*[@name='note']")).getAttribute("value").trim().toUpperCase();

		}
		LogUtil.infoLog(ShowingPage.class, "Note text: " + noteText);
		HtmlReportUtil.stepInfo("Note text: " + noteText);

		return noteText;

	}

	public static void capturePopUpData(WebDriver driver) {

		GlobalUtil.popupCurrentData.clear();

		// Property

		GlobalUtil.popupCurrentData.put(GlobalUtil.propertyAddressKey, getPropertyFromDropDown(driver).toUpperCase());

		// Client
		GlobalUtil.popupCurrentData.put(GlobalUtil.propertyClientKey, getClientFromDropDown(driver).toUpperCase());

		// Date
		GlobalUtil.popupCurrentData.put(GlobalUtil.propertyDateTimeKey, getScheduleDate(driver).toUpperCase());

		// Note
		if (!getNote(driver).isEmpty())
			GlobalUtil.popupCurrentData.put(GlobalUtil.propertyNoteKey, getNote(driver).toUpperCase());
		else
			GlobalUtil.popupCurrentData.put(GlobalUtil.propertyNoteKey, "BLANK");

	}

	public static boolean checkPropertyScheduled(WebDriver driver) throws Exception {
		boolean status = false;

		ShowingPage.clickScheduleANewShowingUI(driver);
		Thread.sleep(2000);
		selectFirstPropertyFromDropDown(driver);

		selectFirstClientFromDropDown(driver);

		enterScheduleDate(driver);

		enterNote(driver, "THIS IS A NOTE");

		// Captur all data
		capturePopUpData(driver);

		if (GlobalUtil.popupCurrentData.get(GlobalUtil.propertyNoteKey).equals("BLANK")) {
			GlobalUtil.popupCurrentData.put(GlobalUtil.propertyNoteKey, "THIS IS A NOTE");
		}

		// Save Showing
		clickSaveShowing(driver);

		Thread.sleep(3000);

		// Find property from record
		String expected = "Success: The showing was created and we have emailed the QR code to the agent/team.";
		String actual = driver.findElement(By.xpath(".//*[@id='alerts']/div/div")).getText().trim();

		LogUtil.infoLog(ShowingPage.class, "Expected :" + expected);
		HtmlReportUtil.stepInfo("Expected: " + expected);

		LogUtil.infoLog(ShowingPage.class, "Actual :" + actual);
		HtmlReportUtil.stepInfo("Actual: " + actual);

		return expected.contains(actual);
	}

	public static boolean checkClientEmpty(WebDriver driver) {

		boolean status = false;
		String expected = "buyerPicker";
		String actual = "";
		List<WebElement> dropDowns = driver.findElements(By.cssSelector(".form-control.invalid"));
		for (WebElement element : dropDowns) {
			actual = element.getAttribute("id");
			if (actual.equalsIgnoreCase(expected)) {
				status = true;
			}

		}

		return status;
	}

	public static ArrayList<String> getAllClientsFromPopup(WebDriver driver) {

		// You are on Showing Page
		Select dropDown = new Select(driver.findElement(By.cssSelector("#buyerPicker")));
		ArrayList<String> clients = new ArrayList<String>();

		HtmlReportUtil.stepInfo("Getting all clients from Popup");
		LogUtil.infoLog(ClientsPage.class, "Getting all clients from Popup");

		List<WebElement> dd = dropDown.getOptions();

		for (WebElement e : dd) {

			if (!e.getText().contains("Choose a client..."))
				clients.add(e.getText());

		}
		return clients;

	}

	// propertyPicker
	public static ArrayList<String> getAllPropertiesFromPopup(WebDriver driver) {

		// You are on Showing Page
		Select dropDown = new Select(driver.findElement(By.cssSelector("#propertyPicker")));
		ArrayList<String> properties = new ArrayList<String>();

		HtmlReportUtil.stepInfo("Getting all properties from Popup");
		LogUtil.infoLog(ClientsPage.class, "Getting all properties from Popup");

		List<WebElement> dd = dropDown.getOptions();

		for (WebElement e : dd) {

			if (!e.getText().contains("Choose a property..."))
				properties.add(e.getText().toUpperCase());

		}
		return properties;

	}

	public static boolean checkScheduleDateEmpty(WebDriver driver) {

		boolean status = false;
		String expected = "error";
		String actual = "";
		actual = driver.findElement(By.cssSelector("#datetimepicker")).getAttribute("class");

		return actual.equalsIgnoreCase(expected);
	}

	public static boolean checkPopupHeading_ForEdit(WebDriver driver) throws Exception {
		boolean status = true;

		if (driver.findElement(By.xpath(".//*[@id='ssModalLabel']")).isDisplayed()) {
			String expected = "EDIT SHOWING";
			String actual = driver.findElement(By.xpath(".//*[@id='ssModalLabel']")).getText().toUpperCase();

			LogUtil.infoLog(ShowingPage.class, "Expected:" + expected);
			HtmlReportUtil.stepInfo("Expected: " + expected);

			LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
			HtmlReportUtil.stepInfo("Actual: " + actual);

			Thread.sleep(1000);

		} else {
			status = false;
			LogUtil.infoLog(ShowingPage.class, "Error! Not Found EDIT SHOWING");
			HtmlReportUtil.stepInfo("Error! Not Found EDIT SHOWING");
		}

		return status;
	}

	public static void clickSaveShowing(WebDriver driver) throws Exception {

		if (driver.findElement(By.xpath(".//*[@id='ssModal']/div/div/div[3]/div/button")).isDisplayed()) {
			driver.findElement(By.xpath(".//*[@id='ssModal']/div/div/div[3]/div/button")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Save Showing");
			HtmlReportUtil.stepInfo("Clicked Save Showing");
			Thread.sleep(1000);
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Save Showing");
			HtmlReportUtil.stepInfo("Error! Not Clicked Save Showing");
		}

	}

	public static void clickClosePopup(WebDriver driver) throws Exception {

		if (driver.findElement(By.xpath(".//*[@id='ssModal']/div/div/div[1]/button")).isDisplayed()) {
			driver.findElement(By.xpath(".//*[@id='ssModal']/div/div/div[1]/button")).click();
			LogUtil.infoLog(ShowingPage.class, "Clicked Close popup");
			HtmlReportUtil.stepInfo("Clicked Close popup");
			Thread.sleep(1000);
		} else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not Clicked Close popup");
			HtmlReportUtil.stepInfo("Error! Not Clicked Close popup");
		}

	}

	public static boolean checkScheduled_DeleteThisShowingUI(WebDriver driver) throws Exception {
		Thread.sleep(1000);
		// Return only first from the list

		LogUtil.infoLog(ShowingPage.class, "Checking Scheduled Delete this Showing UI");
		HtmlReportUtil.stepInfo("Checking Scheduled Delete this Showing UI");

		if (driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li")).size() > 1)
			return driver
					.findElement(
							By.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[2]"))
					.isDisplayed()
					&& driver
							.findElement(By
									.xpath("//*[@class='list-group scheduled-showings']/li[1]/div/div[3]/div/div/button[2]"))
							.isEnabled();
		else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not found Scheduled Delete his Showing UI ");
			HtmlReportUtil.stepInfo("Error! Not found Scheduled Delete his Showing UI ");
			return false;
		}

	}

	public static boolean checkCompleted_DeleteThisShowingUI(WebDriver driver) {

		// Return only first from the list

		LogUtil.infoLog(ShowingPage.class, "Checking Completed Delete This Showing UI ");
		HtmlReportUtil.stepInfo("Checking Completed Delete This Showing UI ");

		if (driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li")).size() > 1)
			return driver
					.findElement(By
							.xpath("//*[@class='list-group completed-showings']/li[1]/div/div[3]/div/div/button[@class='btn btn-default trash']"))
					.isDisplayed();
		else {
			LogUtil.infoLog(ShowingPage.class, "Error! Not found Completed Delete This Showing UI ");
			HtmlReportUtil.stepInfo("Error! Not found Completed Delete This Showing UI ");
			return false;
		}

	}

	public static List<WebElement> getScheduled(WebDriver driver) {

		return driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));
	}

	public static boolean checkLogo(WebDriver driver) {

		return driver.findElement(By.xpath("html/body/section/div[2]/div/div[1]/div[2]/img")).isDisplayed();

	}

	public static boolean checkHeading(WebDriver driver, String expected) {

		String actual = driver.findElement(By.cssSelector(".page-heading")).getText().trim();
		LogUtil.infoLog(ShowingPage.class, "Expected:" + expected);
		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		return expected.equalsIgnoreCase(actual);

	}

	public static void deleteAllScheduledRecords(WebDriver driver) throws InterruptedException {
		LogUtil.infoLog(ShowingPage.class, "Deleting all scheduled records");
		HtmlReportUtil.stepInfo("Deleting all scheduled records");

		List<WebElement> allScheduled = driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

		int size = (allScheduled.size() - 1);

		while (allScheduled.size() > 1) {

			allScheduled.get(0).findElement(By.xpath("div/div[3]/div/div/button[2]")).click();
			Thread.sleep(2000);
			allScheduled = driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

		}

		LogUtil.infoLog(ShowingPage.class, "Deleted all scheduled records");
		HtmlReportUtil.stepInfo("Deleted all scheduled records");

	}

	public static boolean checkMessageFor_AllDeletedScheduledRecords(WebDriver driver) {
		String message = "It looks like you don't have any scheduled showings yet. If you'd like to add some then click on the button above.";
		String actual = "";
		boolean status = true;
		List<WebElement> allmessages = driver
				.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li/div/div/p"));

		LogUtil.infoLog(ShowingPage.class, "Verifing message for scheduled");
		HtmlReportUtil.stepInfo("Verifing message for scheduled");

		for (WebElement msg : allmessages) {
			if (msg.getText().contains(message)) {
				actual = msg.getText();
				break;

			} else {
				status = false;
			}

		}

		LogUtil.infoLog(ShowingPage.class, "Expected:" + message);
		HtmlReportUtil.stepInfo("Expected:" + message);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		HtmlReportUtil.stepInfo("Actual:" + actual);

		return status;
	}

	public static void deleteAllCompletedRecords(WebDriver driver) throws InterruptedException {

		LogUtil.infoLog(ShowingPage.class, "Deleting all completed records");
		HtmlReportUtil.stepInfo("Deleting all completed records");

		List<WebElement> allCompleted = driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

		int size = (allCompleted.size() - 1);

		while (allCompleted.size() > 1) {

			allCompleted.get(0).findElement(By.xpath("div/div[3]/div/div/button[2]")).click();
			Thread.sleep(2000);
			allCompleted = driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

		}
		LogUtil.infoLog(ShowingPage.class, "Deleted all completed records");
		HtmlReportUtil.stepInfo("Deleted all completed records");

	}

	public static boolean checkMessageFor_AllDeletedCompletedRecords(WebDriver driver) {
		String message = "It looks like you don't have any completed showings yet. They are listed here once the scheduled showing they are based on completed.";
		String actual = "";
		boolean status = true;
		List<WebElement> allmessages = driver
				.findElements(By.xpath("//*[@class='list-group completed-showings']/li/div/div/p"));

		LogUtil.infoLog(ShowingPage.class, "Verifing message for completed");
		HtmlReportUtil.stepInfo("Verifing message for completed");

		for (WebElement msg : allmessages) {
			if (msg.getText().contains(message)) {
				actual = msg.getText();
				break;

			} else {
				status = false;
			}

		}

		LogUtil.infoLog(ShowingPage.class, "Expected:" + message);
		HtmlReportUtil.stepInfo("Expected:" + message);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		HtmlReportUtil.stepInfo("Actual:" + actual);

		return status;
	}

	public static void RestoreScheduled_AllRecords(WebDriver driver) throws Exception {

		LogUtil.infoLog(ShowingPage.class, "Restoring all Scheduled records");
		HtmlReportUtil.stepInfo("Restoring all Scheduled records");

		List<WebElement> allCompleted = driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

		int size = (allCompleted.size() - 1);

		while (allCompleted.size() > 1) {

			allCompleted.get(0).findElement(By.xpath("div/div[3]/div/div/button[1]")).click();
			Thread.sleep(2000);
			allCompleted = driver.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li"));

		}
		LogUtil.infoLog(ShowingPage.class, "Restored all Scheduled records");
		HtmlReportUtil.stepInfo("Restored all Scheduled records");

	}

	public static boolean checkMessageFor_AllScheduledRestored(WebDriver driver) {
		String message = "There are no scheduled showings in the trash.";
		String actual = "";
		boolean status = true;
		List<WebElement> allmessages = driver
				.findElements(By.xpath("//*[@class='list-group scheduled-showings']/li/div/div/p"));

		LogUtil.infoLog(ShowingPage.class, "Verifing message for scheduled");
		HtmlReportUtil.stepInfo("Verifing message for scheduled");

		for (WebElement msg : allmessages) {
			if (msg.getText().contains(message)) {
				actual = msg.getText();
				break;

			} else {
				status = false;
			}

		}

		LogUtil.infoLog(ShowingPage.class, "Expected:" + message);
		HtmlReportUtil.stepInfo("Expected:" + message);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		HtmlReportUtil.stepInfo("Actual:" + actual);

		return actual.equalsIgnoreCase(message);
	}

	public static void RestoreCompleted_AllRecords(WebDriver driver) throws Exception {

		LogUtil.infoLog(ShowingPage.class, "Restoring all completed records");
		HtmlReportUtil.stepInfo("Restoring all completed records");

		List<WebElement> allCompleted = driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

		int size = (allCompleted.size() - 1);

		while (allCompleted.size() > 1) {

			allCompleted.get(0).findElement(By.xpath("div/div[3]/div/div/button[1]")).click();
			Thread.sleep(2000);
			allCompleted = driver.findElements(By.xpath("//*[@class='list-group completed-showings']/li"));

		}
		LogUtil.infoLog(ShowingPage.class, "Restored all completed records");
		HtmlReportUtil.stepInfo("Restored all completed records");

	}

	public static boolean checkMessageFor_AllCompletedRestored(WebDriver driver) {
		String message = "There are no completed showings in the trash.";
		String actual = "";
		boolean status = true;
		List<WebElement> allmessages = driver
				.findElements(By.xpath("//*[@class='list-group completed-showings']/li/div/div/p"));

		LogUtil.infoLog(ShowingPage.class, "Verifing message for completed");
		HtmlReportUtil.stepInfo("Verifing message for completed");

		for (WebElement msg : allmessages) {
			if (msg.getText().contains(message)) {
				actual = msg.getText();
				break;

			} else {
				status = false;
			}

		}

		LogUtil.infoLog(ShowingPage.class, "Expected:" + message);
		HtmlReportUtil.stepInfo("Expected:" + message);

		LogUtil.infoLog(ShowingPage.class, "Actual:" + actual);
		HtmlReportUtil.stepInfo("Actual:" + actual);

		return actual.equalsIgnoreCase(message);
	}

	public static boolean checkThisShowingHasStartedMessage(WebDriver driver) {
		boolean status = true;

		List<WebElement> messages = driver
				.findElements(By.xpath("//*[text() [contains(.,'This showing has started.') ] ]"));
		if (messages.size() > 0) {

			if (messages.get(0).isDisplayed()) {
				LogUtil.infoLog(ShowingPage.class, "Message:" + messages.get(0).getText());
				HtmlReportUtil.stepInfo("Message:" + messages.get(0).getText());
			} else {
				status = false;
				LogUtil.infoLog(ShowingPage.class, "Message not displayed ");
				HtmlReportUtil.stepInfo("Message not displayed ");

			}

		} else {
			status = false;
			LogUtil.infoLog(ShowingPage.class, "Message not displayed");
			HtmlReportUtil.stepInfo("Message not displayed");
		}

		return status;

	}

	public static boolean checkClickToJoinButton(WebDriver driver) {
		boolean status = true;

		List<WebElement> buttons = driver.findElements(By.xpath("//*[text() [contains(.,'Click to Join') ] ]"));
		if (buttons.size() > 0) {
			if (buttons.get(0).isDisplayed() && buttons.get(0).isEnabled()) {
				LogUtil.infoLog(ShowingPage.class, "Click to Join button displayed and clickable");
				HtmlReportUtil.stepInfo("Click to Join button displayed and clickable");

			} else {
				status = false;
				LogUtil.infoLog(ShowingPage.class, "Click to Join button not displayed ");
				HtmlReportUtil.stepInfo("Click to Join button not displayed ");

			}

		} else {
			status = false;

		}

		return status;

	}


}// End of class
