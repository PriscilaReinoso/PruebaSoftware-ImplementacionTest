package prueba.soft.Selenium_Cucumber.test;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/prueba/soft/Selenium_Cucumber/test/resources/login.feature",
        glue = "prueba.soft.Selenium_Cucumber.test",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class CucumberTestRunner {
}
