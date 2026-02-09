package com.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.MyInfoPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        loginPage = new LoginPage(driver);
        
    }

    @Test
    public void invalidLoginShouldShowError() {
        loginPage.login("wrongUser", "wrongPass");
        String msg = loginPage.getInvalidCredentialsToastMessage();
        Assert.assertTrue(msg.toLowerCase().contains("invalid"));
    }

    @Test
    public void validUserInvalidPasswordShouldFail() {
        loginPage.login("Admin", "wrongPassword");
        String msg = loginPage.getInvalidCredentialsToastMessage();
        Assert.assertTrue(msg.toLowerCase().contains("invalid"));
    }

    @Test
    public void blankLoginShouldShowRequiredValidation() {
        loginPage.clickLoginOnly();
        Assert.assertTrue(loginPage.isRequiredValidationShown());
    }

    @Test
    public void validLoginShouldGoToDashboard() {
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(loginPage.isDashboardVisible());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
