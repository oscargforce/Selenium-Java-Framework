package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import config.ConfigLoader;

import java.io.FileNotFoundException;

public class Retries implements IRetryAnalyzer {
    int maxRetryCount = ConfigLoader.getInstance().getRetries();
    int retryCount = 0;

    public Retries() throws FileNotFoundException {
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}
