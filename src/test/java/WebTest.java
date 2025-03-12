import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class WebTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testPurchaseFlow() {
        // Login
        driver.get(BASE_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Adding Products to Cart
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));
        for (int i = 0; i < 4; i++) {
            addToCartButtons.get(i).click();
        }

        // Open Cart and Verify Subtotal
        driver.findElement(By.className("shopping_cart_link")).click();
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(cartItems.size(), 4, "Cart does not contain 4 items");

        // Proceed to Checkout
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // Capture and Print Item
        String itemTotal = driver.findElement(By.className("summary_subtotal_label")).getText();
        String tax = driver.findElement(By.className("summary_tax_label")).getText();
        String total = driver.findElement(By.className("summary_total_label")).getText();

        System.out.println("Item Total: " + itemTotal);
        System.out.println("Tax: " + tax);
        System.out.println("Final Total: " + total);

        // Complete Purchase and Verify Success Message
        driver.findElement(By.id("finish")).click();
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertTrue(successMessage.getText().contains("Thank you for your order!"), "Purchase success message not displayed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
