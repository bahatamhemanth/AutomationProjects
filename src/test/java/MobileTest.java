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

public class AppiumTest {
    private AndroidDriver driver;

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

    @Test
    public void testWikipediaSearch() {

        Assert.assertTrue(driver.findElement(AppiumBy.id("org.wikipedia:id/search_container")).isDisplayed(), "App did not launch successfully");

        driver.findElement(AppiumBy.id("org.wikipedia:id/search_container")).click();

        driver.findElement(AppiumBy.id("org.wikipedia:id/search_src_text")).sendKeys("Selenium");

        driver.findElements(AppiumBy.className("android.widget.TextView")).get(1).click();

        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"History\"))"));

        Assert.assertTrue(driver.findElement(AppiumBy.xpath("//*[contains(@text, 'History')]")).isDisplayed(), "History section not found");

        driver.navigate().back();
        driver.navigate().back();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}