package FrameworkUnitTests;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class JavaSample {

  public static final String USERNAME = "sukhjindersingh3";
  public static final String AUTOMATE_KEY = "tB44s7sLq3zeqmCCyhUX";
  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

  public static void main(String[] args) throws Exception {

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("platform", "ios");
    caps.setCapability("device", "iPhone 6S Plus");
    caps.setCapability("browser", "iPhone");
    caps.setCapability("browser_version", "9.1");
    
    
  //  caps.setCapability("os_version", "10");
    
    caps.setCapability("browserstack.debug", "true");
    caps.setCapability("browserstack.video", "true");
    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
    
    
    
    driver.get("http://www.google.com");
    WebElement element = driver.findElement(By.name("q"));

    element.sendKeys("BrowserStack");
    element.submit();

    System.out.println(driver.getTitle());
    driver.quit();

  }
}
