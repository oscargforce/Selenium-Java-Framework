package web_element_behaviour;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.ConfigLoader;

import java.io.FileNotFoundException;
import java.time.Duration;

public class WebElementBehaviourBase {
    protected int waitForElementTimeOut;
    protected int assertionTimeOut;

    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    protected FluentWait<WebDriver> assertionWait;
    protected  JavascriptExecutor javascript;

    public WebElementBehaviourBase(WebDriver driver) throws FileNotFoundException {
        this.driver = driver;
        this.waitForElementTimeOut = ConfigLoader.getInstance().getWaitForElementTimeOut();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitForElementTimeOut)).ignoring(StaleElementReferenceException.class);
        this.assertionTimeOut = ConfigLoader.getInstance().getAssertionTimeOut();
        this.assertionWait = new WebDriverWait(driver, Duration.ofSeconds(assertionTimeOut)).ignoring(StaleElementReferenceException.class);
        this.javascript = (JavascriptExecutor) driver;
    }

    protected WebElement waitUntilElementIsDisplayed(By locator) {
        this.wait.until((ExpectedConditions.visibilityOfElementLocated(locator)));
        return this.driver.findElement(locator);
    }

    protected void scrollIntoViewIfNeeded(By locator) {
        WebElement webElement = driver.findElement(locator);
        try {
            //   System.out.println("Element is displayed. Checking if element is in the viewport");
            String Boolean = javascript.executeScript("const rectangle = arguments[0].getBoundingClientRect();\n" +
                    "    return (\n" +
                    "        rectangle.top >= 0 &&\n" +
                    "        rectangle.left >= 0 &&\n" +
                    "        rectangle.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&\n" +
                    "        rectangle.right <= (window.innerWidth || document.documentElement.clientWidth)\n" +
                    "    );", webElement).toString();
            if (Boolean == "false") {
                //   System.out.println("Element is not in the viewport, attempting to scroll into view");
                javascript.executeScript("arguments[0].scrollIntoView(true);", webElement);
                //   System.out.println("Element is in the viewport");
            } else if (Boolean == "true") {
                //    System.out.println("Element is in the viewport");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected By convertWebElementToByLocator(WebElement webElement){
        String stringOfWebElement = webElement.toString();
        String almostThere = stringOfWebElement.split("->")[1].split(":")[1].trim();
        String locator = almostThere.substring(0, almostThere.length() - 1) + ""; // Remove "]", last char in the string
        return stringOfWebElement.contains("css selector") ? By.cssSelector(locator) : By.xpath(locator);
        // WebElement: [[ChromeDriver: chrome on MAC (c543264f20cfb957604d3294862c1ec1)] -> xpath: //h2[@class='has-text-align-center']]
        // Expected output: //h2[@class='has-text-align-center'

    }
}
