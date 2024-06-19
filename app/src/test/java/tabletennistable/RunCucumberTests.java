package tabletennistable;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "app\\src\\test\\resources\\Game.feature",
        glue = "app\\src\\test\\java\\tabletennistable\\GameSteps.java"
)
public class RunCucumberTests {

}
