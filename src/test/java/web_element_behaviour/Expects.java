/*

Deprecated, keeping just to showcase different approaches.


package web_element_behaviour;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;

public class Expects extends WebElementBehaviourBase {
    private SoftAssert softAssert;

    public Expects(WebDriver driver) throws FileNotFoundException {
        super(driver);
        this.softAssert = new SoftAssert();
    }

    private String customErrorMessage(By locator, String actualText, String expectedText, String typeOfContent) {
        return String.format("Wrong %s found on the element \n Expected: %s \n Received: %s \n By locator: %s ", typeOfContent, expectedText, actualText, locator);
    }



    @Step("expect elementToHaveText: {expectedText}, {locator}")
    public void elementToHaveText(String expectedText, By locator) throws AssertionError {
        this.waitUntilElementIsDisplayed(locator);
        this.scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until((driver) -> driver.findElement(locator).getText().equals(expectedText));
        } catch (Exception e) {
             String actualText = driver.findElement(locator).getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }

    @Step("expect softAssertElementToHaveText: {expectedText}, {webElement}")
    public SoftAssert softAssertElementToHaveText(String expectedText, By locator) {
        this.waitUntilElementIsDisplayed(locator);
        this.scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until((driver) -> driver.findElement(locator).getText().equals(expectedText));
        } catch (Exception e) {
            String actualText = driver.findElement(locator).getText();
            this.softAssert.assertEquals(actualText, expectedText, this.customErrorMessage(locator, actualText, expectedText, "text content"));
        }
        return this.softAssert;
    }


    @Step("expect elementToContainText: {expectedText}, {locator}")
    public void elementToContainText(String expectedText, By locator) throws AssertionError {
        this.waitUntilElementIsDisplayed(locator);
        this.scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until((driver) -> driver.findElement(locator).getText().contains(expectedText));
        } catch (Exception e) {
             String actualText = driver.findElement(locator).getText();
            throw new AssertionError(this.customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }


    @Step("expect softAssertElementToContainText: {expectedText}, {webElement}")
    public SoftAssert softAssertElementToContainText(String expectedText, By locator) {
        this.waitUntilElementIsDisplayed(locator);
        this.scrollIntoViewIfNeeded(locator);
        try {
            this.assertionWait.until((driver) -> driver.findElement(locator).getText().contains(expectedText));
        } catch (Exception e) {
            String actualText = driver.findElement(locator).getText();
            this.softAssert.assertEquals(actualText, expectedText, this.customErrorMessage(locator, actualText, expectedText, "text content"));
        }
        return this.softAssert;
    }

    @Step("expect elementToHaveValue: {expectedValue}, {locator}")
    public void elementToHaveValue(String expectedValue, By locator) throws AssertionError {
        try {
            this.waitUntilElementIsDisplayed(locator);
            this.scrollIntoViewIfNeeded(locator);
            this.assertionWait.until((driver) -> driver.findElement(locator).getAttribute("value").equals(expectedValue));
        } catch (TimeoutException e) {
            String actualValue = this.driver.findElement(locator).getAttribute("value");
            throw new AssertionError(this.customErrorMessage(locator, actualValue, expectedValue, "value"));
        }
    }

    @Step("expect softAssertElementToHaveValue: {expectedValue}, {locator}")
    public SoftAssert softAssertElementToHaveValue(String expectedValue, By locator){
        try {
            this.waitUntilElementIsDisplayed(locator);
            this.scrollIntoViewIfNeeded(locator);
            this.assertionWait.until((driver) -> driver.findElement(locator).getAttribute("value").equals(expectedValue));
        } catch (TimeoutException e) {
            String actualValue = this.driver.findElement(locator).getAttribute("value");
            this.softAssert.assertEquals(actualValue, expectedValue, this.customErrorMessage(locator, actualValue, expectedValue, "text content"));
        }
        return this.softAssert;
    }
}

 */
