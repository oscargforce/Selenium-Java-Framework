package pageobjects.store_page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;

public class SearchComponent {
    private final PerformWebElementActions perform;
    private final By searchInputElement = By.cssSelector("#woocommerce-product-search-field-0");
    private final By searchBtnElement = By.cssSelector("form[role='search'] button");
    private final By browseByCategorySelectElement = By.cssSelector("#product_cat");

    public SearchComponent(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);

    }

    public void browseByCategory(String dropdownValue) throws Exception {
        perform.selectDropdownValue(dropdownValue, browseByCategorySelectElement);
    }

    public SearchComponent searchForProduct(String nameOfProduct) throws Exception {
        perform.fillInputWithValue(nameOfProduct, searchInputElement);
        perform.clickOn(searchBtnElement);
        return this;
    }


}
