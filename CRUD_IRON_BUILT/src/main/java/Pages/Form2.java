package Pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Form2 {
	private WebDriver driver;
	private WebDriverWait wait;
	private ExtentTest test; // Declare test here
	// Form 1 specific elements
	protected By firstName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"FIRSrT NAME*\"][name=\"fname\"]");
	protected By lastName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"LAST NAME*\"][name=\"lname\"]");
	protected By emailName = By
			.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"EMAIL*\"][name=\"email\"]");
	protected By phone = By.cssSelector("input.wpcf7-form-control.wpcf7-text[placeholder=\"PHONE*\"][name=\"phone\"]");

	protected By zip = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"zip\"]");
	protected By width = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"width\"]");
	protected By length = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"length\"]");
	protected By height = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"height\"]");
	protected By dateToBuild = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"datetobuild\"] select");
	protected By state = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"state\"] select");
	protected By submitButton = By.cssSelector(
			"input.wpcf7-form-control.wpcf7-submit[type=\"submit\"][value=\"Submit for Free Price Quote\"]");
	protected By successMessage = By.cssSelector(".elementor-widget-container h1");
	protected By buildingUse = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"builduse\"] select");
	protected By fullNameElement = By.cssSelector("div.relate-field-container a.Leads");
	protected By name = By.name("username");
	protected By passwordField = By.name("password");
	protected By loginButton = By.name("login_button"); // Assuming the login button locator
	protected By leadsListHeader = By.cssSelector(".list-headerpane .ellipsis_inline");
	protected By searchBar1 = By.className("search-name");

	public Form2(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.test = test; // In
	}

	// Methods to interact with elements
	public void enterFirstName(String name) {
		enterText(firstName, name);
	}

	public void enterLastName(String name) {
		enterText(lastName, name);
	}

	public void enterEmail(String email) {
		enterText(emailName, email);
	}

	public void enterPhone(String phoneNumber) {
		enterText(phone, phoneNumber);
	}

	public void enterWidth(String widthValue) {
		enterText(width, widthValue);
	}

	public void enterLength(String lengthValue) {
		enterText(length, lengthValue);
	}

	public void enterHeight(String heightValue) {
		enterText(height, heightValue);
	}

	public void enterZip(String zipCode) {
		enterText(zip, zipCode);
	}

	public void selectBuildingUse() {
		selectRandomOption(buildingUse);
	}

	public void selectDateToBuild() {
		selectRandomOption(dateToBuild);
	}

	public void selectState() {
		selectRandomOption(state);
	}

	private void enterText(By locator, String text) {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			Assert.assertTrue(element.isDisplayed(), "Element not displayed: " + locator);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			String failureMessage = "Failed to enter text in element: " + locator + " on " + " - " + e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage);
		}
	}

	private void selectRandomOption(By dropdownLocator) {
		try {
			Thread.sleep(10000);
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
			Assert.assertTrue(dropdownElement.isDisplayed(), "Dropdown not displayed: " + dropdownLocator);
			Select dropdown = new Select(dropdownElement);
			List<WebElement> options = dropdown.getOptions();
			Random random = new Random();
			int index = random.nextInt(options.size());
			dropdown.selectByIndex(index);
		} catch (Exception e) {
			String failureMessage = "Failed to select random option in dropdown: " + dropdownLocator + " on " + " - "
					+ e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage);
		}
	}

	public void clickSubmitButton() {
		try {
			WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			Assert.assertTrue(submitBtn.isDisplayed(), "Submit button is not displayed.");
			submitBtn.click();
		} catch (Exception e) {
			String failureMessage = "Failed to click the submit button on Form 1: " + e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage);
		}
	}

	public boolean successCriteria_Search_record(String expectedMessage) {
		try {
			WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar1));
			searchBar.sendKeys(expectedMessage);
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameElement));
			String fullName = dropdownElement.getText();
			String[] nameParts = fullName.split(" ");
			String lastName = nameParts[nameParts.length - 1];
			return expectedMessage.equals(lastName);
		} catch (Exception e) {
			Assert.fail("Failed to search the record in Form 1: " + e.getMessage());
			return false;
		}
	}

	public String generateRandomFiveDigitNumber() {
		return String.valueOf((int) (Math.random() * 90000) + 10000);
	}

	public boolean login(String username, String password) {
		driver.get("http://18.235.97.223/SugarCRM-Staging/#Leads");
		try {
			WebElement usernameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(name));
			WebElement passwordEl = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
			WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
			usernameEl.sendKeys(username);
			passwordEl.sendKeys(password);
			loginBtn.click();
			WebElement leadsHeaderEl = wait.until(ExpectedConditions.visibilityOfElementLocated(leadsListHeader));
			return true;
		} catch (Exception e) {
			Assert.fail("Login failed on Form 1: " + e.getMessage());
			return false;
		}
	}
}
