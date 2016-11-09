package TestingXperts.smartshow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.ConfigReader;

public class LoginPage  {

	private static boolean loginStatus=false;
	
	public static void doLogin(WebDriver driver, String userName, String password) throws Exception{
		
		enterUserName(driver,userName);
		enterPassword(driver,password);
		clickLoginButton(driver);
		loginStatus=true;
		
		
	}
	
	public static void enterUserName(WebDriver driver,String uName) throws Exception{
		
		driver.findElement(By.name("email")).clear();
		
		//driver.findElement(By.xpath(".//*[@id='login-form']/form/div[2]/div/div/input")).clear();
		Thread.sleep(1000);
		//driver.findElement(By.xpath(".//*[@id='login-form']/form/div[2]/div/div/input")).sendKeys(ConfigReader.getValue("loginUser"));
		
		driver.findElement(By.name("email")).sendKeys(ConfigReader.getValue("loginUser"));
		
		Thread.sleep(1000);
		
	}
	
	public static void enterPassword(WebDriver driver,String uName) throws Exception{
		driver.findElement(By.xpath(".//*[@id='login-form']/form/div[3]/div/div/input")).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='login-form']/form/div[3]/div/div/input")).sendKeys(ConfigReader.getValue("loginPassword"));
		Thread.sleep(1000);
		
	}
	
	
	public static void clickLoginButton(WebDriver driver) throws Exception{
		
		
		driver.findElement(By.xpath(".//*[@id='login-form']/form/button")).click();
		Thread.sleep(2000);
	}
	

	public static boolean isLogin(WebDriver driver){
		String loginUrl="https://testing.smartshow.com/login";
		
		if(loginUrl.equalsIgnoreCase(driver.getCurrentUrl())){
			if(driver.findElement(By.name("email")).getAttribute("value").isEmpty() )
				loginStatus=false;
		}
				
		return loginStatus;
	} 
	
}
