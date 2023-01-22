package utilities;


import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import utilities.Retries;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GlobalRetryAnnotater implements IAnnotationTransformer {

    /*
    To avoid adding @retry on all tests, we are using the headless.xml
    file to add the annotation globally for the whole suite.
     */
    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        annotation.setRetryAnalyzer(Retries.class);
    }
}
