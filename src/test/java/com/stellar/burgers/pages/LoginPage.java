package com.stellar.burgers.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ========== Локаторы элементов ==========

    // Поля формы (ИСПРАВЛЕНО)
    @FindBy(xpath = "//input[@type='text' and @name='name']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    // Кнопки и ссылки
    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement forgotPasswordLink;

    // Заголовок страницы
    @FindBy(xpath = "//h2[text()='Вход']")
    private WebElement loginTitle;

    // ========== Основные методы ==========

    /**
     * Открыть страницу входа
     */
    public void open() {
        driver.get("https://stellarburgers.education-services.ru/login");
        waitForPageLoad();
    }

    /**
     * Заполнить поле "Email"
     */
    public void setEmail(String email) {
        waitForElement(emailField).clear();
        emailField.sendKeys(email);
    }

    /**
     * Заполнить поле "Пароль"
     */
    public void setPassword(String password) {
        waitForElement(passwordField).clear();
        passwordField.sendKeys(password);
    }

    /**
     * Нажать кнопку "Войти"
     */
    public void clickLoginButton() {
        waitForElement(loginButton).click();
    }

    /**
     * Нажать ссылку "Зарегистрироваться"
     */
    public void clickRegisterLink() {
        waitForElement(registerLink).click();
    }

    /**
     * Нажать ссылку "Восстановить пароль"
     */
    public void clickForgotPasswordLink() {
        waitForElement(forgotPasswordLink).click();
    }

    /**
     * Полный вход пользователя
     * @param email Email пользователя
     * @param password Пароль пользователя
     */
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    // ========== Проверки состояния ==========

    /**
     * Проверить, что страница входа открыта
     * @return true если страница входа открыта
     */
    public boolean isLoginPageOpen() {
        try {
            return loginTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========== Вспомогательные методы ==========

    /**
     * Ожидание элемента
     */
    private WebElement waitForElement(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Ожидание загрузки страницы входа
     */
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(loginTitle));
    }
}
