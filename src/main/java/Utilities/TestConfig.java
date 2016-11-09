package Utilities;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {
	private String suiteName,browserName,suiteId;
	private List<String> testsList = new ArrayList<String>(); 
	
	
	public TestConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestConfig(String suiteName, String browserName, String suiteId, List<String> testsList) {
		super();
		this.suiteName = suiteName;
		this.browserName = browserName;
		this.suiteId = suiteId;
		this.testsList = testsList;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getSuiteId() {
		return suiteId;
	}

	public void setSuiteId(String suiteId) {
		this.suiteId = suiteId;
	}

	public List<String> getTestsList() {
		return testsList;
	}

	public void setTestsList(List<String> testsList) {
		this.testsList = testsList;
	}
 
	
	
	
	
}
