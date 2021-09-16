package driver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.model.ConnectionType;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.gson.JsonObject;

import utils.GenericUtils;
import utils.JsonUtils;
import utils.PropertyManager;


public class DriverManager {

	public static ThreadLocal<RemoteWebDriver> threadLocalDriver = new ThreadLocal<RemoteWebDriver>();
	private static String distributed_huburl;
	private static String hub_node_huburl;
	private static String standalone_huburl;
	private static String kube_huburl;
	private JsonObject jsonObject;
	ChromeOptions chromeOptions;
	SafariOptions safariOptions;
	FirefoxOptions firefoxOptions;
	static DesiredCapabilities capabilities;
	public static WebDriver driver;
	private	static ArrayList<WebDriver> webDriverList = new ArrayList<WebDriver>();
	private	static ArrayList<RemoteWebDriver> remoteWebDriverList = new ArrayList<RemoteWebDriver>();
	private static Logger logger;
	private static DriverManager driverManager;
	private static 	String path;
	private ChromeDriver chromeDriver;

	public static DriverManager getInstance()
	{
		capabilities = new DesiredCapabilities();
		logger = Logger.getLogger(DriverManager.class.getName());

		path =  GenericUtils.createOutputFolderPath();
		if (driverManager==null)
			driverManager = new DriverManager();
		return driverManager;
	}

	@BeforeSuite
	public void initServer() {
		if(jsonObject==null) {
			try {
				jsonObject = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("url"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		distributed_huburl = JsonUtils.getValFromJson(jsonObject, "distributed_huburl","");
		hub_node_huburl = JsonUtils.getValFromJson(jsonObject, "hub_node_huburl","");
		standalone_huburl =  JsonUtils.getValFromJson(jsonObject, "standalone_huburl","");
		kube_huburl = JsonUtils.getValFromJson(jsonObject, "kube_huburl","");
	}



	public void getStandaloneHubNodeServerDriver(String browserType,String platformType, String url) throws MalformedURLException  {
		DesiredCapabilities caps = new DesiredCapabilities();
		if (browserType.equalsIgnoreCase("firefox")) {
			caps.setBrowserName("firefox");
			threadLocalDriver.set(new RemoteWebDriver(new URL(url),caps));
		}
		if (browserType.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
			threadLocalDriver.set(new RemoteWebDriver(new URL(url),options));
		}
		if (browserType.equalsIgnoreCase("safari")) {
			caps.setBrowserName("safari");
		}
		if (browserType.equalsIgnoreCase("edge")) {
			EdgeOptions edgeOptions = new EdgeOptions();
			caps.merge(edgeOptions);
		}

		threadLocalDriver.get().manage().window().maximize();
	}

	public void getLocalDriverInstance(String browserType,String platformType) throws MalformedURLException {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			chromeDriver = new ChromeDriver(options);
			threadLocalDriver.set(chromeDriver);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	public RemoteWebDriver getDriver(String browserType,String platformType) {
		setPlatform(platformType);
		setDriverLocation(browserType ,platformType);
		if(threadLocalDriver.get() == null) {
			try {
				setDriver(browserType, platformType);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return threadLocalDriver.get();
	}



	/**
	 * 
	 * @param browserType
	 * @param platformType
	 * @return Remote Driver Instance if running on Grid mode or up-casted webdriver instance if running locally
	 * @throws MalformedURLException 
	 */
	public void setDriver(String browserType,String platformType) throws MalformedURLException  {
		synchronized (browserType) {
			if(PropertyManager.getPropertyHelper("configuration").get("standalone_mode").equals("ON")) {
				getStandaloneHubNodeServerDriver(browserType, platformType,standalone_huburl);
			}
			else if(PropertyManager.getPropertyHelper("configuration").get("hub_node_mode").equals("ON")) {
				getStandaloneHubNodeServerDriver(browserType, platformType,hub_node_huburl);
			}
			else if(PropertyManager.getPropertyHelper("configuration").get("distributed_mode").equals("ON")) {
				getStandaloneHubNodeServerDriver(browserType, platformType,distributed_huburl);
			}
			else if(PropertyManager.getPropertyHelper("configuration").get("kubernetes_mode").equals("ON")) {
				getStandaloneHubNodeServerDriver(browserType, platformType,kube_huburl);
			}
			else {
				getLocalDriverInstance(browserType, platformType);
			}
			remoteWebDriverList.add(threadLocalDriver.get());
		}
	}

	/**
	 * Set Drivers Platform
	 */
	private static void setPlatform(String platform) {
		switch (platform) {
		case "windows":
			capabilities.setPlatform(Platform.WINDOWS);
			break;
		case "mac":
			capabilities.setPlatform(Platform.MAC);
			break;
		default:
			logger.info("Failed to set the Platform as: " + platform);
			break;
		}
		logger.info("Successfully set the Platform as: " + platform);
	}

	/**
	 * Set Drivers Location System Properties
	 */
	private static void setDriverLocation(String browserName, String platform) {
		String driverLocation = "Driver";
		File file = new File(driverLocation);
		String driverLocationPath = file.getAbsolutePath();
		switch (browserName) {
		case "chrome":
			if (platform.equals("windows"))
				System.setProperty("webdriver.chrome.driver", driverLocationPath + "/chromedriver.exe");
			else if (platform.equalsIgnoreCase("mac"))
				System.setProperty("webdriver.chrome.driver", driverLocationPath + "/chromedriver");
			break;
		case "firefox":
			if (platform.equals("windows"))
				System.setProperty("webdriver.gecko.driver", driverLocationPath + "/geckodriver.exe");
			else if (platform.equals("mac"))
				System.setProperty("webdriver.gecko.driver", driverLocationPath + "/geckodriver");
			break;
		case "safari":
			break;
		case "edge":
			if (platform.equals("windows"))
				System.setProperty("webdriver.edge.driver", driverLocationPath + "/msedgedriver.exe");
			else if (platform.equals("mac"))
				System.setProperty("webdriver.edge.driver", driverLocationPath + "/msedgedriver");
			break;

		default:
			logger.info("Failed to  Set the driver locations");
			Assert.fail();
			break;
		}
		logger.info("Successfully Set the driver locations");
	}


	public String getValFromJson(JsonObject json, String dataKey) {
		return json.get(dataKey).getAsString();
	}


	@AfterSuite
	public void tearDown() {
		if(!remoteWebDriverList.isEmpty()) {
			for(RemoteWebDriver driver : remoteWebDriverList)
				driver.quit();
			logger.info("Hub and Node has been shut down");
		}
		if(!webDriverList.isEmpty()) {
			for(WebDriver driver : webDriverList)
				driver.quit();
		}
		logger.info("All browser instances has been shut down");

	}
}

