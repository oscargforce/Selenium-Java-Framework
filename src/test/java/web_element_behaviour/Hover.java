package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.FileNotFoundException;

public class Hover extends WebElementBehaviourBase {
    private final Actions actions;

    public Hover(WebDriver driver) throws FileNotFoundException {
        super(driver);
        this.actions = new Actions(driver);
    }

    public void mouseHover(By locator) {
        waitUntilElementIsDisplayed(locator);
        wait.until(driver -> {
            try {
                actions.moveToElement(driver.findElement(locator)).perform();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
