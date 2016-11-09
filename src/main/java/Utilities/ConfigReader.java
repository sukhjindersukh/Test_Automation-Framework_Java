
package Utilities;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	
	public static Properties  loadPropertyFile(String filePath)
	{
		// Read from properties file
		File file = new File(filePath);
		Properties prop = new Properties();

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	

}
	
	
	
	public static String getValue(String key)
	{
		
		Properties prop = loadPropertyFile("src\\main\\resources\\ConfigFiles\\config.properties");
		//Open the URL in firefox browser
		String strKey = prop.getProperty(key);
		
		return strKey;
	}

	
	public static int getIntValue(String key)
	{
		Properties prop = loadPropertyFile("src\\main\\resources\\ConfigFiles\\config.properties");
		
		//Open the URL in firefox browser
		String strKey = prop.getProperty(key);
		
		return Integer.parseInt(strKey);
	}
	
}
