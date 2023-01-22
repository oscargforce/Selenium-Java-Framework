package config;

import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigLoader {
    private static ConfigLoader instance;
    private final Properties properties;
    private final String env;

    private ConfigLoader() throws FileNotFoundException {
        env = System.getProperty("env", String.valueOf(EnvironmentTypes.STAGING));

        switch (EnvironmentTypes.valueOf(env)) {
            case DEVELOP -> properties = PropertiesLoader.loadFile("src/test/java/config/config-develop.properties");
            case STAGING -> properties = PropertiesLoader.loadFile("src/test/java/config/config-staging.properties");
            case PRODUCTION -> properties = PropertiesLoader.loadFile("src/test/java/config/config-production.properties");
            default -> throw new IllegalStateException("Invalid env type: " + env);
        }
    }

    private String customErrorMessage(String propertyKey){
        return String.format("property %s is not specified in the " + env + " config file", propertyKey);
    }
    public static ConfigLoader getInstance() throws FileNotFoundException {
        if (instance == null) instance = new ConfigLoader();
        return instance;
    }

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
    public int getRetries(){
        String property = properties.getProperty("Retries");
        if (property != null) return Integer.parseInt(property);
        else throw new RuntimeException(this.customErrorMessage("Retries"));
    }

    public int getPageLoadTimeOut(){
        String property = properties.getProperty("PageLoadTimeOut");
        if (property != null) return Integer.parseInt(property);
        else throw new RuntimeException(this.customErrorMessage("PageLoadTimeOut"));
    }

    public int getAssertionTimeOut(){
        String property = properties.getProperty("AssertionTimeOut");
        if (property != null) return Integer.parseInt(property);
        else throw new RuntimeException(this.customErrorMessage("AssertionTimeOut"));
    }
}
