package driver;

import org.testng.annotations.DataProvider;

import enums.BrowserList;
import enums.PlatformList;

public class BrowserDataProvider {

	@DataProvider(name="browser",parallel = true)
	public static Object[][] browser(){
		return new Object[][]{

			{BrowserList.chrome.toString(),PlatformList.mac.toString()},
    		{BrowserList.firefox.toString(),PlatformList.mac.toString()}
			//{BrowserList.edge.toString(),PlatformList.mac.toString()}

		};

	}
}
