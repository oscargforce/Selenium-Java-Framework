# Selenium-Java-Maven Framework.


### Custom auto waits
**Homebrewed auto waits** were implemented to ensure **stable** and **reliable** tests.  
Before every action following steps below are taken before executing the command.  


| AutoWaits              | [scrollIntoViewIfNeeded]() | [Attached](https://developer.mozilla.org/en-US/docs/Web/API/Node/isConnected) |    [Visible](https://www.selenium.dev/documentation/webdriver/elements/information/#is-displayed)     | [Enabled](https://www.selenium.dev/documentation/webdriver/elements/information/#is-enabled) | [Focus](https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/focus) | 
|:-----------------------|----------------------------|:-----------------------------------------------------------------------------:|:------------------:|:--------------------------------------------------------------------------------------------:|-----------|
| Clicks                 |      :ballot_box_with_check:	 |                            :ballot_box_with_check:                            | :ballot_box_with_check: |                                   :ballot_box_with_check:                                    |           |
| sendKeys (inputs)      |      :ballot_box_with_check:	 |                            :ballot_box_with_check:                            | :ballot_box_with_check: |                                              -                                               |     :ballot_box_with_check:       |
| hover                  |      :ballot_box_with_check:	 |                            :ballot_box_with_check:                            | :ballot_box_with_check: |                                   :ballot_box_with_check:                                    |           |
| getText                |      :ballot_box_with_check:	 |                                       -                                       | :ballot_box_with_check: |                                              -                                               |           |

### Dynamically retrying every action if failed.
If a click fails, the same command will retry every 500 milliseconds until the condition either returns true " the click was successful" or until the given time out specified in the config file expires.

[StaleElementReferenceException](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/StaleElementReferenceException.html) is countered by sending out a new driver.findElement() during each retry to get rid of the stale element.

[ElementClickInterceptedException](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/ElementClickInterceptedException.html) is also a thing of the past since we will dynamically try to click on an element until the given time-out expires.

### Web-first assertions.
Assertions are automatically retried until the necessary conditions are met or fails after the given timeout specified in the config file.  
No more worrying about proper wait statements with selenium.

#### Supports retries for failed tests, parallel execution, multiple parallel browser execution.

### Browser options
Default is set to chrome, but it can be overwritten in the command line by relacing value with  
firefox | edge | chrome
````shell
 mvn clean test -Dbrowser=value
````
Browser can also be set as a parameter in the testng.yaml file.
```yaml
  # now runs the same test class for all different browsers
  - name: Home page tests
    parameters: { browser: chrome }
    classes:
      - selenium_tests.HomePageTests

  - name: Store page tests
    parameters: { browser: chrome }
    classes:
      - selenium_tests.StorePageTests

  - name: e2e tests
    parameters: { browser: firefox }
    classes:
      - selenium_tests.e2e
```
runs in headless mode for all browsers if environment variable is set to true
````shell
mvn clean test -Dheadless=true
````
Is the current script running in the [pipelines](https://github.com/oscargforce/Selenium-Java-Framework/actions)
````shell
mvn clean test -Dheadless=true -Dsurefire.suiteXmlFiles=testng.yaml
````

## Documentation
 -

Steps left for me to do for the framework.

1. CI set up. 
2. Add dockerImg
3. Add videos to allure by docker img and webdriverManager.
4. Enable headless mode feature
