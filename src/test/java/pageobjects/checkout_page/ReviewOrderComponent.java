package pageobjects.checkout_page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;

public class ReviewOrderComponent {
    private final By directBankTransferOptionElement = By.cssSelector("label[for='payment_method_bacs']");
    private final By cashOnDeliveryOptionElement = By.cssSelector("label[for='payment_method_cod']");
    public By placeOrderBtnElement = By.cssSelector("#place_order");
    public By orderHasBeenPlacedSuccessfullyElement = By.cssSelector(".woocommerce-order p");
    private PerformWebElementActions perform;

    public ReviewOrderComponent(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);
    }

    @Step
    public ReviewOrderComponent setPaymentMethodToDirectBankTransfer() throws Exception {
        perform.clickOn(directBankTransferOptionElement);
        return this;
    }

    @Step
    public ReviewOrderComponent setPaymentMethodToCashOnDelivery() throws Exception {
        perform.clickOn(cashOnDeliveryOptionElement);
        return this;
    }

    @Step
    public ReviewOrderComponent placeTheOrder() throws Exception {
        perform.clickOn(placeOrderBtnElement);
        return this;
    }


}
