package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyInfoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Left menu appears only after login
    private By leftMenu = By.cssSelector("aside.oxd-sidepanel");

    // My Info menu item (anchor) - more stable than span text
    private By myInfoMenu = By.cssSelector("a[href*='viewMyDetails']");

    // Page header: "Personal Details"
    private By personalDetailsHeader = By.xpath("//h6[normalize-space()='Personal Details']");

    // First Name / Last Name inputs
    // OrangeHRM has multiple inputs; these 2 are inside the Personal Details form
    private By firstNameInput = By.name("firstName");
    private By lastNameInput  = By.name("lastName");

    // Save button (first visible Save on the page)
    private By saveButton = By.xpath("(//button[@type='submit' and normalize-space()='Save'])[1]");

    public MyInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openMyInfo() {
        // wait login completed
        wait.until(ExpectedConditions.visibilityOfElementLocated(leftMenu));

        // click My Info
        wait.until(ExpectedConditions.elementToBeClickable(myInfoMenu)).click();

        // wait page loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader));
    }

    public boolean isHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader)).isDisplayed();
    }

    public boolean isFirstNamePresent() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        return el.isDisplayed();
    }

    public boolean isLastNamePresent() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        return el.isDisplayed();
    }

    public boolean isSaveButtonEnabled() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        return btn.isEnabled();
    }
}
