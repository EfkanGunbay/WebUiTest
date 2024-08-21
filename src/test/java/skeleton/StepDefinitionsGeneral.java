package skeleton;


import com.starttech.base.DriverManager;
import com.starttech.utils.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static base.ProjectMethods.*;
import static com.starttech.utils.DataManager.getData;
import static com.starttech.utils.ElementManager.returnElement;
import static com.starttech.utils.Helper.sleepInSeconds;
import static com.starttech.utils.UrlManager.getTestUrl;

public class StepDefinitionsGeneral {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsGeneral.class);


    @And("load home page in {string}")
    public void homePage(String base) {
        pageLoad(getTestUrl());
        LOGGER.info("user is on home page " + base);
    }

    @And("click {string}")
    public void clickStep(String element) throws Exception {
        click(element);
    }

    @And("load {string} page in {string}")
    public void loadPageWithData(String page, String base) {
        pageLoad(getTestUrl() + getData("url." + page + ""));
        sleepInSeconds(10);
    }


    @And("wait {int} second")
    public void waitMinMin(int second) {
        sleepInSeconds(second);
    }

    @And("wait {int}")
    public void wait(int second) {
        sleepInSeconds(second);
    }


    @And("go to previous page")
    public void goToPreviousPage() {
        DriverManager.getDriver().navigate().back();
    }

    @And("switch tab")
    public void switchTabTest() {
        switchToTab();
    }

    @And("close tab")
    public void closeTabTest() {
        closeTab();
    }


    @And("close tabs other than the main tab")
    public void closeTabsOtherThanTheMainTab() {
        closeTabOtherThanTheMainTab();

    }

    @And("i see {string} element")
    public void iSeeElementInPage(String element) throws Exception {
        Assert.assertTrue(elementVisibiltyWithSize(element));
    }

    @And("enter {string} text in {string}")
    public void enterTextIn(String text, String element) throws Exception {
        enterText(element, text);
    }





    @And("click if exist {string} element")
    public void clickIfExistElement(String element) throws Exception {
        for (int i = 0; i < ConfigManager.maxClickTry(); i++) {
            if (elementVisibiltyWithSize(element)) {
                click(element);
                return;
            } else {
                sleepInSeconds(1);
            }
        }
        LOGGER.info("Element not visible after waiting for " + ConfigManager.maxClickTry() + " seconds.");
    }

    @And("wait until see {string} element")
    public void waitUntilSeeElement(String element) {
        waitForElement(element, 15);
    }
}