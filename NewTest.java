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
		ChromeOptions options1 = new ChromeOptions();
		driver = new ChromeDriver(options1);
		try {
			String line = "";
			String splitBy = ",";

			BufferedReader br = new BufferedReader(
					new FileReader("/home/rlt/eclipse-workspace/Website/Web/Data/Untitled 1.csv"));
			while ((line = br.readLine()) != null)
			// returns a Boolean value
			{
				// use comma as separator
				String[] spliting = line.split(splitBy);

//				options1.addArguments("--user-data-dir=/home/rlt/.config/google-chrome/");
//				// load default profile
//
//				options1.addArguments("--profile-directory=" + spliting[0]);
				System.out.println(spliting[0]);

				// Calling Google Page
				System.out.println(spliting[4]);
				driver.get( spliting[4]);

				// Maximize the browser
				driver.manage().window().maximize();
				Thread.sleep(4500);

				//// Enter keywords//////////////
				driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
						.sendKeys(spliting[1]);
				Thread.sleep(3000);
				///////////////// Enter search////////////
				WebElement ar = driver.findElement(By.name("q"));
				ar.sendKeys(Keys.RETURN);
				Thread.sleep(20000);
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

				String wait1 = spliting[3];
				int i = Integer.parseInt(wait1);
				driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
				System.out.println(i);
//			


			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
