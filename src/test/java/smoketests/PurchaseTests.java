package smoketests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import driver.BrowserDataProvider;
import driver.DriverManager;
import workflows.PurchaseWorkflow;

public class PurchaseTests extends DriverManager{
	
	@Test(dataProvider = "browser", dataProviderClass = BrowserDataProvider.class)
	public void makePurchase(String browser, String platformType) {
		WebDriver driver = DriverManager.getInstance().getDriver(browser,platformType);
		PurchaseWorkflow purchaseWorkflow = new PurchaseWorkflow(driver);
		purchaseWorkflow.loadHomePage();
		purchaseWorkflow.searchProduct();
		purchaseWorkflow.select_buy_now();
		purchaseWorkflow.you_make_a_successful_purchase();
	}
}
