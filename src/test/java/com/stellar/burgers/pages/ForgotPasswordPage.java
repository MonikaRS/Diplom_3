package com.stellar.burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class ForgotPasswordPage extends BasePage {
    private static final String URL = "https://stellarburgers.education-services.ru/forgot-password";

    // ⚠️ БАГ ФРОНТЕНДА: поле Email имеет name="name" вместо name="email"
    // Используем обходной селектор по label
    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement restoreButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    @FindBy(xpath = "//h2[text()='Восстановление пароля']")
    private WebElement passwordRestoreTitle;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Ожидание загрузки страницы восстановления пароля")
    public void waitForPageLoad() {
        waitForVisibility(passwordRestoreTitle);
        waitForVisibility(emailField);
    }

    @Step("Открытие страницы восстановления пароля")
    public void open() {
        driver.get(URL);
        waitForPageLoad();
    }

    @Step("Ввод email: {email}")
    public void setEmail(String email) {
        waitForVisibility(emailField);
        emailField.sendKeys(email);
    }

    @Step("Клик по кнопке 'Восстановить'")
    public void clickRestoreButton() {
        waitForClickable(restoreButton);
        restoreButton.click();
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        waitForClickable(loginLink);
        loginLink.click();
    }

    @Step("Проверка открытия страницы восстановления пароля")
    public boolean isPageOpen() {
        try {
            waitForVisibility(passwordRestoreTitle);
            waitForVisibility(emailField);
            return passwordRestoreTitle.isDisplayed() && emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
