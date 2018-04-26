package com.automation.framework;

import org.testng.annotations.Test;



import org.testng.annotations.BeforeSuite;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Main {
	
	public WebDriver driver;
	public Properties properties;
	public Reporting report;
	public ExtentReports testReport;
	public ExtentTest test;

  @BeforeSuite
  public void beforeSuite() {
	  
	  intialiseTestReport();
	  System.out.println("intialiseTestReport");
	  getEnvironmentDetails();
	  System.out.println("getEnvironmentDetails");
	  intializeWebDriver();	  
	  
	  
	  
  }

  @AfterSuite
  public void afterSuite() {
	  
		if(driver!=null) {
			driver.quit();
		}
		
		try {
			Desktop.getDesktop().open(new File(report.getReportPath()));			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
  }
  
  public void intializeWebDriver() {
	  
	  String browser= properties.getProperty("Browser");
	  
	  System.out.println(browser);
	  switch(browser){ 
	  
	  case"Chrome":
		  System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		  driver = new ChromeDriver();
		  driver.manage().window().maximize();
		  break;
		  
	  case"Firefox":
		  System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
		  driver = new FirefoxDriver();
		  driver.manage().window().maximize(); 
		  break;
		  
	  }
	  
	  
	  
	  
  }
  
  public void getEnvironmentDetails() {
	 
	  
	  FileInputStream input =null;
	  
	  try {
		  properties = new Properties();
		  input = new FileInputStream("src/test/resources/EnvironmentProperties.properties");
		  properties.load(input);
	  }
	  catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	  
 }
  
  public void intialiseTestReport() {
	  
	  report = new Reporting("Report");
	  testReport=  report.initialize();
	  testReport.loadConfig((new File("src/test/resources/extent-report-config.xml")));
	  
  }

}
