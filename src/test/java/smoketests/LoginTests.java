package smoketests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import driver.BrowserDataProvider;
import driver.DriverManager;
import workflows.LoginWorkflow;

public class LoginTests extends DriverManager{
	
	
	//@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void incorrectPasswordTest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		LoginWorkflow loginWorkflow  = new LoginWorkflow(driver);
		loginWorkflow.navigate_to_loginpage();
		loginWorkflow.enterUsername();
		loginWorkflow.enterPassword();
		loginWorkflow.validateWrongUsernamePassword();
	}
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void forgotPasswordTest(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser, platformType);
		LoginWorkflow loginWorkflow  = new LoginWorkflow(driver);
		loginWorkflow.navigate_to_password_page();
		loginWorkflow.selects_forgot_password();
		loginWorkflow.user_succesfully_resets_the_password();
	}

}
