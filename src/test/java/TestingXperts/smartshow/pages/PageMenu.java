package TestingXperts.smartshow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.HtmlReportUtil;
import Utilities.LogUtil;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class PageMenu {

  
   

  public static String getTitle(WebDriver driver) {
    return driver.getTitle();
  }
  
  
  public static void clickDashboard(WebDriver driver){
		
		driver.findElement(By.partialLinkText("Dashboard")).click();
	}
	
public static void clickAgents(WebDriver driver){
		
		driver.findElement(By.partialLinkText("Agents")).click();
	}
	
public static void clickClients(WebDriver driver){
	
	driver.findElement(By.partialLinkText("Clients")).click();
	LogUtil.infoLog(PageMenu.class, "Navigated to client page");
	HtmlReportUtil.stepInfo("Navigated to client page");
	
}

	
public static void clickProperties(WebDriver driver){
	
	driver.findElement(By.partialLinkText("Properties")).click();
	
	LogUtil.infoLog(PageMenu.class, "Navigated to Property page");
	HtmlReportUtil.stepInfo("Navigated to Property page");
}

public static void clickShowing(WebDriver driver){
	
	
	if(driver.findElement(By.partialLinkText("Showings")).isDisplayed())
		driver.findElement(By.partialLinkText("Showings")).click();
	else{
		driver.findElement(By.cssSelector(".showings>a")).click();
	}
	
	
}


public static void clickHelp(WebDriver driver){
	
	driver.findElement(By.partialLinkText("Help")).click();
}

public static void clickUserName(WebDriver driver){
	
	driver.findElement(By.id("username-brand")).click();
	
}

public static void clickLogout(WebDriver driver) throws Exception{
	
	clickUserName(driver);
	Thread.sleep(500);
	driver.findElement(By.id("logout")).click();
	
}


public static void clickProfile(WebDriver driver) throws Exception{
	
	clickUserName(driver);
	Thread.sleep(500);
	driver.findElement(By.partialLinkText("profile")).click();
	
}

	
public static void clickLiveCATEGORIESLink(WebDriver driver){
		
		driver.findElement(By.partialLinkText("Live CATEGORIES")).click();
	}
	

public static void clickFAQLink(WebDriver driver){
	
	driver.findElement(By.partialLinkText("FAQs")).click();
}


public static void clickJUSTBUYLIVETIMESLink(WebDriver driver){
	
	driver.findElement(By.partialLinkText("JUST BUY LIVE TIMES")).click();
}


public static void clickCONTACTLink(WebDriver driver){
	
	driver.findElement(By.partialLinkText("CONTACT")).click();
}

public static String getCurrentUser(WebDriver driver){
	
	return driver.findElement(By.id("username-brand")).getText();
	
}  
  
  
  

}
