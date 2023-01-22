package pageobjects.store_page;

import org.openqa.selenium.WebDriver;
import pageobjects.common_components.HeaderNavigationBar;
import web_element_behaviour.PerformWebElementActions;
import java.io.FileNotFoundException;

public class StorePage {
    public SearchComponent getSearchComponent;
    public ProductsComponent getProductsComponent;
    public HeaderNavigationBar getHeaderNavBarComponent;
    private PerformWebElementActions perform;

    public StorePage(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);
        this.getHeaderNavBarComponent = HeaderNavigationBar.getInstance(driver);
        this.getSearchComponent = new SearchComponent(driver);
        this.getProductsComponent = new ProductsComponent(driver);
    }

    public void goToCartPage() throws Exception {
        getHeaderNavBarComponent.clickOnCartIconElement();
    }
}
