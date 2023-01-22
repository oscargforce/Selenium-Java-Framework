package web_element_behaviour;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;

public class Expect extends WebElementBehaviourBase {

    private WebElement element;
    private By locator;
    private SoftAssert softAssert;

    public Expect(WebDriver driver) throws FileNotFoundException {
        super(driver);
        this.softAssert = new SoftAssert();
    }

    @Step("Expect element: {element}")
    public Expect element(WebElement element) {
        this.element = element;
        return this;
    }

    @Step("Expect element: {locator}")
    public Expect element(By locator) {
        this.locator = locator;
        return this;
    }

    private void waitForLocatorToBeVisible(By locator) {
        assertionWait.until(driver -> driver.findElement(locator).isDisplayed());
    }

    private String customErrorMessage(By locator, String actualText, String expectedText, String typeOfContent) {
        return String.format("Wrong %s found on the element \n Expected: %s \n Received: %s \n By locator: %s ", typeOfContent, expectedText, actualText, locator);
    }

    @Step("ToContainText: {expectedText}")
    public void toContainText(String expectedText) throws AssertionError {
        if (element != null) {
            locator = this.convertWebElementToByLocator(element);
            toContainTextWebElement(expectedText);
            return;
        }
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            assertionWait.until((driver) -> driver.findElement(locator).getText().contains(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = driver.findElement(locator).getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content"));
        }
    }

    private void toContainTextWebElement(String expectedText) throws AssertionError {
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            assertionWait.until(driver -> element.getText().contains(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = element.getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }

    @Step("toContainText: {expectedText}")
    public SoftAssert softAssertToContainText(String expectedText) throws AssertionError {
        if (element != null) locator = this.convertWebElementToByLocator(element);
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            assertionWait.until((driver) -> driver.findElement(locator).getText().contains(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = driver.findElement(locator).getText();
            softAssert.assertEquals(actualText, expectedText, this.customErrorMessage(locator, actualText, expectedText, "text content"));
        }
        return softAssert;
    }

    @Step("toHaveText: {expectedText}")
    public void toHaveText(String expectedText) throws AssertionError {
        if (element != null) {
            locator = this.convertWebElementToByLocator(element);
            toHaveTextWebElement(expectedText);
            return;
        }
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until(driver -> driver.findElement(locator).getText().equals(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = driver.findElement(locator).getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }

    private void toHaveTextWebElement(String expectedText) throws AssertionError {
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until(driver -> element.getText().equals(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = element.getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }

    @Step("toHaveText: {expectedText}")
    public SoftAssert softAssertToHaveText(String expectedText) throws AssertionError {
        if (element != null) locator = this.convertWebElementToByLocator(element);
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until(driver -> driver.findElement(locator).getText().equals(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = driver.findElement(locator).getText();
            softAssert.assertEquals(actualText, expectedText, this.customErrorMessage(locator, actualText, expectedText, "text content"));
        }
        return softAssert;
    }

    @Step("expect url to containText: {expectedText}")
    public void urlToContainText(String expectedText) throws AssertionError {
        try {
            this.assertionWait.until(driver -> driver.getCurrentUrl().contains(expectedText));
        } catch (TimeoutException e) {
            String actualText = driver.getCurrentUrl();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "url") + e);
        }
    }

    @Step
    public void toBeVisible() throws AssertionError {
        try {
            assertionWait.until(driver -> driver.findElement(locator).isDisplayed());
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new AssertionError("Waited " + assertionTimeOut + " seconds for locator: " + locator + " to be visible");
        }
    }

    @Step
    public void notToBeVisible() throws AssertionError {
        try {
            assertionWait.until(driver -> !driver.findElement(locator).isDisplayed());
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new AssertionError("Waited " + assertionTimeOut + " seconds for locator: " + locator + " to not be visible");
        }
    }
}
