package com.stellar.burgers.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class RegistrationPage {
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ========== Локаторы элементов ==========

    // Поля формы
    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameField;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    // Кнопки и ссылки
    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    // Сообщения об ошибках (ИСПРАВЛЕНО: был div, теперь p)
    @FindBy(xpath = "//p[contains(@class, 'input__error')]")
    private WebElement passwordErrorMessage;

    // Заголовок страницы
    @FindBy(xpath = "//h2[text()='Регистрация']")
    private WebElement registrationTitle;

    // ========== Основные методы ==========

    /**
     * Открыть страницу регистрации
     */
    public void open() {
        driver.get("https://stellarburgers.education-services.ru/register");
        waitForPageLoad();
    }

    /**
     * Заполнить поле "Имя"
     */
    public void setName(String name) {
        waitForElement(nameField).clear();
        nameField.sendKeys(name);
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
     * Зарегистрироваться (нажать кнопку "Зарегистрироваться")
     */
    public void clickRegisterButton() {
        waitForElement(registerButton).click();
    }

    /**
     * Полная регистрация пользователя
     * @param name Имя пользователя
     * @param email Email пользователя
     * @param password Пароль пользователя
     */
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    /**
     * Нажать ссылку "Войти" для перехода на страницу логина
     */
    public void clickLoginLink() {
        waitForElement(loginLink).click();
    }

    // ========== Проверки ошибок ==========

    /**
     * Проверить, отображается ли ошибка пароля
     * @return true если ошибка отображается
     */
    public boolean isPasswordErrorDisplayed() {
        try {
            return passwordErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получить текст ошибки пароля
     * @return текст ошибки или пустую строку если ошибки нет
     */
    public String getPasswordErrorText() {
        try {
            return passwordErrorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Проверить, что отображается заголовок "Регистрация"
     * @return true если страница регистрации открыта
     */
    public boolean isRegistrationPageOpen() {
        try {
            return registrationTitle.isDisplayed();
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
     * Ожидание загрузки страницы регистрации
     */
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(registrationTitle));
    }
}

