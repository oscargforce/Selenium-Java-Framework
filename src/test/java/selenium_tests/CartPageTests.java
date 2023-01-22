package selenium_tests;

import io.qameta.allure.Description;
import json_utilities.CartPagePojo;
import json_utilities.JsonUtility;
import json_utilities.ProductsPojo;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobjects.CartPage;
import web_element_behaviour.Expect;
import java.io.IOException;

public class CartPageTests extends BaseTest {

    private CartPage cartPage;
    private Expect expect;
    private ProductsPojo product;


    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.cartPage = new CartPage(getDriver());
        this.expect = new Expect(getDriver());
        this.product = JsonUtility.deserializeJson("products.json", ProductsPojo.class);
        goToWebSite("/");
    }

    @Description("It should work to increase the quantity of the product on the cart page, for example 1 - > 2 -> 3")
    @Test
    public void increaseItemQuantityFeature() throws Exception {
        addProductToCartApi(product.id, "1");
        goToWebSite("/cart");
        expect.element(cartPage.productTitleElement).toHaveText(product.name);
        int subTotalPriceBefore = cartPage.getSubTotalPrice();
        cartPage.setItemQuantityTo(2);
        String subTotalPriceAfter = "$" + subTotalPriceBefore * 2 + ".00";
        expect.element(cartPage.subtotalTitleElement).toHaveText(subTotalPriceAfter);

    }
    @Description("It should work to remove and restore an item from cart")
    @Test
    public void removeAndRestoreItemsFromCartFeature() throws Exception {
        addProductToCartApi(product.id, "1");
        goToWebSite("/cart");
        cartPage.removeItemFromCart(product.id);
        By yourItemWasRemovedFromCartMsgElement = cartPage.yourItemWasRemovedFromCartMsgElement;
        SoftAssert softAssert = expect.element(yourItemWasRemovedFromCartMsgElement).softAssertToContainText("Undo?");
        cartPage.restoreItemToCart(product.id);
        expect.element(cartPage.priceTitleElement).toBeVisible();
        softAssert.assertAll();
    }

    @Description("It should not work to add an invalid coupon code")
    @Test
    public void expectErrorMessageIfEnteringInvalidCouponCode() throws Exception {
        // This is a practise website and I have no idea if there are any valid coupon codes.
        CartPagePojo cartTexts = JsonUtility.deserializeJson("CartPageTexts.json", CartPagePojo.class);
        addProductToCartApi(product.id, "1");
        goToWebSite("/cart");
        cartPage.addCouponCode("IM INVALID");
        By couponCodeDoesNotExistMessage = cartPage.couponDoesNotExistMsgElement;
        expect.element(couponCodeDoesNotExistMessage).toHaveText(cartTexts.couponDoesNotExistMsg);
    }


}
