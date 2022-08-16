package io.cucumber.skeleton;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.cucumber.skeleton.BaseTest.driver;

public class BasePage {

    WebDriverWait wait;

    @FindBy(className = "menu-logo-symbol")
    private WebElement logo;

//    @FindBy(className = "menu-bars")
//    private WebElement sideMenu;

    @FindBy(xpath = "//h2[@class='module-header__title'][contains(text(),'The Hotness')]/a[@class='arrow-link tw-text-xs tw-uppercase']")
    private WebElement seeAllHotness;

    @FindBy(xpath = "//h2[contains(text(),' The Hotness ')]")
    private WebElement hotnessHeader;

    @FindBy(xpath = "//li[@class='numbered-game-list__item tw-flex tw-items-center'][1]//a[@class='stretched-link link-text-color']")
    private WebElement topHotnessGame;

    @FindBy(xpath = "//span[@class='ng-isolate-scope'][@item-poll-button='languagedependence']/span")
    private WebElement languageDependence;

    public BasePage() {
        PageFactory.initElements(driver, this);
//        return this;
    }

    static ExpectedCondition<Boolean> pageLoaded(String pagePartialUrl) {
        JavascriptExecutor js = driver;

        return driver -> js.executeScript("return window.location.href").toString().contains(pagePartialUrl) &&
        js.executeScript("return document.readyState").equals("complete");
    }

    public static void waitForPageLoaded(String pagePartialUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(pageLoaded(pagePartialUrl));
    }

    public Boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

//    public void clickSideMenuButton() {
//        sideMenu.click();
//    }

    public void clickSeeAllHotness() {
        seeAllHotness.click();
    }

    public Boolean isHotnessHeaderDisplayed() {
        waitForPageLoaded("/hotness");
        return hotnessHeader.isDisplayed();
    }

    public String getTopHotnessGameId() {
        String id = topHotnessGame.getAttribute("href").replaceAll("https://boardgamegeek.com/boardgame/(\\d+)/[\\s\\S]+", "$1");
        return id;
    }

    public void clickOnHotnessGame() {
        topHotnessGame.click();
    }

    public String getLanguageDependence() {
        return languageDependence.getText();
    }
}