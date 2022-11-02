package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import services.HRMAssignment;
import services.Log;
import services.Prop;

import java.time.Duration;

public class HomePage extends HRMAssignment {

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT));
        js = ((JavascriptExecutor) driver);
    }

    public void verify_logout_link_is_visible() {
        WebElement logoutLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Prop.elements().getProperty("LOGOUT_LINK"))));
        Assert.assertTrue(logoutLink.isDisplayed());
        Log.info("LOGOUT LINK VISIBLE");
    }

}
