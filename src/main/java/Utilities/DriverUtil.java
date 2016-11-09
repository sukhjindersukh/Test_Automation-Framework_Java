package Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverUtil {
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	public WebDriver driver;
	private static HashMap<String, String> checkLogin = new HashMap();
	public static WebDriver getBrowser(String browserName) throws Exception {
		WebDriver browser = null;
			if (browserName.equalsIgnoreCase("Chrome")) {
				// Write code for chrome
				browser =drivers.get(browserName);
				if(browser==null){
					File chromeExecutable = new File(ConfigReader.getValue("ChromeDriverPath"));
					System.setProperty("webdriver.chrome.driver", chromeExecutable.getAbsolutePath());
					browser = new ChromeDriver();
					drivers.put("Chrome", browser);
				}//End if
			}
			else if (browserName.equalsIgnoreCase("IE")) {
				// Write code for chrome
				browser =drivers.get(browserName);
				if(browser==null){
					File ieExecutable = new File(ConfigReader.getValue("IEDriverPath"));
					System.setProperty("webdriver.ie.driver", ieExecutable.getAbsolutePath());
					DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
//					capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
//							true);
					capabilitiesIE.setCapability("ie.ensureCleanSession", true);
					capabilitiesIE.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
					browser = new InternetExplorerDriver(capabilitiesIE);
					drivers.put("IE", browser);
					checkLogin.put(browserName, "Y");
				}
				
			}
			else {
				// Getting Firefox Browser
				browser =drivers.get("Firefox");
				if(browser==null){
					browser = new FirefoxDriver();
					drivers.put("Firefox", browser);
				}
			}
			browser.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			browser.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			browser.manage().deleteAllCookies();
			browser.manage().window().maximize();
			return browser;
	}// End of function


	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) {
		String browser_version = null;
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			System.out.println(uAgent);
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
				browser_version = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf("Windows") - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browser_version = "11.0";
			} else {
				browser_version = "0.0";
			}
		} else {
			// Browser version for Firefox and Chrome
			browser_version = cap.getVersion();// .split(".")[0];
		}
		String browserversion = browser_version.substring(0, browser_version.indexOf("."));
		return browsername + " " + browserversion;
	}

	public static String getOSName() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "Windows";
		} else if (os.contains("nux") || os.contains("nix")) {
			return "Linux";
		} else if (os.contains("mac")) {
			return "Mac";
		} else if (os.contains("sunos")) {
			return "Solaris";
		} else {
			return "Other";
		}
	}//End function
	
	public static void closeAllDriver() {
		System.out.println("Closing Browsers");
		
		for (String key : drivers.keySet()) {
			drivers.get(key).quit();
		}
	}
}//End class
