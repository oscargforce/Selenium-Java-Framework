package selenium_tests;

import io.qameta.allure.Description;
import json_utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.checkout_page.BillingFormComponent;
import pageobjects.checkout_page.CheckoutPage;
import pageobjects.home_page.HomePage;
import web_element_behaviour.Expect;

import java.io.IOException;
import java.util.List;

public class CheckoutPageTests extends BaseTest {

    private Expect expect;
    private CheckoutPage checkoutPage;
    private CheckoutTextsPojo checkOutTexts;
    private ProductsPojo product;
    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.checkoutPage = new CheckoutPage(getDriver());
        this.expect = new Expect(getDriver());
        this.checkOutTexts = JsonUtility.deserializeJson("CheckoutPageTexts.json", CheckoutTextsPojo.class);
        this.product = JsonUtility.deserializeJson("products.json", ProductsPojo.class);
        goToWebSite("/");
    }

    @Description("It should not work to place a order without filling in the required fields")
    @Test
    public void requiredFieldsFeature() throws Exception {
        addProductToCartApi(product.id, "1");
        goToWebSite("/checkout");
        checkoutPage.getReviewOrderComponent.placeTheOrder();
        By errorMessage = checkoutPage.getBillingFormComponent.requiredFieldsErrorMsgElement;
        expect.element(errorMessage).toHaveText(checkOutTexts.requiredFieldsErrorMessage);
    }

    @Description("Expect the option to ship to a different address then the billing address to be visible")
    @Test
    public void shipToADifferentAddressFeature() throws Exception {
        BillingFormComponent billingForm = checkoutPage.getBillingFormComponent;
        By shipToADifferentAddressForm = billingForm.shipToADifferentAddressFormElement;

        addProductToCartApi(product.id, "1");
        goToWebSite("/checkout");
        expect.element(billingForm.shipToADifferentAddressTitleElement).toHaveText(checkOutTexts.shipToADifferentAddress);
        billingForm.clickOnShipToADifferentAddress();
        expect.element(shipToADifferentAddressForm).toBeVisible();
        billingForm.clickOnShipToADifferentAddress();
        expect.element(shipToADifferentAddressForm).notToBeVisible();
    }

    @Description("Expect the option to fill in a form to create an new account to be visible")
    @Test
    public void createAnAccountFormFeature() throws Exception {
        BillingFormComponent billingForm = checkoutPage.getBillingFormComponent;
        By accountUsernameInput = billingForm.accountUsernameInputElement;

        addProductToCartApi(product.id, "1");
        goToWebSite("/checkout");
        expect.element(billingForm.createAnAccountLabelElement).toHaveText(checkOutTexts.createAnAccount);
        billingForm.clickOnCreateAnAccount();
        expect.element(accountUsernameInput).toBeVisible();
        billingForm.clickOnCreateAnAccount();
        expect.element(accountUsernameInput).notToBeVisible();
    }

    @Description("Expect the place order button to have visible text ")
    @Test
    public void thePlaceOrderButtonShouldHaveVisibleText() throws Exception {
        By placeOrderButton = checkoutPage.getReviewOrderComponent.placeOrderBtnElement;

        addProductToCartApi(product.id, "1");
        goToWebSite("/checkout");
        expect.element(placeOrderButton).toHaveText(checkOutTexts.placeOrder);

    }
}
