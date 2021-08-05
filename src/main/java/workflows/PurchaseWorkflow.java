package workflows;

import org.openqa.selenium.WebDriver;
import utils.GenericUtils;
import utils.JsonUtils;
import workflows.*;

/**
 * @author rishikhanna
 *
 */

public class PurchaseWorkflow {


	private WebDriver driver;
	private BaseWorkflow baseWorkflow;
	private WorkflowManager workflowManager;

	public PurchaseWorkflow(WebDriver driver) {
		this.driver = driver;
		baseWorkflow = new BaseWorkflow(driver);
		workflowManager = new WorkflowManager();
	}

	public void loadHomePage()  {		
		workflowManager.getBasePage(driver).load();
	}

	public void searchProduct() {
		workflowManager.getHomePage(driver).setSearchText(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "product"));
		workflowManager.getHomePage(driver).selectProduct();
	}


	public void select_buy_now() {
		GenericUtils.switchToChildWindow(driver);
		workflowManager.getProductPage(driver).selectBuyNow();
	}

	public void you_make_a_successful_purchase() {
	}

}
