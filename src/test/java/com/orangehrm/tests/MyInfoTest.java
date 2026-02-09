package com.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.MyInfoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class MyInfoTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MyInfoPage myInfoPage;

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
        myInfoPage = new MyInfoPage(driver);
    }

    @Test
    public void verifyMyInfoPageDetails() {
        myInfoPage.openMyInfo();

        Assert.assertTrue(myInfoPage.isHeaderDisplayed(),
                "Personal Details header not visible");

        Assert.assertTrue(myInfoPage.isFirstNamePresent(),
                "First Name field missing");

        Assert.assertTrue(myInfoPage.isLastNamePresent(),
                "Last Name field missing");

        Assert.assertTrue(myInfoPage.isSaveButtonEnabled(),
                "Save button is disabled");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
