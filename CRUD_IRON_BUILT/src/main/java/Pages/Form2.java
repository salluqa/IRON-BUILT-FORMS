package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Form2 extends Common_Element_Methods {

	public Form2(WebDriver driver) {
		super(driver);
	}

	// Overriding locators for Form2-specific fields
	protected By firstName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"FIRST NAME*\"][name=\"fname\"]");
	protected By lastName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"LAST NAME*\"][name=\"lname\"]");
	protected By emailName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"EMAIL*\"][name=\"email\"]");
	protected By phone = By.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"PHONE*\"][name=\"phone\"]");

	protected By submitButton = By.cssSelector(
			"input.wpcf7-form-control.wpcf7-submit[type=\"submit\"][value=\"Submit for Free Price Quote\"]");

	@Override
	public void enterFirstName(String name) {
		System.out.println("I am in form2 enterFirstName");
		enterText(firstName, name);
	}

	@Override
	public void enterLastName(String name) {
		enterText(lastName, name);
	}

	@Override
	public void enterEmail(String email) {
		enterText(emailName, email);
	}

	@Override
	public void enterPhone(String phoneNumber) {
		enterText(phone, phoneNumber);
	}

	@Override
	public void clickSubmitButton() {
		try {
			WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			Assert.assertTrue(submitBtn.isDisplayed(), "Submit button is not displayed.");
			submitBtn.click();
		} catch (Exception e) {
			Assert.fail("Failed to click the submit button: " + e.getMessage());
		}
	}
}
