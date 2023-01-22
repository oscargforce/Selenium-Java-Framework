package selenium_tests;

import io.qameta.allure.Description;
import json_utilities.HomePageTextsPojo;
import json_utilities.JsonUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.home_page.HomePage;
import web_element_behaviour.Expect;
import java.io.IOException;
import java.util.List;

public class HomePageTests extends BaseTest {
    private HomePage homePage;
    private Expect expect;
    private HomePageTextsPojo homePageTextFor;

    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.homePage = new HomePage(getDriver());
        this.expect = new Expect(getDriver());
        this.homePageTextFor = JsonUtility.deserializeJson("HomePageTexts.json", HomePageTextsPojo.class);
    }

    @Description("Expect featured products to have add to cart buttons.")
    @Test
    public void featuredProductsToBeVisible() throws Exception {
        goToWebSite("/");
        List<WebElement> listOfProducts = homePage.getFeaturedProductsComponent.getListOfFeaturedProducts();

        for (WebElement product : listOfProducts) {
            expect.element(product).toContainText(homePageTextFor.addToCartButtons);
        }
    }

    @Description("Expect a user to be able to add featured products to cart.")
    @Test
    public void addFeaturedProductsToCart() throws Exception {
        goToWebSite("/");
        String numberOfProducts = homePage.getFeaturedProductsComponent.addAllFeaturedProductsToCart();
        By cartIcon = homePage.getHeaderNavBarComponent.cartIconElement;
        expect.element(cartIcon).toContainText(numberOfProducts);
    }

    @Description("Expect global shipping, best quality, best offers, secure payments to be visible")
    @Test
    public void commercialInfoToBeVisible() throws Exception {
        goToWebSite("/");
        List<WebElement> titles = homePage.getFeaturedProductsComponent.getListOfCommercialInfoTitles();
        for (int i = 0; i < titles.size(); i++) {
            expect.element(titles.get(i)).toHaveText(homePageTextFor.commercialTitles[i]);
        }
    }

    @Description("Expect a user to be navigated to /contact-us when clicking on find more")
    @Test
    public void findMoreButtonNavigation() throws Exception {
        goToWebSite("/");
        homePage.getHeaderComponent.clickOnFindMore();
        expect.urlToContainText("/contact-us");
        // Navigation to store is being tested in the e2e test cases.
    }

}