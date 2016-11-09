package Utilities;

import java.util.Date;

public class TestResults {
	private Date runDateTime;
	private int runId;
	private String status,remarks,screenshot_ref;
	public String getScreenshot_ref() {
		return screenshot_ref;
	}

	public void setScreenshot_ref(String screenshot_ref) {
		this.screenshot_ref = screenshot_ref;
	}


	private double actualTime=0;
	
	
	
	public TestResults() {
		super();
		
		
		this.runDateTime = new Date();
		this.runId = 0;
		this.status = "";
		this.remarks = "";
		this.actualTime = 0;
	}
	
	public TestResults(Date runDateTime, int runId, String status, String remarks, double actualTime) {
		super();
		this.runDateTime = runDateTime;
		this.runId = runId;
		this.status = status;
		this.remarks = remarks;
		this.actualTime = actualTime;
	}


	public Date getRunDateTime() {
		return runDateTime;
	}


	public void setRunDateTime(Date runDateTime) {
		this.runDateTime = runDateTime;
	}


	public int getRunId() {
		return runId;
	}


	public void setRunId(int runId) {
		this.runId = runId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public double getActualTime() {
		return actualTime;
	}


	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}
	
	
	
	
	
	

}
