package selenium_tests;

import json_utilities.BillingAddressPojo;
import json_utilities.JsonUtility;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Pages;
import pageobjects.store_page.StorePage;
import pageobjects.checkout_page.CheckoutPage;
import web_element_behaviour.Expect;

import java.io.IOException;

public class e2e extends BaseTest {

    private Pages pages;
    private Expect expect;
    private BillingAddressPojo billingAdress;
    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.pages = new Pages(getDriver());
        this.expect = new Expect(getDriver());
        this.billingAdress = JsonUtility.deserializeJson("BillingInfo.json", BillingAddressPojo.class);
    }
    @Test
    public void endToEndBuyAProduct() throws Exception {
        StorePage storePage = pages.getStorePage;
        CheckoutPage checkoutPage = pages.getCheckoutPage;

        goToWebSite("/");
        pages.getHomePage.getHeaderComponent.navigateToTheStorePage();
        storePage.getSearchComponent.searchForProduct("blue");
        storePage.getProductsComponent.addProductToCart("Blue Shoes");

        expect.element(storePage.getProductsComponent.headerTitleElement).toContainText("blue");
        expect.element(storePage.getHeaderNavBarComponent.cartIconElement).toContainText("1");
        storePage.goToCartPage();
        pages.getCartPage.goToCheckOutPage();
        checkoutPage.getBillingFormComponent.setBillingAddress(billingAdress);
        checkoutPage
                .getReviewOrderComponent
                .setPaymentMethodToDirectBankTransfer()
                .placeTheOrder();
        By orderMessage = checkoutPage.getReviewOrderComponent.orderHasBeenPlacedSuccessfullyElement;
        expect.element(orderMessage).toHaveText("Thank you. Your order has been received.");

    }

    @Test
    public void endToEndBuyADifferentProduct() throws Exception {
        StorePage storePage = pages.getStorePage;
        CheckoutPage checkoutPage = pages.getCheckoutPage;

        goToWebSite("/");
        pages.getHomePage.getHeaderComponent.navigateToTheStorePage();
        storePage.getSearchComponent.searchForProduct("shoes");
        storePage.getProductsComponent.addProductToCart("Red Shoes");

        expect.element(storePage.getProductsComponent.headerTitleElement).toContainText("shoes");
        expect.element(storePage.getHeaderNavBarComponent.cartIconElement).toContainText("1");
        storePage.goToCartPage();
        pages.getCartPage.goToCheckOutPage();
        checkoutPage.getBillingFormComponent.setBillingAddress(billingAdress);
        checkoutPage
                .getReviewOrderComponent
                .setPaymentMethodToDirectBankTransfer()
                .placeTheOrder();
        By orderMessage = checkoutPage.getReviewOrderComponent.orderHasBeenPlacedSuccessfullyElement;
        expect.element(orderMessage).toHaveText("Thank you. Your order has been received.");

    }

}
