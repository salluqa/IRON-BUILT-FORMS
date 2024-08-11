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

public class Common_Element_Methods {
	protected WebDriver driver;
	protected WebDriverWait wait;

	// Common form fields
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
	protected By name = By.name("username");
	protected By passwordField = By.name("password");
	protected By loginButton = By.name("login_button"); // Assuming the login button locator
	protected By leadsListHeader = By.cssSelector(".list-headerpane .ellipsis_inline");
	protected By searchBar1 = By.className("search-name");
	protected By buildingUse = By.cssSelector(".wpcf7-form-control-wrap[data-name=\"builduse\"] select");
	protected By fullNameElement = By.cssSelector("div.relate-field-container a.Leads");

	public Common_Element_Methods(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	}

	// Common methods for interacting with form fields
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

	protected void enterText(By locator, String text) {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			Assert.assertTrue(element.isDisplayed(), "Element not displayed: " + locator);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			Assert.fail("Failed to enter text in element: " + locator + " - " + e.getMessage());
		}
	}

	protected void selectRandomOption(By dropdownLocator) {
		try {
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
			Assert.assertTrue(dropdownElement.isDisplayed(), "Dropdown not displayed: " + dropdownLocator);
			Select dropdown = new Select(dropdownElement);
			List<WebElement> options = dropdown.getOptions();
			Random random = new Random();
			int index = random.nextInt(options.size());
			dropdown.selectByIndex(index);
		} catch (Exception e) {
			Assert.fail("Failed to select random option in dropdown:" + dropdownLocator + " - " + e.getMessage());
		}
	}

	public void clickSubmitButton() {
		try {
			WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			Assert.assertTrue(submitBtn.isDisplayed(), "Submit button is not displayed.");
			submitBtn.click();
		} catch (Exception e) {
			Assert.fail("Failed to click the submit button: " + e.getMessage());
		}
	}

	public boolean successCriteria_Search_record(String expectedMessage) {
		try {
			WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar1));
			searchBar.sendKeys(expectedMessage);
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameElement));

			// Extract the full text, e.g., "Automated 31375"
			String fullName = dropdownElement.getText();

			// Split the full text by space to separate the first name and last name
			String[] nameParts = fullName.split(" ");

			// The last part (31375) is the last name
			String lastName = nameParts[nameParts.length - 1];
			System.out.println(lastName);
			System.out.println(expectedMessage);
			if (expectedMessage.equals(lastName)) {
				return true;
			} else {
				System.out.println("Name does not match");
				return false;
			}
		} catch (Exception e) {
			Assert.fail("Failed to search the record: " + e.getMessage());
			return false;
		}
	}

	public String generateRandomFiveDigitNumber() {
		Random random = new Random();
		int number = 10000 + random.nextInt(90000); // Generates a number between 10000 and 99999
		return String.valueOf(number);
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

			try {
				WebElement leadsHeaderEl = wait.until(ExpectedConditions.visibilityOfElementLocated(leadsListHeader));
				System.out.println("Leads List Header found: " + leadsHeaderEl.getText());
				return true;
			} catch (Exception e) {
				System.out.println("Leads List Header not found. Printing page source for debugging...");
				System.out.println(driver.getPageSource());
				Assert.fail("Login failed: " + e.getMessage());
				throw e;
			}

		} catch (Exception e) {
			Assert.fail("Login failed: " + e.getMessage());
		}
		return false;
	}
}
