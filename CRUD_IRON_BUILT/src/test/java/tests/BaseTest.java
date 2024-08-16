package tests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utils.WebDriverManager;

public class BaseTest {
	protected WebDriver driver;
	protected static ExtentReports extent;
	protected static ExtentSparkReporter spark;
	protected ExtentTest test;

	@BeforeSuite
	public void setUpSuite() {
		System.out.println("Starting setup of ExtentReports suite...");

		// Delete the existing report file to start fresh
		File reportFile = new File("target/ExtentReports.html");
		if (reportFile.exists()) {
			if (reportFile.delete()) {
				System.out.println("Existing report file deleted.");
			} else {
				System.out.println("Failed to delete existing report file.");
			}
		}

		// Initialize ExtentReports
		extent = new ExtentReports();
		spark = new ExtentSparkReporter("target/ExtentReports.html");
		extent.attachReporter(spark);
		System.out.println("ExtentReports suite setup is complete.");
	}

	@BeforeMethod
	public void setUp() {
		System.out.println("Starting setup of WebDriver and test case...");
		driver = WebDriverManager.getDriver();
		if (driver == null) {
			System.out.println("WebDriver initialization has failed.");
		} else {
			System.out.println("WebDriver initialized successfully.");
		}
		driver.manage().window().maximize();
		System.out.println("Test case setup complete.");
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("Tearing down test case...");
		if (driver != null) {
//            driver.quit();
			System.out.println("WebDriver quit successfully.");
		} else {
			System.out.println("WebDriver was not initialized.");
		}
		if (extent != null) {
			extent.flush();
			System.out.println("ExtentReports flushed.");
		} else {
			System.out.println("ExtentReports was not initialized.");
		}
	}

	@AfterSuite
	public void tearDownSuite() {
		System.out.println("Starting teardown of ExtentReports suite...");
		if (extent != null) {
			extent.flush();
			System.out.println("ExtentReports flushed.");
		} else {
			System.out.println("ExtentReports was not initialized.");
		}
		System.out.println("ExtentReports suite teardown complete.");
	}
}
