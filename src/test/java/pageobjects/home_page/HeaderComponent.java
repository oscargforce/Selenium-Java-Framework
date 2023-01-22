package pageobjects.home_page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;

public class HeaderComponent {
    private PerformWebElementActions perform;
    private final By shoppingBtnElement = By.cssSelector(".wp-block-button [href='/store']");
    private final By findMoreBtnElement = By.cssSelector(".wp-block-button a[href*='/contact-us']");
    public HeaderComponent(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);

    }

    @Step
    public void navigateToTheStorePage() throws Exception {
        perform.clickOn(shoppingBtnElement);
    }

    @Step
    public void clickOnFindMore() throws Exception {
        perform.clickOn(findMoreBtnElement);
    }




}
