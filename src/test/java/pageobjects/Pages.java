package pageobjects;

import org.openqa.selenium.WebDriver;
import pageobjects.checkout_page.CheckoutPage;
import pageobjects.home_page.HomePage;
import pageobjects.store_page.StorePage;

import java.io.FileNotFoundException;

public class Pages {
    public HomePage getHomePage;
    public StorePage getStorePage;
    public CartPage getCartPage;
    public CheckoutPage getCheckoutPage;

    public Pages(WebDriver driver) throws FileNotFoundException {
        this.getHomePage = new HomePage(driver);
        this.getStorePage = new StorePage(driver);
        this.getCartPage = new CartPage(driver);
        this.getCheckoutPage = new CheckoutPage(driver);
    }
}
