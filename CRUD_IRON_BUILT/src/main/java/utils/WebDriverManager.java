package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {

	public static WebDriver getDriver() {
		// Set the path to the WebDriver executable (e.g., chromedriver)
		System.setProperty("webdriver.chrome.driver",
				"/home/salman.ali@ROLUSTECH.NET/Downloads/chromedriver_linux64/chromedriver.exe");

		return new ChromeDriver();
	}
}
