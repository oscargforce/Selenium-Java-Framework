package utilities;


import config.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class CartApi {
    @Step
    public List<Cookie> addItemToCart(String productId, String quantity) throws FileNotFoundException {
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String, Object> formData = new HashMap<>();
        formData.put("product_sku", "");
        formData.put("product_id", productId);
        formData.put("quantity", quantity);

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formData).log().all().
                when().
                post("/?wc-ajax=add_to_cart").
                then().log().all().
                extract().response();
        if (response.getStatusCode() >= 400) throw new RuntimeException(response.asPrettyString());
        Cookies cookies = response.getDetailedCookies();
        List<Cookie> seleniumCookies = convertRestAssuredCookiesToSeleniumCookies(cookies);
        return seleniumCookies;
    }

    @Step
    private List<Cookie> convertRestAssuredCookiesToSeleniumCookies(Cookies cookies) {
        List<org.openqa.selenium.Cookie> seleniumCookies = new ArrayList<>();
        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>();

        restAssuredCookies = cookies.asList();

        for (io.restassured.http.Cookie cookie : restAssuredCookies) {
            seleniumCookies.add(new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(),
                    cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured(), cookie.isHttpOnly(),
                    cookie.getSameSite()));
        }
        return seleniumCookies;
    }
}
