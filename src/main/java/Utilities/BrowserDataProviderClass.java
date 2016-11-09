package Utilities;
import org.testng.annotations.DataProvider;

public class BrowserDataProviderClass {
	
	
	@DataProvider(name = "browser-data-provider")
    public static Object[][] dataProviderMethod() throws Exception 
    {
		Object[][] browserData=ExcelDataUtil.getBrowsersList();
			
        return browserData;
    }
}
