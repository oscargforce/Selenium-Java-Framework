package pageobjects.checkout_page;

import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;

public class CheckoutPage {
    public BillingFormComponent getBillingFormComponent;
    public ReviewOrderComponent getReviewOrderComponent;

    public CheckoutPage(WebDriver driver) throws FileNotFoundException {
        this.getBillingFormComponent = new BillingFormComponent(driver);
        this.getReviewOrderComponent = new ReviewOrderComponent(driver);
    }


}
