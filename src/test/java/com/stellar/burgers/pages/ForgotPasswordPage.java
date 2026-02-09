package com.stellar.burgers.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class ForgotPasswordPage {
    private final WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement restoreButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    @FindBy(xpath = "//h2[text()='Восстановление пароля']")
    private WebElement passwordRestoreTitle;

    public void open() {
        driver.get("https://stellarburgers.education-services.ru/forgot-password");
    }

    // ДОБАВИТЬ ЭТОТ МЕТОД
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(passwordRestoreTitle));
    }

    public void setEmail(String email) {
        waitForElement(emailField).clear();
        waitForElement(emailField).sendKeys(email);
    }

    public void clickRestoreButton() {
        waitForElement(restoreButton).click();
    }

    public void clickLoginLink() {
        waitForElement(loginLink).click();
    }

    public boolean isPasswordRestorePageOpen() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOf(passwordRestoreTitle));
            return passwordRestoreTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement waitForElement(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(element));
    }
}