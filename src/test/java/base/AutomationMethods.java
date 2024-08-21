package base;


import com.starttech.base.DriverManager;
import com.starttech.utils.ConfigManager;
import com.starttech.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.starttech.utils.ElementManager.returnElement;
import static com.starttech.utils.Helper.sleepInSeconds;


public class AutomationMethods {


    private static final Logger LOGGER = LogManager.getLogger(AutomationMethods.class);



    public static void enterText(String element, String textToEnter) throws Exception {
        WebElement webElement = findObject(returnElement(element));
        webElement.clear();
        webElement.sendKeys(textToEnter);
    }

    public static String getText(String element) throws Exception {
        return findObject(returnElement(element)).getText().trim();
    }

    public static WebElement findObject(By by) throws Exception {
        FluentWait<WebDriver> wait = getFluentWait();
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }


    public static void click(String element) throws Exception {
        waitForIntervalsAndClick(returnElement(element), 1, ConfigManager.getExplicitWaitTime());
    }
    public static void clickPath(By element) throws Exception {
        waitForIntervalsAndClick(element, 1, ConfigManager.getExplicitWaitTime());
    }


    public static void waitForIntervalsAndClick(By by, int interval, int maxWait) throws Exception {
        boolean elementExists = false;
        int counter = 0;
        while (counter <= maxWait) {
            try {
                DriverManager.getDriver().findElement(by).click();
                elementExists = true;
                counter = maxWait + 1;
            } catch (Exception e) {
                LOGGER.info("Web element [{}] | Click attempt : [{}]", by.toString(), counter);
                sleepInSeconds(interval);
                counter++;
                elementExists = false;
            }
        }
        if (!elementExists) {
            DriverManager.getDriver().findElement(by).click();
        }
    }


    public static FluentWait<WebDriver> getFluentWait(int intervalInSeconds, int maxWaitTimeInSeconds) {
        return new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(intervalInSeconds))
                .pollingEvery(Duration.ofSeconds(maxWaitTimeInSeconds))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    public static FluentWait<WebDriver> getFluentWait() {
        return getFluentWait(1, ConfigManager.getExplicitWaitTime());
    }

    public static void switchToTab() {
        // açık olan diğer taba geçer.

        String main = DriverManager.getDriver().getWindowHandle();
        for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
            if (!main.contentEquals(windowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                LOGGER.info("Tab switch");
                break;
            }
        }
    }




    public static boolean elementVisibiltyWithSize(String element) throws Exception {
        // by ile gönderilen elementi, tüm sayfa kodu içinde arar. olup olmadığını döndürür
        By by = returnElement(element);
        return DriverManager.getDriver().findElements(by).size() > 0;
    }



    public static void closeTab() {

        DriverManager.getDriver().close();
    }


    public static void closeTabOtherThanTheMainTab() {
        // Bu yöntem, driver nesnesi üzerinden yönetilen tüm sekmelerin tanımlarını alır ve ana sekme dışındaki tüm sekmeleri dolaşır.
        // Dolaşırken, her bir sekme için ana sekmeden farklı bir ID kontrolü yapar.
        // Ana sekmeden farklı bir ID'ye sahip olan sekmeler kapatılır ve sonunda ana sekme tekrar seçil

        String mainTab = DriverManager.getDriver().getWindowHandle();
        Set<String> allTabs = DriverManager.getDriver().getWindowHandles();

        for (String tab : allTabs) {
            if (!tab.equals(mainTab)) {
                DriverManager.getDriver().switchTo().window(tab);
                DriverManager.getDriver().close();
            }
        }

        DriverManager.getDriver().switchTo().window(mainTab);

    }

    public static boolean waitForElement(String element, int timeoutInSeconds) {
        int attempts = 0;
        while (attempts < timeoutInSeconds) {
            try {
                if (elementVisibiltyWithSize(element)) {
                    return true;
                }
            } catch (NoSuchElementException e) {
                sleepInSeconds(1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            attempts++;
        }
        return false;
    }
}
