
package com.stellar.burgers.tests.ui;

import com.stellar.burgers.config.WebDriverFactory;
import com.stellar.burgers.pages.LoginPage;
import com.stellar.burgers.pages.MainPage;
import com.stellar.burgers.pages.RegistrationPage;
import com.stellar.burgers.pages.ForgotPasswordPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class UserLoginUITests {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private ForgotPasswordPage forgotPasswordPage;

    @Parameterized.Parameter
    public String browserName;

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> getBrowser() {
        return Arrays.asList(new Object[][] {
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver(browserName);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
    }

    @Test
    public void loginFromMainPageButton() {
        System.out.println("=== Тест: loginFromMainPageButton ===");

        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickLoginAccountButton();
        loginPage.waitForPageLoad();

        loginPage.setEmail("sssss@mail.ru");
        loginPage.setPassword("1233456");
        loginPage.clickLoginButton();

        mainPage.waitForPageLoad();

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonDisplayed());

        System.out.println("Тест успешно завершён");
    }

    @Test
    public void loginFromPersonalAccountButton() {
        System.out.println("=== Тест: loginFromPersonalAccountButton ===");

        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickPersonalAccountButton();
        loginPage.waitForPageLoad();

        loginPage.setEmail("sssss@mail.ru");
        loginPage.setPassword("1233456");
        loginPage.clickLoginButton();

        mainPage.waitForPageLoad();

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonDisplayed());

        System.out.println("Тест успешно завершён");
    }

    @Test
    public void loginFromRegistrationForm() {
        System.out.println("=== Тест: loginFromRegistrationForm ===");

        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickLoginAccountButton();
        loginPage.waitForPageLoad();

        loginPage.clickRegisterLink();
        registrationPage.waitForPageLoad();

        registrationPage.clickLoginLink();

        // Ждем загрузки страницы входа
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("login"));

        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();

        loginPage.setEmail("sssss@mail.ru");
        loginPage.setPassword("1233456");
        loginPage.clickLoginButton();

        mainPage.waitForPageLoad();

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonDisplayed());

        System.out.println("Тест успешно завершён");
    }

    @Test
    public void loginFromForgotPasswordForm() {
        System.out.println("=== Тест: loginFromForgotPasswordForm ===");

        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickLoginAccountButton();
        loginPage.waitForPageLoad();

        loginPage.clickForgotPasswordLink();
        forgotPasswordPage.waitForPageLoad();

        forgotPasswordPage.clickLoginLink();

        // Ждем загрузки страницы входа
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("login"));

        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();

        loginPage.setEmail("sssss@mail.ru");
        loginPage.setPassword("1233456");
        loginPage.clickLoginButton();

        mainPage.waitForPageLoad();

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonDisplayed());

        System.out.println("Тест успешно завершён");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}