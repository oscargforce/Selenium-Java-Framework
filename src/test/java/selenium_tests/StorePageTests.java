package selenium_tests;

import io.qameta.allure.Description;
import json_utilities.JsonUtility;
import json_utilities.StorePageTextsPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.store_page.StorePage;
import web_element_behaviour.Expect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StorePageTests extends BaseTest {

    private StorePage storePage;
    private Expect expect;
    private StorePageTextsPojo storePageTexts;

    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.storePage = new StorePage(getDriver());
        this.expect = new Expect(getDriver());
        this.storePageTexts = JsonUtility.deserializeJson("StorePageTexts.json", StorePageTextsPojo.class);
    }

    @Description("As a user, when I search for a product only related items should show in the store page.")
    @Test
    public void searchFeature() throws Exception {
        goToWebSite("/store");
        storePage.getSearchComponent.searchForProduct("Jeans");
        List<WebElement> productTitles = storePage.getProductsComponent.getAllProductTitlesInCurrentPage();

        for (WebElement productTitle : productTitles)
            expect.element(productTitle).toContainText("Jeans");
    }

    @Description("Expect all add to cart buttons to have visible text")
    @Test
    public void addToCartButtonsShouldHaveVisibleText() throws Exception {
        goToWebSite("/store");
        List<WebElement> addToCartBtns = storePage.getProductsComponent.getAllAddToCartButtonsOnTheCurrentPage();
        for (WebElement addToCartBtn : addToCartBtns)
            expect.element(addToCartBtn).toHaveText(storePageTexts.addToCart);
    }

    @Description("Products should be filtered based on the selected category filter")
    @Test
    public void browseByCategoryFeature() throws Exception {
        goToWebSite("/product-category/men/");
       // webpage category selector is buggy. Going directly by url instead, storePage.getSearchComponent.browseByCategory("men");
        List<WebElement> productsCategoryTag = storePage.getProductsComponent.getAllProductsCategoryTag();
        for (WebElement categoryTag : productsCategoryTag) {
            expect.element(categoryTag).toHaveText("Men");
        }
    }
    @Description("it should work to add an Item to the cart")
    @Test
    public void addProductToCartFeature() throws Exception {
        goToWebSite("/store");
        storePage.getProductsComponent.addProductToCart("Blue Shoes");
       By blueShoes = storePage.getHeaderNavBarComponent
                .mouseHoverOverTheCartIcon()
                .getAddedItemInCartWidgetElement("blue-shoes");
       expect.element(blueShoes).toHaveText("Blue Shoes");

    }

    @Description("Verify text is displayed if no items are in the cart widget")
    @Test
    public void noItemsInCartWidgetFeature() throws Exception {
        goToWebSite("/store");
        storePage.getHeaderNavBarComponent.mouseHoverOverTheCartIcon();
        By emptyCartMessage = storePage.getHeaderNavBarComponent.emptyCartWidgetMessageElement;
        expect.element(emptyCartMessage).toHaveText(storePageTexts.emptyCartMessage);
    }

    @Description("Expect sorting products based on price to work")
    @Test
    public void sortProductsFeature() throws Exception {
        /*
        Normally I would abstract this code, but since it's rather complicated to follow if abstracted,
        I've decided to keep the logic in the test case for demo purposes.
        */
        List<Integer> priceArrayList = new ArrayList<Integer>();
        goToWebSite("/store");
        // Filter by highest price to the lowest price
        storePage.getProductsComponent.sortProductsBy("price-desc");
        // waiting until page has finished loading.
        expect.urlToContainText("orderby=price-desc");

        // returns driver.findElements(locator);
        List<WebElement> priceTags = storePage.getProductsComponent.getThePriceTagOnAllProducts();
        for (int i = 0; i < priceTags.size(); i++) {
            // returns "$25.00"
            String priceAmount = priceTags.get(i).getText();
            // splits to -> $25, replace $ with "" and convert 25 to integer.
            int price = Integer.parseInt(priceAmount.split(Pattern.quote("."))[0].replace("$", "").trim());
            // Add to new arrayList
            priceArrayList.add(price);
        }
        // Assert that price is in descending order.
        for (int i = 0; i < priceArrayList.size(); i++) {
            int currentPrice = priceArrayList.get(i);
            // if condition becomes true if = [10, 8, 9] instead of [10, 9, 8]
            if (priceArrayList.size() != i + 1 && currentPrice < priceArrayList.get(i+1)) {
               String errorMessage = String.format("Current product has lower price: %s then the following product %s, see index position %s and %s ",currentPrice, priceArrayList.get(i+1), i, i+1);
                throw new AssertionError(errorMessage);
            }



        }

    }
}
