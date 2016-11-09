package TestingXperts.smartshow.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.GlobalUtil;

public class AgentPage {
	
	public static String getCurrentUserInfo(WebDriver driver) throws Exception{
		
		GlobalUtil.currentUserEmail= PageMenu.getCurrentUser(driver);
		PageMenu.clickAgents(driver);
		Thread.sleep(2000);
		
		
		List<WebElement> agents =driver.findElements(By.xpath("//*[@class='agent']"));
		
		for (WebElement agent : agents){
			
			String strXpath ="//*[text() [contains(.,'" + GlobalUtil.currentUserEmail+"') ] ]";
			
			if(driver.findElements(By.xpath(strXpath)).size()>0)
			{
			strXpath ="//*[text() [contains(.,'" + GlobalUtil.currentUserEmail+"') ] ]/../li";
			List<WebElement>agentInfo=agent.findElements(By.xpath(strXpath));
			//User Type 
			GlobalUtil.currentUserType=agentInfo.get(0).getText().trim();
			
			GlobalUtil.currentUserFullName =agentInfo.get(1).getText().trim();
			break;
			}
			
						
		}
				
		
		return GlobalUtil.currentUserType + "," +GlobalUtil.currentUserFullName+","+ GlobalUtil.currentUserEmail;
	}

}
