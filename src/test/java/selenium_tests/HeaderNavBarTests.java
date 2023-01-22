package selenium_tests;

import io.qameta.allure.Description;
import json_utilities.HeaderNavBarPojo;
import json_utilities.JsonUtility;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.home_page.HomePage;
import web_element_behaviour.Expect;
import java.io.*;
import java.util.List;

public class HeaderNavBarTests extends BaseTest {
    private HomePage homePage;
    private Expect expect;
    private HeaderNavBarPojo headerNavBarTexts;

    @BeforeMethod
    private void initPageObjects() throws IOException {
        this.homePage = new HomePage(getDriver());
        this.expect = new Expect(getDriver());
        this.headerNavBarTexts = JsonUtility.deserializeJson("HeaderNavBarTexts.json", HeaderNavBarPojo.class);
    }

    @Description("Expect The header nav bar to have text content.")
    @Test
    public void menuItemsShouldHaveTexts() throws Exception {
        goToWebSite("/");
        List<WebElement> menuItems = homePage.getHeaderNavBarComponent.getListOfAllMenuItems();

        for (int i = 0; i < menuItems.size(); i++)
            expect.element(menuItems.get(i)).toHaveText(headerNavBarTexts.menuItems[i]);
    }

    @Description("Expect a user to be able to navigate through the navigation bar.")
    @Test(dataProvider = "menuItemsNavigationBar")
    public void headerNavBarNavigationFeature(String menuItem, String endpoint) throws Exception {
        goToWebSite("/");
        homePage.getHeaderNavBarComponent.clickOnItem(menuItem);
        expect.urlToContainText(endpoint);
    }

    @DataProvider
    public Object[] [] menuItemsNavigationBar() {
        return new Object[][] {
                {"Contact Us", "/contact-us"},
                {"About", "/about"},
                {"Account", "/account"},
                {"Accessories", "/product-category/accessories"},
                {"Women", "/product-category/women"},
                {"Men", "/product-category/men"},
                {"Store", "/store"},
                {"Home", "/"},
        };
    }

}

