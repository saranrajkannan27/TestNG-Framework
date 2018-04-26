package com.automation.framework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;


import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;

import org.apache.commons.io.FileUtils;

public class ReusableLibrary extends Main{
	
	
	public String getCurrentTime(String format) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);  
	    Date date = new Date(); 
	    return formatter.format(date);
		
		
	}
	
	public void waitCommand(final By by) throws TimeoutException{
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.withTimeout(20, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			
			
			public Boolean apply(WebDriver driver) {
				boolean displayed = driver.findElement(by).isEnabled();
				if (displayed) {
					return true;
				} else {
					return false;
				}
			}

		};
		wait.until(function);	
		
	}
	
	public void click(By by) {

		try {
			waitCommand(by);
			driver.findElement(by).click();
			test.log(LogStatus.PASS, "Element with locator - <b>" + by + "</b> is clicked successfully",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
		}catch(Exception e){
			test.log(LogStatus.FAIL, "Element with locator - <b>" + by + "</b> is not clicked successfully",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
			test.log(LogStatus.FAIL, e.getMessage());

		}

	}
	
	public void enterText(By by, String textToEnter) {
		try {
			waitCommand(by);
			driver.findElement(by).sendKeys(textToEnter);
			test.log(LogStatus.PASS,
					"Text '<b>" + textToEnter + "</b>' is entered successfully in element with locator - <b>" + by
							+ "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Text '<b>" + textToEnter + "</b>' is not entered successfully in element with locator - <b>" + by
							+ "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
			test.log(LogStatus.FAIL, e.getMessage());
		}
	}
	
	public String screenShot() {
		
		String screenshotName=null;
		 screenshotName=getCurrentTime("dd_MMM_yyyy_hh_mm_ss_SSS");
		TakesScreenshot scr= ((TakesScreenshot)driver);
		File Scrfile=scr.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(Scrfile,new File("./Results/" + report.getReportFolder() + "/screenshots/" + screenshotName + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshotName;
		
		
	}
	
	public void addInfo(LogStatus status, String testInfo) {

		test.log(status, "<b>" + testInfo + "</b>", "");

	}
	
	public void addTestStep(LogStatus status, String testInfo) {

		test.log(status, testInfo,
				"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));


	}
}
