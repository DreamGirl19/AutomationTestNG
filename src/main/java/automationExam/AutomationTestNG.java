package automationExam;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutomationTestNG {
	WebDriver driver;

	String browser = "chrome";
	
	@BeforeClass
	public void readConfig() {

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("./src/main/java/config/config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void init() {
		
		// control over multiple browser at the same time
				if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
					driver = new ChromeDriver();
				} else if (browser.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
					driver = new FirefoxDriver();
				}
		
		driver.get("https://techfios.com/test/101/index.php");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	// this is Element Library

	By ADD_CATEGORY_ELEMENT = By.xpath("//*[@id=\"extra\"]/input[1]");
	By ADD_CATEGORY_BUTTON_ELEMENT = By.xpath("//*[@id=\"extra\"]/input[2]");
	By CATEGORY_DROPDOWN_ELEMENT = By.xpath("//*[@id=\"extra\"]/select[1]/option[184]");

	//Test Data or Mock Data
	String addCategory = "Dreamgirl";
	String category = "DreamGirl75";

	Random rdm = new Random();
	int randomNumber = rdm.nextInt(99);

	
	@Test
	public void AddFormTest() {
		driver.findElement(ADD_CATEGORY_ELEMENT).sendKeys(addCategory);
		driver.findElement(ADD_CATEGORY_BUTTON_ELEMENT).click();
		driver.findElement(CATEGORY_DROPDOWN_ELEMENT).sendKeys(category);
		
		
		selectFromDropDown(driver.findElement(CATEGORY_DROPDOWN_ELEMENT), category);
		
		Assert.assertEquals(driver.findElement(CATEGORY_DROPDOWN_ELEMENT).getText(),
				"The category you want to add already exists: <duplicated category name>.", "CategoryElement");

	}

		
	public void selectFromDropDown(WebElement category, String VisibleText) {

		Select sel = new Select(category);
		sel.selectByVisibleText(VisibleText);
	}
	
	//Before Xpath //*[@id="extra"]/input[1]
	//After Xpath //*[@id="extra"]/input[2]
	//After Xpath Save//*[@id="extra"]/select[1]
	
	String before_Xpath = "//*[@id=";
	String after_Xpath = "]/input[2]";
	String after_Xpath_Save = "//*[@id=\"extra\"]/select[1]";
	
	
	
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
