package com.stellar.burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class LoginPage extends BasePage {
    private static final String URL = "https://stellarburgers.education-services.ru/login";

    // ⚠️ БАГ ФРОНТЕНДА #2: поле Email имеет name="name" вместо name="email"
    // Баг заведён 13.02.2026 в BUGS.md
    // Используем обходной селектор по label
    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    // ⚠️ БАГ ФРОНТЕНДА #3: поле Пароль имеет name="Пароль" (русские буквы!)
    // Баг заведён 13.02.2026 в BUGS.md
    // Используем обходной селектор по label
    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//h2[text()='Вход']")
    private WebElement loginTitle;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Ожидание загрузки страницы входа")
    public void waitForPageLoad() {
        waitForVisibility(loginTitle);
        waitForVisibility(emailField);
    }

    @Step("Открытие страницы входа")
    public void open() {
        driver.get(URL);
        waitForPageLoad();
    }

    @Step("Ввод email: {email}")
    public void setEmail(String email) {
        waitForVisibility(emailField);
        emailField.clear();
        emailField.sendKeys(email);
        System.out.println("   ✅ Email введен: " + email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        System.out.println("   ✅ Пароль введен");
    }

    @Step("Клик по кнопке 'Войти'")
    public void clickLoginButton() {
        waitForClickable(loginButton);
        loginButton.click();
        System.out.println("   ✅ Кнопка 'Войти' нажата");
    }

    @Step("Авторизация пользователя")
    public void login(String email, String password) {
        System.out.println("   🔐 Попытка входа: " + email);
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Клик по ссылке 'Зарегистрироваться'")
    public void clickRegisterLink() {
        waitForClickable(registerLink);
        registerLink.click();
    }

    @Step("Клик по ссылке 'Восстановить пароль'")
    public void clickForgotPasswordLink() {
        waitForClickable(forgotPasswordLink);
        forgotPasswordLink.click();
    }

    @Step("Проверка открытия страницы входа")
    public boolean isPageOpen() {
        try {
            waitForVisibility(loginTitle);
            waitForVisibility(emailField);
            return loginTitle.isDisplayed() && emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
