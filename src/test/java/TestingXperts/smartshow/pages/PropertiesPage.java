package TestingXperts.smartshow.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.GlobalUtil;
import Utilities.HtmlReportUtil;
import Utilities.LogUtil;

public class PropertiesPage {
	


	
	//*[@class='list-group-item buyer']
	
	public static ArrayList<String> getAllProperties(WebDriver driver) throws Exception{
		
		PageMenu.clickProperties(driver);
		Thread.sleep(3000);
		
		
		List<WebElement> properties =driver.findElements(By.xpath("//*[@class='list-group-item property']"));
		
		HtmlReportUtil.stepInfo("Getting all properties from properties page");
		LogUtil.infoLog(ClientsPage.class, "Getting all properties from properties page");
		
		for (WebElement property : properties){
			
			//*[@class='list-group-item buyer'][1]/div/div[@class='col-sm-3']/h3
			
			String value="";
			String value2="";
			String strXpath="div/div[1]/h3";
			
			//Client Full Name
			value =property.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			
			strXpath ="div/div[2]/small[2]";
			value2=property.findElement(By.xpath(strXpath)).getText().trim().toUpperCase();
			
			
			String temp="";
			
			if(value2.contains("PRICE")){
				 int i =value2.indexOf('P');
				 
				 temp=value2.substring(0, i-1);
				 
			 }else 
				 temp =value2;
			
			
			 String numberOnly= temp.replaceAll("[^0-9]", "");
			 
			
			 if(!numberOnly.equals(""))
				 {value2 =" (" + numberOnly +")";
				 GlobalUtil.listOfProperties.add(value + value2);
				 }
			 else
				 GlobalUtil.listOfProperties.add(value);
						
		}
				
		
		return GlobalUtil.listOfProperties;
	}

	


}
