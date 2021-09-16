package smoketests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import driver.BrowserDataProvider;
import driver.DriverManager;
import utils.DevToolsApiUtils;

public class DevToolsTests extends DriverManager{

	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void logTest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.viewConsoleLogs(chromeDriver);
		chromeDriver.get("https://testersplayground.herokuapp.com/console-5d63b2b2-3822-4a01-8197-acd8aa7e1343.php");
	}


	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void geoLocationTest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.viewGeoLocation(chromeDriver);
		chromeDriver.get("https://mycurrentlocation.net/");
	}

	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void networkSpeedTest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.simulateNetwork(chromeDriver);
		long startTime = System.currentTimeMillis();
		chromeDriver.get("https://www.google.com");
		long endTime = System.currentTimeMillis();

		System.out.println("Slow Network: Page loaded in " + (endTime - startTime) + "milliseconds");
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void handleSSL(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.handleSSLCertificatesError(chromeDriver);
		chromeDriver.get("https://expired.badssl.com/");
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void simlateDevice(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.simulateDevice(chromeDriver);
		chromeDriver.get("https://google.com/");
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void captureHttpRequest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.captureHttpRequests(chromeDriver);
		chromeDriver.get("https://google.com/");
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void capturePerformance(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevToolsApiUtils.capturePerformance(chromeDriver);
		chromeDriver.get("https://google.com/");
	}
}
