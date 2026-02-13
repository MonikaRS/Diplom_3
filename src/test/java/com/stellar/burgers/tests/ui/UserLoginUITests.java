package com.stellar.burgers.tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

public class UserLoginUITests extends BaseUITest {

    @Before
    public void createUser() {
        createTestUser();
    }

    @Test
    @DisplayName("Вход через Личный кабинет")
    @Description("Проверка входа через кнопку 'Личный кабинет' на главной странице")
    public void loginFromPersonalAccountButton() {
        // ⚠️ БАГ ФРОНТЕНДА #2 и #3
        mainPage.open();
        
        mainPage.clickPersonalAccountButton();
        loginPage.waitForPageLoad();
        
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        mainPage.waitForPageLoad();
        
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'", 
                   mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка входа через ссылку 'Войти' на странице регистрации")
    public void loginFromRegistrationForm() {
        registrationPage.open();
        
        registrationPage.clickLoginLink();
        loginPage.waitForPageLoad();
        
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        mainPage.waitForPageLoad();
        
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'", 
                   mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через восстановление пароля")
    @Description("Проверка входа через ссылку 'Войти' на странице восстановления пароля")
    public void loginFromForgotPasswordForm() {
        forgotPasswordPage.open();
        
        forgotPasswordPage.clickLoginLink();
        loginPage.waitForPageLoad();
        
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        mainPage.waitForPageLoad();
        
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'", 
                   mainPage.isOrderButtonDisplayed());
    }
}
