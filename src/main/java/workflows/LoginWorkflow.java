package workflows;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.GenericUtils;
import utils.JsonUtils;

/**
 * @author rishikhanna
 *
 */

public class LoginWorkflow {

	private WebDriver driver;
	private BaseWorkflow baseWorkflow;
	private WorkflowManager workflowManager;
	
	public LoginWorkflow(WebDriver driver) {
		this.driver = driver;
		baseWorkflow = new BaseWorkflow(driver);
		workflowManager = new WorkflowManager();
	}
	
	public void navigate_to_loginpage() {
		workflowManager.getBasePage(driver).load();
		workflowManager.getLoginPage(driver).clickSignIn();
	}


	public void enterUsername() {
		workflowManager.getLoginPage(driver).setEmail(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "email"));
		workflowManager.getLoginPage(driver).clicktContinueBtn();
	}


	public void enterPassword() {
		workflowManager.getLoginPage(driver).setPassword(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "password"));
		workflowManager.getLoginPage(driver).clicktSignInBtn();
	}

	public void validateWrongUsernamePassword() {
		Assert.assertTrue(GenericUtils.isDisplayed(workflowManager.getLoginPage(driver).getErrorMessageLbl()) 
				|| GenericUtils.isDisplayed(workflowManager.getLoginPage(driver).getErrorMessageImpMessage()));
	}

	public void userNotLoggedIn() {

	}

	public void navigate_to_password_page() {
		navigate_to_loginpage();
		enterUsername();
	}

	public void selects_forgot_password() {
		workflowManager.getLoginPage(driver).clickForgotPassword();
		workflowManager.getLoginPage(driver).clicktContinueBtn();
	}

	public void navigated_to_password_reset_page() {
		int i =0;
		int j =i/0;
		//purposefully
	}

	public void user_succesfully_resets_the_password() {

	}
	
	public void test() {
		String baseUrl = "http://demo.guru99.com/test/newtours/";
        String expectedTitle = "Welcome: Mercury Tours";
        String actualTitle = "";
        driver.get(baseUrl);
        actualTitle = driver.getTitle();

        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
	}
}
