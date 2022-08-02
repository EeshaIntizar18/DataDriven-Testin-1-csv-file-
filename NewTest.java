package com.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NewTest {

	public static WebDriver driver;

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		// Setting property for Chrome Driver
		System.setProperty("webdriver.chrome.driver", "/home/rlt/Downloads/chromedriver");

		// Instance of ChromeOption
		ChromeOptions options = new ChromeOptions();
		String line = "";
		String splitBy = ",";

		try {
			// parsing a CSV file into BufferedReader class constructor
			BufferedReader br = new BufferedReader(
					new FileReader("/home/rlt/eclipse-workspace/Website/Web/Data/Untitled 1.csv"));
			while ((line = br.readLine()) != null)
			// returns a Boolean value
			{
				// use comma as separator
				String[] spliting = line.split(splitBy);
				// Disable notification PopUp
				options.addArguments("--disable-notifications");
//			options.addArguments("chrome.switches", "--disable-extensions");
//			options.addArguments("--user-data-dir=/home/rlt/.config/google-chrome/");
//			// load default profile
//
//			options.addArguments("--profile-directory="+spliting[0]);
//			Thread.sleep(1000);
				System.out.println(spliting[0])
				// Instance of ChromeDriver
				;
				driver = new ChromeDriver(options);
				// Calling Google Page
				driver.get("https://www.google.com/?gl=us&hl=en&pws=0&gws_rd=cr");
				Thread.sleep(1000);
				driver.manage().deleteAllCookies();

				// Maximize the browser
				driver.manage().window().maximize();
				Thread.sleep(4500);

                ////////////////////print ///////////////////////////

				System.out.println(spliting[1]);
				System.out.println(spliting[2]);
				System.out.println(spliting[3]);
				System.out.println(spliting[4]);
				
				////Enter keywords//////////////
				driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
						.sendKeys(spliting[1]);
				Thread.sleep(8000);
				///////////////// Enter search////////////
				WebElement ar = driver.findElement(By.name("q"));
				ar.sendKeys(Keys.RETURN);
				System.out.println("   ");

				boolean flag = false;
				int pageNumber = 1;
				System.out.println(spliting[2]);
				while (!flag) {

					List<WebElement> list = driver.findElements(By.xpath("//*[@href or @src]"));
					for (WebElement eachResult : list) {
						String link = eachResult.getAttribute("href");
						if (null == link)
							link = eachResult.getAttribute("src");
						if (link.contains(spliting[2])) {

							eachResult.findElement(By.xpath("//a[@href='" + link + "']")).click();
							System.out.println("Link found");
							flag = true;

							break;
						}
					} // End of foreach-loop

					if (!flag) {
						driver.findElement(By.xpath("//a[@id='pnnext']/span[1]")).click();
						Thread.sleep(2000);
						pageNumber++;

					}

				}
				flag = false; // while
				String wait1 = spliting[3];
				int i = Integer.parseInt(wait1);
				driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
				String url = spliting[2];
				driver.get(url);

			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}