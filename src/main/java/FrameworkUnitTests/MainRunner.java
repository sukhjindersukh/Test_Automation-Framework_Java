package FrameworkUnitTests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.NumberOfDocuments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sun.xml.internal.fastinfoset.sax.Properties;
import com.thoughtworks.selenium.webdriven.commands.FireEvent;

import Utilities.ExcelDataUtil;
import Utilities.TestConfig;
import Utilities.Utility;

public class MainRunner {

	public static void main(String[] args) throws Throwable {
		String htmlReportFile = System.getProperty("user.dir")+"\\"+ Utility.getValue("HtmlReportFullPath");
		File f = new File(htmlReportFile);
		if(f.exists())
		{
			try {
				Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" +htmlReportFile +"\"");
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
         

		
		
		
			
	}

}
