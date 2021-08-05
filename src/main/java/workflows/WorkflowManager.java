package workflows;

import org.openqa.selenium.WebDriver;
import driver.DriverManager;
import webpages.BasePage;
import webpages.HomePage;
import webpages.LoginPage;
import webpages.ProductPage;

public class WorkflowManager {
	
	private BasePage basePage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ProductPage productPage;
	private DriverManager driverManager;
	
	
	public  BasePage getBasePage(WebDriver driver) {
		this.basePage = new BasePage(driver);
		return this.basePage;
	}
	
	public  LoginPage getLoginPage(WebDriver driver) {
		this.loginPage = new LoginPage(driver);
		return this.loginPage;
	}
	
	public  HomePage getHomePage(WebDriver driver) {
		this.homePage = new HomePage(driver);
		return this.homePage;
	}
	
	public  ProductPage getProductPage(WebDriver driver) {
		this.productPage = new ProductPage(driver);
		return this.productPage;
	}
	
	public  DriverManager getDriverManager(WebDriver driver) {
		this.driverManager = new DriverManager();
		return this.driverManager;
	}
	
}
