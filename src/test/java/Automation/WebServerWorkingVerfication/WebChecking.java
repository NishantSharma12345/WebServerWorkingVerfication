package Automation.WebServerWorkingVerfication;

import org.testng.annotations.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClass.BaseClass;
import helper.ExtentReporterNG;

public class WebChecking extends BaseClass
{
	ExtentReports extent = ExtentReporterNG.getReporterObject();
	ExtentTest test;
	
	@Test
	public void webCheckingTest() throws MalformedURLException, IOException, InterruptedException
	{
		HashMap<String, String> webURL = new HashMap<String, String>();
		webURL.put("Bagisto Demo","https://demo.bagisto.com/bagisto-common/");
		webURL.put("Mobikul Bagisto Laravel App","https://demo.bagisto.com/mobikul-common/");
		webURL.put("Bagisto Multi Vendor App","https://demo.bagisto.com/mobikul-mp-common/");

		for ( Entry<String, String> i : webURL.entrySet()) 
		{	
			String demo = i.getKey();
			String url = i.getValue();
			
			driver.get(url);
			driver.manage().window().minimize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			
			List<WebElement> links = driver.findElements(By.xpath("//body//a"));

			for(int j = 0; j< 1; j++)
			{
				int responseCode = 0;
				try
				{
					String urlLink = links.get(j).getAttribute("href");
					HttpURLConnection con = (HttpURLConnection)new URL(urlLink).openConnection();
					con.setRequestMethod("HEAD");
					Thread.sleep(4000);
					con.connect();
					responseCode = con.getResponseCode();
				}
				catch(Exception e)
				{
					//e.printStackTrace();
				}
				finally
				{
					test = extent.createTest(demo);
					if(responseCode == 200 || responseCode == 201 || responseCode == 202 || responseCode == 203 ||
							responseCode == 204 || responseCode == 205 || responseCode == 206 || responseCode == 207 || 
							responseCode == 208 || responseCode == 302)
					{	
						test.log(Status.PASS, url + " is working fine.");
					}
					else
					{
						test.log(Status.FAIL, url + " is not working fine.");
					}
					test.info("Status Code :> " + responseCode);					
				}				
			}			
		}	
		extent.flush();	
		EmailSending es =  new EmailSending();
		es.emailSending();
	}
}
