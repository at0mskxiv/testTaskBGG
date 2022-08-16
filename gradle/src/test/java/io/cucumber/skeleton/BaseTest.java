package io.cucumber.skeleton;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class BaseTest {

    public static ChromeDriver driver;

    @Before
    public static void init() {
        System.setProperty("webdriver.chrome.driver" ,  "/Users/ivankhvorostukhin/Projects/testTask/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @After
    public void shutDown() {
        driver.close();
    }
}
