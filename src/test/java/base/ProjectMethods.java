package base;


import com.starttech.base.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.starttech.utils.Helper.sleepInSeconds;


public class ProjectMethods extends AutomationMethods {

    private static final Logger LOGGER = LogManager.getLogger(ProjectMethods.class);


    public static void pageLoad(String url) {
        DriverManager.getDriver().get(url);
    }

}