package pageobjects.checkout_page;

import io.qameta.allure.Step;
import json_utilities.BillingAddressPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_element_behaviour.PerformWebElementActions;

import java.io.FileNotFoundException;

public class BillingFormComponent {
    private PerformWebElementActions perform;
    public By requiredFieldsErrorMsgElement = By.cssSelector("ul[class='woocommerce-error']");
    public By createAnAccountLabelElement = By.cssSelector(".woocommerce-account-fields label[class*='woocommerce-form__label']");
    public By accountUsernameInputElement = By.cssSelector("#account_username");
    public By shipToADifferentAddressTitleElement = By.cssSelector("#ship-to-different-address");
    public By shipToADifferentAddressFormElement = By.cssSelector("#shipping_first_name");
    private final By firstNameInputElement = By.cssSelector("#billing_first_name");
    private final By lastNameInputElement = By.cssSelector("#billing_last_name");
    private final By countryInputElement = By.cssSelector("#select2-billing_country-container");
    private final By addressInputElement = By.cssSelector("#billing_address_1");
    private final By cityInputElement = By.cssSelector("#billing_city");
    private final By selectStateInputElement = By.cssSelector("#select2-billing_state-container");
    private final By zipCodeInputElement = By.cssSelector("#billing_postcode");
    private final By emailInputElement = By.cssSelector("#billing_email");
    private final By shipToADifferentAddressCheckBoxElement = By.cssSelector("#ship-to-different-address-checkbox");
    private final By createAnAccountCheckBoxElement = By.cssSelector("#createaccount");

    public BillingFormComponent(WebDriver driver) throws FileNotFoundException {
        this.perform = PerformWebElementActions.getInstance(driver);
    }

    @Step
    public void setBillingAddress(BillingAddressPojo billingAddress) throws Exception {
        perform.fillInputWithValue(billingAddress.firstName, firstNameInputElement);
        perform.fillInputWithValue(billingAddress.lastName, lastNameInputElement);
        selectCountryOrState(countryInputElement, billingAddress.country);
        perform.fillInputWithValue(billingAddress.address, addressInputElement);
        perform.fillInputWithValue(billingAddress.city, cityInputElement);
        selectCountryOrState(selectStateInputElement, billingAddress.state);
        perform.fillInputWithValue(billingAddress.zipcode, zipCodeInputElement);
        perform.fillInputWithValue(billingAddress.emailAddress, emailInputElement);
    }

    private void selectCountryOrState(By locator, String nameOfCountryOrState) throws Exception {
        perform.clickOn(locator);
        perform.clickOn(By.xpath("//li[text()='" + nameOfCountryOrState + "']"));
    }

    public void clickOnShipToADifferentAddress() throws Exception {
        perform.clickOn(shipToADifferentAddressCheckBoxElement);
    }
    public void clickOnCreateAnAccount() throws Exception {
        perform.clickOn(createAnAccountCheckBoxElement);
    }
}
