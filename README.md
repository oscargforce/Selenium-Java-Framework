# Selenium-Java-Maven Framework.

### Custom auto waits
 - Class WebElementBehaviour was created to minigate the flaky tests of selenium.
 - For each click several actions will be performed before throwing an error.
     - It will dynamically wait and check every 500 milliseconds by smartly using explicit wait.
     - It will then check if the element is in the viewport. If not then it will auto scroll into view.
     - Once in the viewport we will attempt to click on the element in with several attempts if any exceptions are thrown.
     - One config file to specify each environment waitFor timeouts / retries / attempts to click on a element.
     - Like cypress / playwright I baked something similar to web-first assertions.
 - Retries for failed test.
 - Supports running the same test in all browsers. This feature is achieved by using testng.xml file and the maven command line.

mvn clean test -Dbrowser=value
mvn clean test -Dsurefire.suiteXmlFiles=value,multiplexmls

mvn clean test -Dheadless=true -Dsurefire.suiteXmlFiles=testng.yaml

Steps left for me to do for the framework.

1. CI set up. 
2. Add dockerImg
3. Add videos to allure by docker img and webdriverManager.
4. Enable headless mode feature

1. Inputs auto wait. Done
3. Add better logs to allure reporter. Done.
4. Think of folder structure.  Done.
5. In before or teardown add clear cookies / local storage in the browser, for clean tests. Done
