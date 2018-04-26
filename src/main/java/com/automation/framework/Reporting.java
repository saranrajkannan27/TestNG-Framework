package com.automation.framework;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;



public class Reporting extends ReusableLibrary {

	private String relativePath;
	private String reportName;
	private String reportFolder;
	private String reportPath;

	public Reporting(String reportName) {
		setRelativePath();
		setReportName(reportName);
		setReportFolder();
		setReportPath();
	}
	
	public ExtentReports initialize() {	
		
		return new ExtentReports(this.reportPath, true, NetworkMode.OFFLINE);
		
	}

	public void setRelativePath() {
		this.relativePath = System.getProperty("user.dir");
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setReportFolder() {
		this.reportFolder = getCurrentTime("dd_MMM_yyyy_hh_mm_ss");
	}
	
	public  String getReportFolder() {
		return this.reportFolder;
	}

	public void setReportPath() {
		this.reportPath = this.relativePath + "/Results/" + reportFolder + "/" + reportName + ".html";
	}
	
	public String getReportPath() {
		return this.reportPath;
	}

}
