package services;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HRMAssignment {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    protected static ChromeOptions chromeOptions = new ChromeOptions();
    protected static FirefoxOptions firefoxOptions = new FirefoxOptions();
    protected static DateTimeFormatter ss = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss");

    protected int WEBDRIVER_WAIT = 5;
    protected boolean headless = false;
    protected boolean debug = false;

    @BeforeSuite()
    public void delete_test_reports_and_screenshots_of_previous_runs() throws Exception {
        File screenshots = new File("test-screenshots");
        File[] imageFiles = screenshots.listFiles((d,f)-> f.toLowerCase().endsWith(".png"));

        if (imageFiles != null) {
            for(File f : imageFiles) {
                if(!f.delete())
                    throw new IOException("Not able to delete png files: " + f.getAbsolutePath());
            }
        }

        WebDriverManager.chromedriver().setup();
//        WebDriverManager.firefoxdriver().setup();
        if (headless) {
            chromeOptions.addArguments("--headless", "--incognito", "--window-size=1920,1080", "--disable-gpu", "--disable-extensions", "--disable-site-isolation-trials", "--no-sandbox","--disable-dev-shm-usage", "--ignore-certificate-errors");
            firefoxOptions.addArguments("--headless", "--private", "--width=1920", "--height=1080");
        }
        else {
            chromeOptions.addArguments("--incognito", "--window-size=1920,1080", "--disable-gpu", "--disable-extensions", "--disable-site-isolation-trials", "--no-sandbox","--disable-dev-shm-usage", "--ignore-certificate-errors");
            firefoxOptions.addArguments("--private", "--width=1920", "--height=1080");
        }
    }

    @BeforeMethod
    public void initiate_browser_instance() {
        driver = new ChromeDriver(chromeOptions);
//        driver = new FirefoxDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT));
        js = ((JavascriptExecutor) driver);
    }

    @AfterMethod
    public void teardown(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            LocalDateTime now = LocalDateTime.now();
            TakesScreenshot ts = (TakesScreenshot) driver;
            String screenshot = "ss-" + ss.format(now);
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source, new File("test-screenshots/" + screenshot + ".png"));
            Log.error("[SCREENSHOT] SAVED: " + screenshot + ".png");
        }

        if (driver != null && !debug) {
            driver.quit();
        }
    }
}
