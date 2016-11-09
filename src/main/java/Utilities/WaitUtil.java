package Utilities;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class WaitUtil {

	
	// This method will return WebElement After Given wait           
	public static WebElement FindWithWait(WebDriver driver,By locator, int seconds) throws Exception{
			// Sleep until the Element we want is visible or n seconds is over
			WebElement element=null;
			
			//Because if implict wait is set then fluint wait will not work
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			try{
						FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
								.withTimeout(seconds, TimeUnit.SECONDS)
								.pollingEvery(200, TimeUnit.MILLISECONDS)
								.ignoring(NoSuchElementException.class)
								.ignoring(StaleElementReferenceException.class)
								.ignoring(WebDriverException.class);
										 
					element=fluentWait.until(
								
								ExpectedConditions.visibilityOfElementLocated(locator)
								
								);
					return element;
						
			} catch(Exception e){
				
				throw new Exception("Timeout reached when searching for element! Time: " + seconds+" seconds " +"\n" +e.getMessage());
				
			}
			finally {
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
			
		}

		            
}
