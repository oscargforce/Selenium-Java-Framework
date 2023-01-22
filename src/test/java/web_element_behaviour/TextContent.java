package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;

public class TextContent extends WebElementBehaviourBase {
        public TextContent(WebDriver driver) throws FileNotFoundException {
            super(driver);
        }
        public String getTextContent(By locator) {
            waitUntilElementIsDisplayed(locator);
            return driver.findElement(locator).getText();
        }
}
