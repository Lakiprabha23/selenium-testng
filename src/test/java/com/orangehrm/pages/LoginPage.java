package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // URL
    private final String loginUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    // ---- Locators (stable) ----
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");

    // Error toast (invalid credentials)
    private final By errorToastContainer = By.cssSelector(".oxd-alert.oxd-alert--error");
    private final By errorToastText      = By.cssSelector(".oxd-alert.oxd-alert--error .oxd-alert-content-text");

    // Field validation message (blank inputs -> "Required")
    private final By requiredFieldMessage = By.xpath(
            "//span[contains(@class,'oxd-input-field-error-message') and normalize-space()='Required']"
    );

    // Dashboard header (used to confirm successful login)
    private final By dashboardHeader = By.xpath("//h6[normalize-space()='Dashboard']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get(loginUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
    }

    public void enterUsername(String username) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        el.clear();
        el.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        el.clear();
        el.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String u, String p) {
        enterUsername(u);
        enterPassword(p);
        clickLogin();
    }

    public void clickLoginOnly() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    // ---- Assertions helpers ----

    // For invalid credentials scenario
    public String getInvalidCredentialsToastMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastContainer));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastText)).getText();
    }

    // For blank login scenario ("Required" messages)
    public boolean isRequiredValidationShown() {
        // wait a moment for validations to appear
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfAllElementsLocatedBy(requiredFieldMessage),
                ExpectedConditions.presenceOfElementLocated(errorToastContainer)
        ));
        List<WebElement> msgs = driver.findElements(requiredFieldMessage);
        return msgs != null && msgs.size() > 0;
    }

    // For valid login scenario
    public boolean isDashboardVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
