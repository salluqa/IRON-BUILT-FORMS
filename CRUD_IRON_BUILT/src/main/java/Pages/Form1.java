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
import org.testng.Assert; // Correct import for TestNG Assert

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Form1 {
	private WebDriver driver;
	private WebDriverWait wait;
	private ExtentTest test; // Declare test here

	// Form 1 specific elements
	protected By firstName = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"fname\"]");
	protected By lastName = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"lname\"]");
	protected By emailName = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"email\"]");
	protected By phone = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"phone\"]");
	protected By zip = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"zip\"]");
	protected By width = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"width\"]");
	protected By length = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"length\"]");
	protected By height = By.cssSelector(".wpcf7-form-control-wrap > input[name=\"height\"]");
	protected By dateToBuild = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"datetobuild\"] select");
	protected By state = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"state\"] select");
	protected By submitButton = By.cssSelector("input.wpcf7-submit");
	protected By successMessage = By.cssSelector(".elementor-widget-container h1");
	protected By buildingUse = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"builduse\"] select");
	protected By fullNameElement = By.cssSelector("div.relate-field-container a.Leads");
	protected By name = By.name("username");
	protected By passwordField = By.name("password");
	protected By loginButton = By.name("login_button"); // Assuming the login button locator
	protected By leadsListHeader = By.cssSelector(".list-headerpane .ellipsis_inline");
	protected By searchBar1 = By.className("search-name");

	public Form1(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.test = test; // Initialize the test variable correctly
	}

	// Methods to interact with elements
	public void enterFirstName(String name) {
		enterText(firstName, name);
	}

	// Other methods remain unchanged

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
			System.out.println("Inside Enter Text");
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			Assert.assertTrue(element.isDisplayed());
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			System.out.println("Inside Enter Text catch");
			String failureMessage = "Failed to enter text in element : " + locator + " - " + e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage);
		}
	}

	private void selectRandomOption(By dropdownLocator) {
		try {
			Thread.sleep(1000);
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
			Assert.assertTrue(dropdownElement.isDisplayed());
			Select dropdown = new Select(dropdownElement);
			List<WebElement> options = dropdown.getOptions();
			Random random = new Random();
			int index = random.nextInt(options.size());
			dropdown.selectByIndex(index);
		} catch (Exception e) {
			String failureMessage = "Failed to select random option in dropdown: " + dropdownLocator + " - "
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
			String failureMessage = "Failed to click the submit button: " + e.getMessage();
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
			String failureMessage = "Failed to search the record: " + e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage); // Ensure this failure is logged
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
			Thread.sleep(35000);

			WebElement leadsHeaderEl = wait.until(ExpectedConditions.visibilityOfElementLocated(leadsListHeader));
			System.out.println(leadsHeaderEl.getText());
			System.out.println("Test log  done");
			return true;
		} catch (Exception e) {
			String failureMessage = "Login failed: " + e.getMessage();
			test.log(Status.FAIL, failureMessage);
			Assert.fail(failureMessage); // Fail immediately and log
			return false;
		}
	}
}
