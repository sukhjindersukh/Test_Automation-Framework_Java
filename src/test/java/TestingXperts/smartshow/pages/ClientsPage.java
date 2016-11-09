package TestingXperts.smartshow.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.LogUtil;

public class ClientsPage {

	
	//*[@class='list-group-item buyer']
	
	public static ArrayList<String> getAllClients(WebDriver driver) throws Exception{
		
		PageMenu.clickClients(driver);
		Thread.sleep(3000);
		
		
		List<WebElement> clients =driver.findElements(By.xpath("//*[@class='list-group-item buyer']"));
		
		HtmlReportUtil.stepInfo("Getting all clients from Client page");
		LogUtil.infoLog(ClientsPage.class, "Getting all clients from Client page");
		
		for (WebElement client : clients){
			
			//*[@class='list-group-item buyer'][1]/div/div[@class='col-sm-3']/h3
			
			String value="";
			String strXpath="div/div[@class='col-sm-3']/h3";
			
			//Client Full Name
			value =client.findElement(By.xpath(strXpath)).getText().trim();
			
			GlobalUtil.listOfClients.add(value);
			
//			strXpath="div/div[@class='col-sm-7 item-meta']/small";
//			//Client Full Name
//			value =client.findElement(By.xpath(strXpath)).getText().trim();
//			
			
			
				
						
		}
				
		
		return GlobalUtil.listOfClients;
	}

	
}
