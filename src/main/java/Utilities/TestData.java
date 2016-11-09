package Utilities;

public class TestData {

	private String projectID,  suiteName, testId, testDesc, complexity,testPlatformInfo;
	private double expectedTime = 0;
	
	
	

	public TestData() {
		super();
		this.projectID = "";
		
		this.suiteName = "";
		this.testId = "";
		this.testDesc = "";
		this.complexity = "";
		this.testPlatformInfo = "";
		this.expectedTime = 0;
	}
	
	
	
	public TestData(String projectID, String suiteName, String testId, String testDesc,
			String complexity, String testPlatformInfo, double expectedTime) {
		super();
		this.projectID = projectID;
		
		this.suiteName = suiteName;
		this.testId = testId;
		this.testDesc = testDesc;
		this.complexity = complexity;
		this.testPlatformInfo = testPlatformInfo;
		this.expectedTime = expectedTime;
	}


	public String getProjectID() {
		return projectID;
	}


	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getSuiteName() {
		return suiteName;
	}


	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}


	public String getTestId() {
		return testId;
	}


	public void setTestId(String testId) {
		this.testId = testId;
	}


	public String getTestDesc() {
		return testDesc;
	}


	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}


	public String getComplexity() {
		return complexity;
	}


	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}


	public String getTestPlatformInfo() {
		return testPlatformInfo;
	}


	public void setTestPlatformInfo(String testPlatformInfo) {
		this.testPlatformInfo = testPlatformInfo;
	}


	public double getExpectedTime() {
		return expectedTime;
	}


	public void setExpectedTime(double expectedTime) {
		this.expectedTime = expectedTime;
	}
	

}
