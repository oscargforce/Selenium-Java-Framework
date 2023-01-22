package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;

public class Inputs extends WebElementBehaviourBase {

    // private Actions actions;
    private JavascriptExecutor javascript;

    protected Inputs(WebDriver driver) throws FileNotFoundException {
        super(driver);
        //   this.actions = new Actions(driver);
        this.javascript = (JavascriptExecutor) this.driver;
    }

    public void webDriverSendKeys(String inputValue, By locator) throws Exception {
        System.out.println("Waiting until locator " + locator + " is displayed");
        this.waitUntilElementIsDisplayed(locator);
        this.scrollIntoViewIfNeeded(locator);
        wait.until((driver) -> {
            try {
                WebElement webElement = driver.findElement(locator);
                System.out.println("Attempting to fill input, locator " + locator);
                javascript.executeScript("arguments[0].focus()", webElement);
                webElement.clear();
                webElement.sendKeys(inputValue);
                System.out.println("Input attempt was successful");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
