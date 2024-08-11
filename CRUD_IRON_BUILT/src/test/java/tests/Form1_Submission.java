package tests;

import java.awt.Desktop;
import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.Common_Element_Methods;
import Pages.Form1;
import Pages.Form2;

public class Form1_Submission extends BaseTest {

	@Test(priority = 1)
	public void testForm1Submission() {
		test = extent.createTest("Form 1 Submission Test", "Testing submission of Form 1");
		System.out.println("Test case 1:");
		System.out.println();

		try {
			if (driver == null) {
				System.out.println("Driver is null.");
				throw new NullPointerException("WebDriver is not initialized.");
			}

			// Navigate to Form 1 page
			driver.get("https://westeros.rolustech.com:44325/building-quote/");
			System.out.println("Navigated to Form 1 page.");

			Form1 formPage1 = new Form1(driver);
			System.out.println("Created Form1 instance.");

			// Generate a 5-digit random number for the last name
			String lastName = formPage1.generateRandomFiveDigitNumber();
			System.out.println("Generated last name: " + lastName);

			// Fill in the form fields
			formPage1.enterFirstName("Automated");
			formPage1.enterLastName(lastName);
			formPage1.enterEmail("john.doe@example.com");
			formPage1.enterPhone("1234567890");
			formPage1.enterWidth("100");
			formPage1.enterLength("200");
			formPage1.enterHeight("300");
			formPage1.enterZip("12345");

			// Select dropdown options
			formPage1.selectBuildingUse();
			formPage1.selectDateToBuild();
			formPage1.selectState();
			System.out.println("Form fields filled and options selected.");

			formPage1.clickSubmitButton();
			System.out.println("Submit button clicked.");

			// Log in if necessary
			formPage1.login("eversafe38", "Esu$er#38");
			System.out.println("Login attempted.");

			// Verify the success message
			formPage1.successCriteria_Search_record(lastName);
			System.out.println("Success criteria verified.");

			test.log(Status.PASS, "Form 1 submission successful");

		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			test.log(Status.FAIL, "Form 1 submission failed: " + e.getMessage());
			Assert.fail("Form 1 submission failed: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void testForm2Submission() {
		test = extent.createTest("Form 2 Submission Test", "Testing submission of Form 2");
		System.out.println("Test case 2:");
		System.out.println();

		try {
			if (driver == null) {
				System.out.println("Driver is null.");
				throw new NullPointerException("WebDriver is not initialized.");
			}

			// Navigate to Form 2 page
			driver.get("https://westeros.rolustech.com:44325/contact-us/");
			System.out.println("Navigated to Form 2 page.");

			Form2 formPage2 = new Form2(driver);
			Common_Element_Methods commonMethods = new Common_Element_Methods(driver);
			System.out.println("Created Form2 and Common_Element_Methods instances.");

			// Generate a 5-digit random number for the last name
			String lastName = formPage2.generateRandomFiveDigitNumber();
			System.out.println("Generated last name: " + lastName);

			// Fill in the form fields
			formPage2.enterFirstName("Automated");
			formPage2.enterLastName(lastName);
			formPage2.enterEmail("john.doe@example.com");
			formPage2.enterPhone("1234567890");
			commonMethods.enterWidth("100");
			commonMethods.enterLength("200");
			commonMethods.enterHeight("300");
			commonMethods.enterZip("12345");

			// Select dropdown options
			commonMethods.selectBuildingUse();
			commonMethods.selectDateToBuild();
			commonMethods.selectState();
			System.out.println("Form fields filled and options selected.");

			formPage2.clickSubmitButton();
			System.out.println("Submit button clicked.");

			// Verify the success message if applicable

			boolean loginresult = formPage2.login("eversafe38", "Esu$er#38");

			if (loginresult) {
				test.log(Status.PASS, "Login was succesfull");
			} else {
				test.log(Status.FAIL, "Form 2 submission FAILED");
			}

			// Verify success criteria if applicable
			boolean result = formPage2.successCriteria_Search_record(lastName);

			if (result) {
				test.log(Status.PASS, "Form 2 submission successful");
			} else {
				test.log(Status.FAIL, "Form 2 submission FAILED");
			}

		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			test.log(Status.FAIL, "Form 2 submission failed: " + e.getMessage());
			Assert.fail("Form 2 submission failed: " + e.getMessage());
		} finally {
			if (extent != null) {
				extent.flush();
				System.out.println("ExtentReports flushed.");
			}

			String filePath = System.getProperty("user.dir") + "/target/ExtentReports.html";
			File htmlFile = new File(filePath);

			// Check if Desktop is supported by the platform
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (htmlFile.exists()) {
					try {
						// Open the file in the default browser
						desktop.browse(htmlFile.toURI());
						System.out.println("Opened ExtentReports.html in the default browser.");
					} catch (Exception e) {
						System.out.println("Failed to open ExtentReports.html: " + e.getMessage());
					}
				} else {
					System.out.println("ExtentReports.html file not found at: " + filePath);
				}
			} else {
				System.out.println("Desktop is not supported. Cannot open ExtentReports.html automatically.");
			}
		}
	}

}