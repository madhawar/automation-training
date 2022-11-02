package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.HRMAssignment;
import services.Log;
import services.Prop;

import java.time.Duration;

public class LoginPage extends HRMAssignment {

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT));
        js = ((JavascriptExecutor) driver);
    }

    public void navigate_to_login(String URL) {
        driver.get(URL);
    }

    public void login_to_actitime(String USERNAME, String PASSWORD, boolean keepLogged) {
        WebElement userEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Prop.elements().getProperty("LOGIN_USERNAME"))));
        userEmail.sendKeys(USERNAME);
        Log.info("USER HAS ENTERED USERNAME: " + USERNAME);

        WebElement userPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Prop.elements().getProperty("LOGIN_PASSWORD"))));
        userPassword.sendKeys(PASSWORD);
        Log.info("USER HAS ENTERED PASSWORD: " + PASSWORD);

        if (keepLogged) {
            WebElement stayLogged = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Prop.elements().getProperty("LOGIN_STAY_LOGGED"))));
            stayLogged.click();
            Log.info("USER IS STAYING LOGGED IN");
        }

        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Prop.elements().getProperty("LOGIN_BUTTON"))));
        loginButton.click();
    }

}
