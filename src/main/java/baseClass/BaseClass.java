package baseClass;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass 
{
	public WebDriver driver;
	public Properties prop;
	
	public BaseClass()
	{
		try
		{
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/config/config.properties");
			prop.load(fis);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public void configurBrowser()
	{
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--headless");
		
		driver = new ChromeDriver(options);
	}
	
	@AfterClass()
	public void tearDown()
	{
		driver.quit();
	}
}
