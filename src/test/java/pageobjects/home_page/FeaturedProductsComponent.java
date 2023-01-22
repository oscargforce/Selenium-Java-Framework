package pageobjects.home_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;
import java.util.List;

public class FeaturedProductsComponent {
    private final By addToCartBtnElements = By.cssSelector("ul[class='products columns-5'] li .add_to_cart_button");
    private final By commercialInfoTitleElements = By.cssSelector("h6[class='has-text-align-center']");
    public By featuredProductsItemsElement = By.cssSelector("ul[class='products columns-5'] li");
    private PerformWebElementActions perform;

    public FeaturedProductsComponent(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);
    }

    public List<WebElement> getListOfFeaturedProducts() {
        List<WebElement> listOfProducts = perform.getListOfElements(featuredProductsItemsElement);
        return listOfProducts;
    }

    public String addAllFeaturedProductsToCart() throws Exception {
        List<WebElement> listOfProducts = perform.getListOfElements(addToCartBtnElements);
        int numberOfProducts = listOfProducts.size();
        for (WebElement product : listOfProducts)
            perform.clickOn(product);

        return Integer.toString(numberOfProducts);
    }
    public List<WebElement> getListOfCommercialInfoTitles() {
        List<WebElement> listOfTitles = perform.getListOfElements(commercialInfoTitleElements);
        return listOfTitles;
    }


}
