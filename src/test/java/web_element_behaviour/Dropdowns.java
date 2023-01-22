package web_element_behaviour;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileNotFoundException;

public class Dropdowns extends WebElementBehaviourBase {

    public Dropdowns(WebDriver driver) throws FileNotFoundException {
        super(driver);
    }

    public void selectByValue(String value, By locator) throws Exception {
        wait.until(driver -> driver.findElement(locator).isDisplayed() && driver.findElement(locator).isEnabled());
        scrollIntoViewIfNeeded(locator);
        Select dropdown = new Select(driver.findElement(locator));
        wait.until((driver) -> {
            try {
                String Boolean = javascript.executeScript("return arguments[0].isConnected;", driver.findElement(locator)).toString();
                if (Boolean.equals("true")) {
                    dropdown.selectByValue(value);
                    return true;
                } else {
                    System.out.println("567");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }


}
