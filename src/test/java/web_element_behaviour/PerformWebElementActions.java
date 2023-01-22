package web_element_behaviour;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.util.List;


public class PerformWebElementActions {
    private static PerformWebElementActions instance;
    private final Clicks typeOfClicks;
    private final Inputs inputs;
    private final FindElements findElements;
    private final Dropdowns dropdowns;
    private final Hover hover;
    private final TextContent textContent;
    private WebDriver driver;

    private PerformWebElementActions(WebDriver driver) throws FileNotFoundException {
        this.driver = driver;
        this.typeOfClicks = new Clicks(driver);
        this.inputs = new Inputs(driver);
        this.findElements = new FindElements(driver);
        this.dropdowns = new Dropdowns(driver);
        this.hover = new Hover(driver);
        this.textContent = new TextContent(driver);

    }

    public static PerformWebElementActions getInstance(WebDriver driver) throws FileNotFoundException {
        if (instance != null && driver.equals(instance.driver)) return instance;
        instance = new PerformWebElementActions(driver);
        return instance;
    }

    @Step("Clicking on locator {locator}")
    public void clickOn(By locator) throws Exception {
        typeOfClicks.webDriverClick(locator);
    }

    @Step("Clicking on locator {webElement}")
    public void clickOn(WebElement webElement) throws Exception {
        typeOfClicks.webDriverClick(webElement);
    }

    @Step("Fill input with {inputValue} {locator}")
    public void fillInputWithValue(String inputValue, By locator) throws Exception {
        inputs.webDriverSendKeys(inputValue, locator);
    }

    @Step("Get list of elements {locator}")
    public List<WebElement> getListOfElements(By locator) {
        return findElements.getList(locator);
    }

    @Step("Select value {value} by dropdown locator {locator}")
    public void selectDropdownValue(String value, By locator) throws Exception {
        dropdowns.selectByValue(value, locator);
    }

    @Step("Hover mouse over element: {locator}")
    public void hoverMouseOver(By locator) {
        hover.mouseHover(locator);
    }

    public String getTextContentOfElement(By locator) {
        return textContent.getTextContent(locator);
    }

}
