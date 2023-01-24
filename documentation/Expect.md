
<h1 align="center">
  Expect Library
</h1>



![Expect](https://miro.medium.com/max/1400/1*P_zZlof7IhiohKQ7QEaXzA.webp)

Copied the syntax from Jest Library, because its just that good.

### Background.

Working with today's dynamic web, retries for assertions is a must.  
For example go to: https://askomdch.com/store/ Click on add to cart multiple times for any item.  
Observe the cart icon in the top right corner. There is a delay before all items are added to the cart.

```java
import org.testng.Assert;
// If the condition does not equal the expected condition, the test fails. Immediately.
  Assert.assertEquals(actual, expected);
   

```
If we were to use testng.Assert package, the test would fail since It will not wait until the cart has finished loading,  
thus an extra wait statement needs to be added. If you have 100 test cases, that means 100 extra wait statements.  
Also even if the wait statement is added, the assert will only happen once. 
- The desired outcome is to assert a condition and dynamically checking if it satisfies the expected outcome. 




```java
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
// FluentWaits "until" method will dynamically check every 500 miliseconds if the function within it returns true.
// If the function returns false, the until method will retry until the given time out expires.
// Thus provides us with our desired outcome.
 wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, "Hello world"));

// Issue 1: With this approach, no soft assertions are available.
// Issue 2: Not a favourable syntax.
```

### Expect class was therefor created.
- To solve softAssertions.
- Make the syntax easier to read.

#### How it works:
Expect class extends [WebElementBehaviour]() class for its method to reduce duplicate code.

Assertions can be made with both data types "By" & "WebElement".  
OBS!!!
 - Whenever possible, use "By" instead of "WebElement". When WebElement is used, we will point to the same WebElement during the retries. If it's caught with [StaleElementReferenceException](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/StaleElementReferenceException.html) and we point to the same stale element, the test will fail due to the stale element exception.
 - One has to send out a new findElement request to get rid of the stale element, which is done only with data type By. Sometimes we convert WebElement back to a By locator, but for looping through a List of WebElements, sending out a new driver.findElement screws the index.

The below code snippets are simplified version, [see the exact code here](https://github.com/oscargforce/Selenium-Java-Framework/blob/main/src/test/java/web_element_behaviour/WebElementBehaviourBase.java)
````java
// src/java/test/selenium_tests
@Test
public void exampleTest() {
    goToWebSite("/");
    // Set the locator
    By headerTitle = By.cssSelector("#title");
    // Step 1: "element" acts as a setter.
    expect.element(headerTitle).toHaveText("Selenium");
}

// src/java/test/web_element_behaviour/Expect
public class Expect extends WebElementBehaviourBase {
    private By locator;
    private WebElement element;
}
    public Expect(WebDriver driver){
        super(driver);
    }
    // Sep 2: The element methods sets the global locator variable.
    public Expect element(By locator) {
        this.locator = locator;
        return this; // chain the method in order to be able to add assertions.
    }
    // You can also use WebElement instead of By.
    public Expect element(WebElement element) {
        this.element = element;
        return this;
    }
    
    // Step 3: Do the assertion.
    public void toHaveText(String expectedText) throws AssertionError {
        if (element != null) {
            locator = this.convertWebElementToByLocator(element);
            toHaveTextWebElement(expectedText);
            return;
        }
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try { 
            // assertionWait -> initialized in WebElementBehaviourBase === timeout for retrying which is set in the config.properties file.
            assertionWait.until(driver -> driver.findElement(locator).getText().equals(expectedText));
            // If the assertion is successful, set the variables back to null, so it won't break our if statements, for the next set of assertions.
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            // If failed and timeout has expired, print a pretty error message.
            String actualText = driver.findElement(locator).getText();
            throw new AssertionError(customErrorMessage(locator, actualText, expectedText, "text content") + e);
        }
    }

````
### Soft Assertions

```java
    // src/java/test/selenium_tests
@Test
public void exampleTest() {
        goToWebSite("/");
        By headerTitle = By.cssSelector("#title");
        // method softAssertToHaveText returns the softAssert instance;
        SoftAssert softAssert = expect.element(headerTitle).softAssertToHaveText("Selenium");
        // Continue with test, and when test is done, softAssert.all() to throw any caught errors.
        softAssert.all();
        }

// src/java/test/web_element_behaviour/Expect
public class Expect extends WebElementBehaviourBase {
    private By locator;
    private WebElement element;
    // Step 1: Create a local variable and create the instance in the constructor.
    private SoftAssert softAssert;
    
    public Expect(WebDriver driver){
        super(driver);
        this.softAssert = new SoftAssert();
    }
    
    public SoftAssert softAssertToHaveText(String expectedText) {
        // if webElement is given in the setter "element" method, then convert it to locator.
        if (element != null) locator = this.convertWebElementToByLocator(element);
        waitForLocatorToBeVisible(locator);
        scrollIntoViewIfNeeded(locator);
        try {
            assertionWait.until(driver -> driver.findElement(locator).getText().equals(expectedText));
            element = null;
            locator = null;
        } catch (TimeoutException e) {
            String actualText = driver.findElement(locator).getText();
            // Step 2: If we enter this code block, then we know for sure that the actual is different from the expected value.
            // so we utilise testng's assert library and simply compare them once again, to create the caught error. Easy!
            // testng will do all the work for us.
            softAssert.assertEquals(actualText, expectedText, customErrorMessage(locator, actualText, expectedText, "text content"));
        }
        return softAssert;
     }
    }
```
