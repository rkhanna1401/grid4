package utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.emulation.Emulation;
import org.openqa.selenium.devtools.v91.log.Log;
import org.openqa.selenium.devtools.v91.network.Network;
import org.openqa.selenium.devtools.v91.network.model.ConnectionType;
import org.openqa.selenium.devtools.v91.performance.Performance;
import org.openqa.selenium.devtools.v91.performance.Performance.EnableTimeDomain;
import org.openqa.selenium.devtools.v91.performance.model.Metric;
import org.openqa.selenium.devtools.v91.security.Security;

public class DevToolsApiUtils {

	/*
	 * To view Console Logs
	 */
	public static void viewConsoleLogs(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Log.enable());
		devTools.addListener(Log.entryAdded(),LogEntry ->{
			System.out.println("Logs: "+LogEntry.getText());
			System.out.println("Level: "+LogEntry.getLevel());
		});

	}

	/*
	 * To mock geo-location
	 */
	public static void viewGeoLocation(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Emulation.setGeolocationOverride(Optional.of(25.321684), Optional.of(82.987289), Optional.of(100)));

	}

	/*
	 * To simulate Network
	 */
	public static void simulateNetwork(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(
				false,100,200000,100000,Optional.of(ConnectionType.CELLULAR3G)));  //"true" to test in offline mode
	}

	/*
	 * To handle certificate errors
	 */
	public static void handleSSLCertificatesError(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Security.enable());
		devTools.send(Security.setIgnoreCertificateErrors(true));
	}

	/*
	 * Simulate Devices
	 */
	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	public static void simulateDevice(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		Map deviceMetrics = new HashMap()
		{{
			put("width", 600);
			put("height", 1000);
			put("mobile", true);
			put("deviceScaleFactor", 50);
		}};
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

	}

	/*
	 * Capture HTTP Requests
	 */
	public static void captureHttpRequests(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(),
				entry -> {
					System.out.println("Request URI : " + entry.getRequest().getUrl()+"\n"
							+ " With method : "+entry.getRequest().getMethod() + "\n");
					entry.getRequest().getMethod();
					
				});
	}
	
	/*
	 * Capture HTTP Requests
	 */
	public static void capturePerformance(ChromeDriver driver) {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Performance.enable(Optional.empty()));
		devTools.addListener(Performance.metrics(),
				metric -> {
					System.out.println("Metrics are: "+ metric.getTitle());
					
				});
		Command<List<Metric>> metrics = Performance.getMetrics();
		System.out.println("Performace Response: "+metrics.getSendsResponse());
		
	}
}
