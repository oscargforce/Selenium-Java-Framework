package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;

public class Clicks extends WebElementBehaviourBase {

    public Clicks(WebDriver driver) throws FileNotFoundException {
        super(driver);
    }

    private void waitUntilElementIsClickable(By locator) {
        try {
            this.wait.until((driver) -> driver.findElement(locator).isDisplayed() && driver.findElement(locator).isEnabled());
        } catch (Exception e) {
            throw new TimeoutException("Tried waiting for " + this.time + " seconds for locator: " + locator + " to be clickable. ");
        }
    }

    public void webDriverClick(By locator) throws Exception {
        this.waitUntilElementIsClickable(locator);
        this.scrollIntoViewIfNeeded(locator);
        wait.until((driver) -> {
            try {
                driver.findElement(locator).click();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    /**
     * Use with care. The method won't send out multiple driver.findElement() requests, so if staleElementException is thrown, the method will fail, no matter how long you retry.
     * This is due to that you are using the same "element" variable. Use this method with By locator instead whenever you can. If you use By locator, staleElementException is history :)
     * @param webElement
     * @throws Exception
     */
    public void webDriverClick(WebElement webElement) throws Exception {
        wait.until((driver) -> {
            try {
                webElement.click();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
