package pageobjects.store_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;
import java.util.List;

public class ProductsComponent {
    private final PerformWebElementActions perform;
    private final By sortProductsSelectElement = By.cssSelector("select[name='orderby']");
    private final By productTitleElements = By.cssSelector("h2[class*='product__title']");
    private final By priceTagElements = By.cssSelector("#main .woocommerce-Price-amount");

    private final By productCategoryTagElements = By.cssSelector("span[class='ast-woo-product-category']");
    public By addToCartBtnElements = By.cssSelector(".add_to_cart_button");
    public By headerTitleElement = By.cssSelector("h1[class*='page-title']");


    public ProductsComponent(WebDriver driver) throws FileNotFoundException {
        perform = PerformWebElementActions.getInstance(driver);
    }

    public void addProductToCart(String productName) throws Exception {
        perform.clickOn(By.cssSelector("a[aria-label*='" + productName + "']"));
    }

    public void sortProductsBy(String dropdownValue) throws Exception {
        perform.selectDropdownValue(dropdownValue, sortProductsSelectElement);
    }

    public List<WebElement> getAllProductTitlesInCurrentPage() {
        return perform.getListOfElements(productTitleElements);
    }

    public List<WebElement> getAllAddToCartButtonsOnTheCurrentPage() {
        return perform.getListOfElements(addToCartBtnElements);
    }

    public List<WebElement> getThePriceTagOnAllProducts() {
        return perform.getListOfElements(priceTagElements);
    }

    public List<WebElement> getAllProductsCategoryTag() {
        return perform.getListOfElements(productCategoryTagElements);
    }
}
