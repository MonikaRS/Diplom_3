package com.stellar.burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class RegistrationPage extends BasePage {
    private static final String URL = "https://stellarburgers.education-services.ru/register";

    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameField;

    // ⚠️ БАГ ФРОНТЕНДА #1: поле Email имеет name="name" вместо name="email"
    // Баг заведён 13.02.2026 в BUGS.md
    // Используем обходной селектор по label
    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement invalidPasswordError;

    @FindBy(xpath = "//h2[text()='Регистрация']")
    private WebElement registrationTitle;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Ожидание загрузки страницы регистрации")
    public void waitForPageLoad() {
        waitForVisibility(registrationTitle);
        waitForVisibility(emailField);
    }

    @Step("Открытие страницы регистрации")
    public void open() {
        driver.get(URL);
        waitForPageLoad();
    }

    @Step("Ввод имени: {name}")
    public void setName(String name) {
        waitForVisibility(nameField);
        nameField.sendKeys(name);
    }

    @Step("Ввод email: {email}")
    public void setEmail(String email) {
        waitForVisibility(emailField);
        emailField.sendKeys(email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        waitForVisibility(passwordField);
        passwordField.sendKeys(password);
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public void clickRegisterButton() {
        waitForClickable(registerButton);
        registerButton.click();
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        waitForClickable(loginLink);
        loginLink.click();
    }

    @Step("Проверка отображения ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            waitForVisibility(invalidPasswordError);
            return invalidPasswordError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка открытия страницы регистрации")
    public boolean isPageOpen() {
        try {
            waitForVisibility(registrationTitle);
            waitForVisibility(emailField);
            return registrationTitle.isDisplayed() && emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Регистрация пользователя")
    public void register(String name, String email, String password) {
        open();
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }
}
