package Utilities;

public class CommonSettings {
	
	private  String appType, appEnviornment,projectName;
	private  String emailOutput, emailId,htmlReport, xlsReport,testLogs, defectMgmt,testMgmt;
	
	
	
	
	public CommonSettings(String projectName,String appType, String appEnviornment, String emailOutput, String emailId, String htmlReport,
			String xlsReport, String testLogs, String defectMgmt, String testMgmt) {
		super();
		this.projectName=projectName;
		this.appType = appType;
		this.appEnviornment = appEnviornment;
		this.emailOutput = emailOutput;
		this.emailId = emailId;
		this.htmlReport = htmlReport;
		this.xlsReport = xlsReport;
		this.testLogs = testLogs;
		this.defectMgmt = defectMgmt;
		this.testMgmt = testMgmt;
		
	}
		
	
	public CommonSettings() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setProjectName(String projectName){
		this.projectName=projectName;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppEnviornment() {
		return appEnviornment;
	}
	public void setAppEnviornment(String appEnviornment) {
		this.appEnviornment = appEnviornment;
	}
	public String getEmailOutput() {
		return emailOutput;
	}
	public void setEmailOutput(String emailOutput) {
		this.emailOutput = emailOutput;
	}
	public String getEmailIds() {
		return emailId;
	}
	public void setEmailIds(String emailId) {
		this.emailId = emailId;
	}
	public String getHtmlReport() {
		return htmlReport;
	}
	public void setHtmlReport(String htmlReport) {
		this.htmlReport = htmlReport;
	}
	public String getXlsReport() {
		return xlsReport;
	}
	public void setXlsReport(String xlsReport) {
		this.xlsReport = xlsReport;
	}
	public String getTestLogs() {
		return testLogs;
	}
	public void setTestLogs(String testLogs) {
		this.testLogs = testLogs;
	}
	public String getDefectMgmt() {
		return defectMgmt;
	}
	public void setDefectMgmt(String defectMgmt) {
		this.defectMgmt = defectMgmt;
	}
	public String getTestMgmt() {
		return testMgmt;
	}
	public void setTestMgmt(String testMgmt) {
		this.testMgmt = testMgmt;
	}
	
	

}
