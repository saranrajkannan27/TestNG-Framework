package com.automation.framework;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class TestSuite extends ReusableLibrary {
	
	@BeforeClass
	public void launchapp() {
		driver.get(properties.getProperty("AppUrl"));

		if (driver.getTitle().equals(properties.getProperty("HomePageTitle"))) {
			addTestStep(LogStatus.PASS,
					"Application with URL  " + properties.getProperty("AppUrl") + " is launched successfully");
		} else {
			addTestStep(LogStatus.FAIL,
					"Application with URL  " + properties.getProperty("AppUrl") + " is not launched successfully");
		}

		Assert.assertEquals(driver.getTitle(), properties.getProperty("HomePageTitle"));
	}
	

	@BeforeTest
	public void startTest() {
		test = testReport.startTest(properties.getProperty("TestDescription"));
	}
	

	@AfterTest
	public void endTest() {

		testReport.endTest(test);
		testReport.flush();
	}
	
	@Test
	public void dummytest() {
		System.out.println("Saran");
		addInfo(LogStatus.PASS, "TestNG");
		
	}

}

