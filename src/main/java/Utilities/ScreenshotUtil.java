package Utilities;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

public class ScreenshotUtil {
//	public static String takeScreenshot(WebDriver driver, String testCaseID) throws Exception 
//    {
//		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		System.out.println("Taking screen shot");
//		String path=ConfigReader.getValue("screenshotPath")+"\\"+testCaseID+".jpg";
//		System.out.println("File path:" + path);
//		File img =new File(path);
//		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\ExecutionReports\\HtmlReport\\"+path));
//		return img.getPath();
//    }

	
	public static String takeScreenshot(WebDriver driver, String testCaseID) throws IOException {
		String path=ConfigReader.getValue("screenshotPath")+"\\"+testCaseID+".jpg";
		Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(100)).takeScreenshot(driver);
		File src = new File(System.getProperty("user.dir")+"\\ExecutionReports\\HtmlReport\\"+path);
		ImageIO.write(screenshot.getImage(), "PNG", src);
		return src.getPath();
	}
	
	
}


