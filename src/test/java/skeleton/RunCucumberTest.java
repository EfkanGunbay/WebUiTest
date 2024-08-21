package skeleton;


import com.starttech.base.DriverManager;
import com.starttech.utils.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static com.starttech.utils.Helper.deleteOldTestReportFiles;
import static org.openqa.selenium.OutputType.BYTES;


@CucumberOptions(
        publish = false,
        features = "src/test/resources/io.cucumber.features",
        glue = "io.cucumber.skeleton",
        monochrome = true,
        tags = "Reg",
        plugin = {"summary", "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "rerun:target/failedrerun.txt"}
)

public class RunCucumberTest extends AbstractTestNGCucumberTests {


    @Before
    public void setUp(){
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigManager.getConfigProperty("url"));
        DriverManager.getDriver().manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS);
        DriverManager.getDriver().manage().deleteAllCookies();

    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){

            final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","screenshot");
        }

        DriverManager.closeDriver();

    }
}
