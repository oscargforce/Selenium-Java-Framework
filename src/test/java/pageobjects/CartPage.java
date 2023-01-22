package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class CartPage {
    private PerformWebElementActions perform;
    public By productTitleElement = By.cssSelector("[data-title='Product']");
    public By priceTitleElement = By.cssSelector("[data-title='Price']");
    public By subtotalTitleElement = By.cssSelector("[data-title='Subtotal']");
    public By couponDoesNotExistMsgElement = By.cssSelector("ul[class='woocommerce-error']");
    public By yourItemWasRemovedFromCartMsgElement = By.cssSelector(".woocommerce-notices-wrapper .woocommerce-message");
    private By proceedToCheckoutBtnElement = By.cssSelector("a[href*='/checkout']");
    private By couponInputElement = By.cssSelector("#coupon_code");
    private By applyCouponBtnElement = By.cssSelector("button[name='apply_coupon']");
    private By restoreItemToCartBtnElement = By.cssSelector(".restore-item");
    private By updateCartBtnElement = By.cssSelector("[name='update_cart']");
    private By quantityInputElement = By.cssSelector("[data-title='Quantity'] input");

    public CartPage(WebDriver driver) throws FileNotFoundException {
        perform = PerformWebElementActions.getInstance(driver);
    }

    public void goToCheckOutPage() throws Exception {
        perform.clickOn(proceedToCheckoutBtnElement);
    }

    public void setItemQuantityTo(int num) throws Exception {
        perform.fillInputWithValue(Integer.toString(num), quantityInputElement);
        perform.clickOn(updateCartBtnElement);
    }
    private int convertPriceToInteger(String price){
        int priceInteger = Integer.parseInt(price.split(Pattern.quote("."))[0].replace("$", "").trim());
        return priceInteger;
    }

    public int getSubTotalPrice() {
       String textContent =  perform.getTextContentOfElement(subtotalTitleElement);
       int currentSubTotalPrice = convertPriceToInteger(textContent);
       return currentSubTotalPrice;
    }

    public void removeItemFromCart(String productId) throws Exception {
        perform.clickOn(By.cssSelector(".product-remove [data-product_id='"+productId+"']"));
    }

    public void restoreItemToCart(String productId) throws Exception {
        perform.clickOn(restoreItemToCartBtnElement);
    }

    public void addCouponCode(String code) throws Exception {
        perform.fillInputWithValue(code, couponInputElement);
        perform.clickOn(applyCouponBtnElement);
    }
}