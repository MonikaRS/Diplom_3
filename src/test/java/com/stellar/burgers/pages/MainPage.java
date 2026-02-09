package com.stellar.burgers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ========== Локаторы ==========

    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginAccountButton;

    @FindBy(xpath = "//p[text()='Личный Кабинет']/..")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab__')]//span[text()='Булки']/..")
    private WebElement bunsSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab__')]//span[text()='Соусы']/..")
    private WebElement saucesSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/..")
    private WebElement fillingsSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab_type_current__')]")
    private WebElement activeTabDiv;

    @FindBy(xpath = "//h1[text()='Соберите бургер']")
    private WebElement constructorTitle;

    @FindBy(xpath = "//button[text()='Оформить заказ']")
    private WebElement orderButton;

    // ========== Основные методы ==========

    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
        waitForPageLoad();
    }

    public void clickLoginAccountButton() {
        waitForElement(loginAccountButton).click();
    }

    public void clickPersonalAccountButton() {
        waitForElement(personalAccountButton).click();
    }

    public void clickBunsSection() {
        waitForElement(bunsSection).click();
    }

    public void clickSaucesSection() {
        waitForElement(saucesSection).click();
    }

    public void clickFillingsSection() {
        waitForElement(fillingsSection).click();
    }

    public boolean isOrderButtonDisplayed() {
        try {
            return orderButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========== Проверки активности разделов ==========

    public boolean isBunsSectionActive() {
        try {
            return waitForElement(activeTabDiv)
                    .findElement(By.tagName("span"))
                    .getText()
                    .trim()
                    .equals("Булки");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSaucesSectionActive() {
        try {
            return waitForElement(activeTabDiv)
                    .findElement(By.tagName("span"))
                    .getText()
                    .trim()
                    .equals("Соусы");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFillingsSectionActive() {
        try {
            return waitForElement(activeTabDiv)
                    .findElement(By.tagName("span"))
                    .getText()
                    .trim()
                    .equals("Начинки");
        } catch (Exception e) {
            return false;
        }
    }

    // ========== Вспомогательные методы ==========

    private WebElement waitForElement(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(constructorTitle));
    }
}