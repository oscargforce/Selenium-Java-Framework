package pageobjects.home_page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.common_components.HeaderNavigationBar;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;

public class HomePage {
    private PerformWebElementActions perform;
    public FeaturedProductsComponent getFeaturedProductsComponent;
    public HeaderNavigationBar getHeaderNavBarComponent;

    public HeaderComponent getHeaderComponent;

    public HomePage(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);
        this.getFeaturedProductsComponent = new FeaturedProductsComponent(driver);
        this.getHeaderNavBarComponent = HeaderNavigationBar.getInstance(driver);
        this.getHeaderComponent = new HeaderComponent(driver);
    }

}
