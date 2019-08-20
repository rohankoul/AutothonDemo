package testserver.server.uiAutomation;

//import com.thoughtworks.selenium.DefaultSelenium;
//import com.thoughtworks.selenium.Selenium;
//import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.apache.commons.io.FileUtils;
import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;

public class SeleniumStandaloneServer extends Thread {
	// public class SeleniumStandaloneServer {
	String movie;
	String device;
	WebDriver driver;

	SeleniumStandaloneServer(String movie_name, String device_name) {
		movie = movie_name;
		device = device_name;
		// factory for device
		try {
			if ("Windows".equals(device)) {

				driver = winChromeFactory();
			}
			if ("Android".equals(device)) {
				driver = androidChromeFactory();
			}
			if(("Firefox").equals(device)){
				
				driver = firefoxFactory();
			}
		} catch (Exception e) {

			System.out.println("Error " + e.getLocalizedMessage());
		}

	}

	private WebDriver androidChromeFactory() throws MalformedURLException {

		DesiredCapabilities cap = new DesiredCapabilities();// DesiredCapabilities.android();
		cap.setCapability("deviceName", "Moto G (5S) Plus");
		cap.setCapability("udid", "ZY32285CLZ");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "8.1.0");
		cap.setCapability("browserName", "Chrome");
		WebDriver d = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), cap);
		return d;
	}

	private WebDriver firefoxFactory() throws MalformedURLException {
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-maximized");
		//options.addArguments("--headless");
		WebDriver d = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
		return d;
	}

	private WebDriver winChromeFactory() throws MalformedURLException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		//options.addArguments("--headless");
		WebDriver d = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
		return d;
	}
	
	public void run() {
		SeleniumUtilities utl = new SeleniumUtilities();
		// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
		// options);
		HTMLOutputWriter html = new HTMLOutputWriter(
				"C:\\Users\\i340909\\OneDrive - SAP SE\\Autothon\\Execution_Output\\" +device+"_"+ movie + "_Report.html", movie);
		

		try {
			// Repeated steps
			// actions

			html.createTable("Wikipedia");
			utl.goToPage(driver, "https://google.com", this.movie + "home_page.png", html);
			Thread.sleep(2000);
			utl.enterInput(driver, "//input[@name='q']", this.movie, this.movie + "_Step_1.png", html);
			Thread.sleep(2000);
			utl.findAndClickElement(driver, "(//*[@aria-label='Google Search'])[1]", this.movie+"google_search_btn.png", html);
			Thread.sleep(2000);
			utl.findAndClickElement(driver, "(//a[contains(@href,'https://en.wikipedia.org/wiki')])[2]",
					this.movie + "step_2.png", html);
			Thread.sleep(2000);
			String director1 = utl.getDataFromElement(driver,
					"//table[@class='infobox vevent']//tr[th[contains(text(),'Directed by')]]//td/a",
					this.movie + "data_wiki.png", html);
			
			if ("".equals(director1)) {
				html.closeTable(0);
			} else {
				html.closeTable(1);
			}

			html.createTable("IMDB");
			utl.goToPage(driver, "https://google.com", this.movie + "home_page_2.png", html);
			
			utl.enterInput(driver, "//input[@name='q']", this.movie, this.movie + "_Step_1.png", html);
			utl.findAndClickElement(driver, "(//*[@aria-label='Google Search'])[1]", this.movie+"google_search_btn2.png", html);
			
			//utl.findAndClickElement(driver, "//a[contains(@href,'https://www.imdb.com/title') and div[cite]]",
			//		this.movie + "imdb.png", html);
			utl.findAndClickElement(driver, "//a[span[contains(text(),'IMDb')]]",
						this.movie + "imdb.png", html);
			
			String director2="";
			if(this.device.equals("Windows"))
			{
				director2 = utl.getDataFromElement(driver, "//div[h4[contains(text(),'Director')]]/a",
						this.movie + "data_imdb.png", html);
			}
			if(this.device.equals("Android"))
			{
				director2 = utl.getDataFromElement(driver, "//a[div[h3[contains(text(),'Director')]]]/div/span",
						this.movie + "data_imdb.png", html);
			}
				
			
			if ("".equals(director2)) {
				html.closeTable(0);
			} else {
				html.closeTable(1);
			}

			if ((director2).equals(director1)) {
				System.out.println("[ { movie_name : " + this.movie + "} , { director_Wiki : " + director1
						+ " } , { director_imdb : " + director2 + " } , { match : Success } ] ");
			} else {

				System.out.println("[ { movie_name : " + this.movie + "} , { director_Wiki : " + director1
						+ " } , { director_imdb : " + director2 + " } , { match : Failed } ] ");
			}
			
			driver.quit();
		} catch (Exception e) {
			System.out.println("Exception E " + e.getMessage());
			html.closeTable(0);
			
		} finally {
			// html.closeHTML();
			html.closeHTML();
			html.cleanup();
			driver.quit();
		}

	}

}
