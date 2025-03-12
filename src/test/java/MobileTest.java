import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


  // This class contains an Appium test to automate Wikipedia search functionality on an Android device/emulator.
 
public class MobileTest {
    
    private AndroidDriver driver;

    
    // Setup method to initialize the Appium driver with desired capabilities before running the tests.
     
    @BeforeClass
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554"); // Use actual device/emulator ID
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability("appPackage", "org.wikipedia");
        caps.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        caps.setCapability("noReset", "true");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    
    // Test case to validate the Wikipedia search functionality.
     
    @Test
    public void testWikipediaSearch() {
        // Verify if the search bar is displayed
        Assert.assertTrue(driver.findElement(AppiumBy.id("org.wikipedia:id/search_container")).isDisplayed(), "App did not launch successfully");

        // Click on the search bar
        driver.findElement(AppiumBy.id("org.wikipedia:id/search_container")).click();

        // Enter 'Selenium' as the search term
        driver.findElement(AppiumBy.id("org.wikipedia:id/search_src_text")).sendKeys("Selenium");

        // Select the second search result
        driver.findElements(AppiumBy.className("android.widget.TextView")).get(1).click();

        // Scroll to find the 'History' section in the article
        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"History\"))"));

        // Verify that the 'History' section is displayed
        Assert.assertTrue(driver.findElement(AppiumBy.xpath("//*[contains(@text, 'History')]")).isDisplayed(), "History section not found");

        // Navigate back to the main screen
        driver.navigate().back();
        driver.navigate().back();
    }

    
    // Teardown method to quit the driver after test execution.
     
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
