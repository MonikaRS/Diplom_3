package com.stellar.burgers.tests.ui;

import com.stellar.burgers.config.WebDriverFactory;
import com.stellar.burgers.pages.LoginPage;
import com.stellar.burgers.pages.MainPage;
import com.stellar.burgers.pages.RegistrationPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class UserRegistrationUITests {
    private WebDriver driver;
    private MainPage mainPage;
    private RegistrationPage registrationPage;

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
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    public void successfulRegistration() {
        String name = "TestUser_" + UUID.randomUUID().toString().substring(0, 8);
        String email = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String password = "ValidPass123";

        mainPage.open();
        mainPage.clickPersonalAccountButton();

        // Создаем LoginPage локально, так как он используется только в этом тесте
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        assertTrue("Страница регистрации должна открыться",
                registrationPage.isRegistrationPageOpen());

        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegisterButton();

        assertTrue("После успешной регистрации должен открыться вход",
                loginPage.isLoginPageOpen());
    }

    @Test
    public void registrationWithShortPassword() {
        String name = "TestUser_" + UUID.randomUUID().toString().substring(0, 8);
        String email = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String shortPassword = "12345";

        registrationPage.open();
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(shortPassword);
        registrationPage.clickRegisterButton();

        assertTrue("Должно появиться сообщение об ошибке для короткого пароля",
                registrationPage.isPasswordErrorDisplayed());
    }

    @Test
    public void registrationWithExistingEmail() {
        // Сначала создаем пользователя
        String name = "TestUser_" + UUID.randomUUID().toString().substring(0, 8);
        String email = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String password = "ValidPass123";

        registrationPage.open();
        registrationPage.register(name, email, password);

        // Проверяем, что регистрация прошла успешно
        LoginPage loginPage = new LoginPage(driver);
        assertTrue("После успешной регистрации должен открыться вход",
                loginPage.isLoginPageOpen());

        // Пытаемся зарегистрироваться с тем же email
        registrationPage.open();
        String newName = "AnotherUser_" + UUID.randomUUID().toString().substring(0, 8);
        registrationPage.setName(newName);
        registrationPage.setEmail(email); // Тот же email
        registrationPage.setPassword(password);
        registrationPage.clickRegisterButton();

        // В будущем можно добавить проверку на ошибку существующего email
        // если такая функциональность будет реализована в приложении
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}