package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"stepdefinitions", "hooks"},
        plugin = {"html:target/test-results.html"}
//        tags = "@Test03"

)
public class TestRunner {
}
