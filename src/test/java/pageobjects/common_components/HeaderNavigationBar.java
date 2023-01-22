package pageobjects.common_components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;
import java.util.List;

public class HeaderNavigationBar {

    private static HeaderNavigationBar instance;
    private final PerformWebElementActions perform;
    private final By menuItemElements = By.cssSelector("div[class*='site-header-primary-section-right'] li");
    public By cartIconElement = By.xpath("(//span[@class='count'])[1]");
    public By emptyCartWidgetMessageElement = By.cssSelector("p[class='woocommerce-mini-cart__empty-message']");
    private WebDriver driver;

    private HeaderNavigationBar(WebDriver driver) throws FileNotFoundException {
        this.driver = driver;
        this.perform = PerformWebElementActions.getInstance(driver);
    }

    public static HeaderNavigationBar getInstance(WebDriver driver) throws FileNotFoundException {
        if (instance != null && driver.equals(instance.driver)) return instance;
        instance = new HeaderNavigationBar(driver);
        return instance;
    }

    @Step
    public void clickOnCartIconElement() throws Exception {
        perform.clickOn(cartIconElement);
    }

    public By getAddedItemInCartWidgetElement(String productName) {
        return By.cssSelector("li[class*='mini_cart_item'] a[href*='" + productName + "']");
    }

    public HeaderNavigationBar mouseHoverOverTheCartIcon() {
        perform.hoverMouseOver(cartIconElement);
        return this;
    }

    @Step
    public List<WebElement> getListOfAllMenuItems() {
        return perform.getListOfElements(menuItemElements);
    }

    @Step("Clicking on menu item: \"{menuItem}\" on the header navigation bar")
    public void clickOnItem(String menuItem) throws Exception {
        perform.clickOn(By.xpath("//div[contains(@class,'site-header-primary-section-right')] //a[text()='" + menuItem + "']"));
    }

}
