package selenium_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import config.ConfigLoader;
import utilities.CartApi;
import utilities.Screenshots;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BaseTest {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return this.driver.get();
    }

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    protected void launchBrowser(@Optional("edge") String Browser) {
       String browser = System.getProperty("browser") == null ? Browser : System.getProperty("browser");
       String headless = System.getProperty("headless") != null ? System.getProperty("headless") : "headed";
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().cachePath("Drivers").setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("disable-gpu");
                if (headless.equals("true")) chromeOptions.addArguments("--headless", "--window-size=1280,858");
                this.setDriver(new ChromeDriver(chromeOptions));
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless.equals("true")) {
                options.setHeadless(true);
                options.addArguments("--width=1280");
                options.addArguments("--height=858");
                }
                this.setDriver(new FirefoxDriver(options));
            }
            case "edge" -> {
                WebDriverManager.edgedriver().cachePath("Drivers").setup();
                EdgeOptions options = new EdgeOptions();
                if (headless.equals("true")) options.addArguments("--headless", "--window-size=1280,858");
                this.setDriver(new EdgeDriver(options));
            }
            default ->
                    throw new IllegalStateException("Invalid browser name: " + browser + ", pick any of the following values: chrome | edge | firefox");
        }
        System.out.println(browser + " BeforeMethod CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            Screenshots screenshot = new Screenshots(this.getDriver(), result);
            screenshot.takeScreenshotOfTheFullPage();
        }
        System.out.println("AfterMethod CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());
        this.getDriver().close();
    }

    @Step("I am navigating to baseUrl + {endpoint}")
    protected void goToWebSite(String endpoint) throws FileNotFoundException {
        WebDriver driver = this.getDriver();
        int time = ConfigLoader.getInstance().getPageLoadTimeOut();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        String baseUrl = ConfigLoader.getInstance().getBaseUrl();
        driver.get(baseUrl + endpoint);
    }
    @BeforeSuite
    private void deleteAllureResultsDirectory(){
        File allureResultsDirectory = new File("allure-results");
        if(allureResultsDirectory.exists()){
            String[] filesInDirectory = allureResultsDirectory.list();
            for (String file : filesInDirectory){
                File currentFile = new File(allureResultsDirectory.getPath(), file);
                currentFile.delete();
            }
            allureResultsDirectory.delete();
        }
    }

    public void addProductToCartApi(String productId, String quantity) throws FileNotFoundException {
        List<Cookie> cookies = new CartApi().addItemToCart(productId, quantity);
        for (Cookie cookie:cookies)
            getDriver().manage().addCookie(cookie);
    }
}
