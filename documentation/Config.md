# ConfigLoader

The idea was to have one central place to configure the test configuration.
A simple .properties file would thus suffice.

To make life easier I created a utility class to read the properties instance.
Since we only need one instance of the configuration file I also converted it to singleton.

### What you can configure:
 - BaseUrl for each environment [dev, staging, production]
 - WaitForElementTimeOut, how long we will retry our UI interactions on an element.
 - AssertionTimeOut, same as WaitForElementTimeOut, but only for the [Expect class](https://github.com/oscargforce/Selenium-Java-Framework/blob/main/src/test/java/web_element_behaviour/Expect.java)
 - PageLoadTimeOut, how long we should wait until a page is finished loading.
 - Retries, how many times we should retry a failed test.

### How the utility class works.
````java
public class ConfigLoader {
    private static ConfigLoader instance;
    private final Properties properties;
    private final String env;
    
    // The constructor sets the path to the config file based on the environment variable.
    private ConfigLoader() throws FileNotFoundException {
        env = System.getProperty("env", String.valueOf(EnvironmentTypes.STAGING));

        switch (EnvironmentTypes.valueOf(env)) {
            case DEVELOP -> properties = PropertiesLoader.loadFile("src/test/java/config/config-develop.properties");
            case STAGING -> properties = PropertiesLoader.loadFile("src/test/java/config/config-staging.properties");
            case PRODUCTION -> properties = PropertiesLoader.loadFile("src/test/java/config/config-production.properties");
            default -> throw new IllegalStateException("Invalid env type: " + env);
        }
    }
    
    public static ConfigLoader getInstance() throws FileNotFoundException {
        if (instance == null) instance = new ConfigLoader();
        return instance;
    }
     // Returns the property value of tWaitForElementTimeOut.
    public int getWaitForElementTimeOut(){
        String property = properties.getProperty("WaitForElementTimeOut");
        if (property != null) return Integer.parseInt(property);
        else throw new RuntimeException(this.customErrorMessage("WaitForElementTimeOut"));
    }

    public String getBaseUrl(){
        String property = properties.getProperty("BaseUrl");
        if (property != null) return property;
        else throw new RuntimeException(this.customErrorMessage("WaitForElementTimeOut"));
    }
````