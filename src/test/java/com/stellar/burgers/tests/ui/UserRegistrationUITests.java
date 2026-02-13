package com.stellar.burgers.tests.ui;

import com.stellar.burgers.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UserRegistrationUITests extends BaseUITest {

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации с валидными данными")
    public void successfulRegistration() {
        // ⚠️ БАГ ФРОНТЕНДА #1: поле Email имеет name="name" вместо name="email"
        // Баг заведён 13.02.2026 в BUGS.md
        // Используем обходной селектор //label[text()='Email']/following-sibling::input
        User newUser = User.getRandomUser();
        
        registrationPage.register(newUser.getName(), newUser.getEmail(), newUser.getPassword());
        
        assertTrue("После успешной регистрации должна открыться страница входа", 
                   loginPage.isPageOpen());
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля")
    @Description("Проверка ошибки при регистрации с паролем меньше 6 символов")
    public void registrationWithShortPassword() {
        // ⚠️ БАГ ФРОНТЕНДА #1: поле Email имеет name="name" вместо name="email"
        // Баг заведён 13.02.2026 в BUGS.md
        // Используем обходной селектор //label[text()='Email']/following-sibling::input
        String shortPassword = "12345";
        User newUser = User.getRandomUserWithPassword(shortPassword);
        
        registrationPage.register(newUser.getName(), newUser.getEmail(), newUser.getPassword());
        
        assertTrue("Должно появиться сообщение об ошибке для короткого пароля", 
                   registrationPage.isPasswordErrorDisplayed());
    }
}
