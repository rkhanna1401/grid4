package smoketests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import driver.BrowserDataProvider;
import driver.DriverManager;
import workflows.LoginWorkflow;

public class LoginTests extends DriverManager{
	
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void incorrectPasswordTest1(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		LoginWorkflow loginWorkflow  = new LoginWorkflow(driver);
		loginWorkflow.test();
		
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void incorrectPasswordTest2(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		LoginWorkflow loginWorkflow  = new LoginWorkflow(driver);
		loginWorkflow.test();
		
	}

}
