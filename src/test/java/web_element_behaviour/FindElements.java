package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.util.List;

public class FindElements extends WebElementBehaviourBase {
    public FindElements(WebDriver driver) throws FileNotFoundException {
        super(driver);
    }

    public List<WebElement> getList(By locator) {
        waitUntilElementIsDisplayed(locator);
        scrollIntoViewIfNeeded(locator);
        return driver.findElements(locator);
    }

}
