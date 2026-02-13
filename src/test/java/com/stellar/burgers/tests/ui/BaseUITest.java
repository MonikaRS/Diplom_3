package com.stellar.burgers.tests.ui;

import com.stellar.burgers.api.UserApiClient;
import com.stellar.burgers.config.WebDriverFactory;
import com.stellar.burgers.models.User;
import com.stellar.burgers.pages.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseUITest {
    protected WebDriver driver;
    protected UserApiClient userApiClient;
    protected User testUser;
    protected String accessToken;
    protected String browserName;
    
    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected RegistrationPage registrationPage;
    protected ForgotPasswordPage forgotPasswordPage;

    @Before
    @Step("Подготовка тестовой среды")
    public void setUp() {
        browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);
        userApiClient = new UserApiClient();
        
        // ✅ Добавляем информацию о браузере в Allure отчёт
        String browserInfo = "Браузер: " + browserName;
        Allure.addAttachment("Информация о браузере", browserInfo);
        
        // Добавляем как отдельный файл
        Allure.addAttachment("Браузер", "text/plain", 
            new ByteArrayInputStream(browserInfo.getBytes(StandardCharsets.UTF_8)), ".txt");
        
        System.out.println("🌐 ТЕСТ ЗАПУЩЕН В БРАУЗЕРЕ: " + browserName.toUpperCase());
        
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
    }

    @Step("Создание тестового пользователя через API")
    protected void createTestUser() {
        testUser = User.getRandomUser();
        
        Response response = userApiClient.createUser(testUser);
        
        if (response.statusCode() == 200) {
            accessToken = response.path("accessToken");
            System.out.println("✅ Тестовый пользователь создан в API: " + testUser.getEmail());
        } else {
            System.out.println("❌ Ошибка создания пользователя: " + response.statusCode());
        }
    }

    @After
    @Step("Очистка тестовой среды")
    public void tearDown() {
        if (accessToken != null) {
            userApiClient.deleteUser(accessToken);
            System.out.println("✅ Тестовый пользователь удален");
        }
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver закрыт");
        }
    }
}
