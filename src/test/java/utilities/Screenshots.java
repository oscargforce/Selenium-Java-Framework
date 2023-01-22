package utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Screenshots {
    private final ITestResult result;
    private final WebDriver driver;

    public Screenshots(WebDriver driver, ITestResult result) {
        this.driver = driver;
        this.result = result;
    }
    public void takeScreenshotOfTheFullPage() throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) this.driver;
        byte[] srcFile = screenshot.getScreenshotAs(OutputType.BYTES);
        String nameOfScreenShot = this.setScreenshotNameEqualToTestCaseName();
        Allure.addAttachment(nameOfScreenShot, new ByteArrayInputStream(srcFile));


    }

    private String setScreenshotNameEqualToTestCaseName() {
        String outputFileName = String.format(
                this.result.getTestClass().getRealClass().getSimpleName() + "-" +
                this.result.getMethod().getMethodName() + ".png");
        return outputFileName;
    }

}
