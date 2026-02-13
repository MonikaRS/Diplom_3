package com.stellar.burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuppressWarnings("unused")
public class MainPage extends BasePage {
    private static final String URL = "https://stellarburgers.education-services.ru/";

    @FindBy(xpath = "//a[.//p[text()='Личный Кабинет']]")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//button[text()='Оформить заказ']")
    private WebElement orderButton;

    @FindBy(xpath = "//h1[text()='Соберите бургер']")
    private WebElement pageTitle;

    @FindBy(xpath = "//span[text()='Булки']/parent::div")
    private WebElement bunsSection;

    @FindBy(xpath = "//span[text()='Соусы']/parent::div")
    private WebElement saucesSection;

    @FindBy(xpath = "//span[text()='Начинки']/parent::div")
    private WebElement fillingsSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab_type_current')]//span[text()='Булки']")
    private WebElement activeBunsSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab_type_current')]//span[text()='Соусы']")
    private WebElement activeSaucesSection;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab_type_current')]//span[text()='Начинки']")
    private WebElement activeFillingsSection;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие главной страницы")
    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
    }

    @Override
    @Step("Ожидание загрузки главной страницы")
    public void waitForPageLoad() {
        waitForVisibility(pageTitle);
    }

    @Step("Клик по кнопке 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        // ⚠️ БАГ ФРОНТЕНДА #2: поле Email имеет name="name" вместо name="email"
        // ⚠️ БАГ ФРОНТЕНДА #3: поле Пароль имеет name="Пароль" (русские буквы!)
        // Баги заведены 13.02.2026 в BUGS.md
        // Используем обходные селекторы по label
        waitForClickable(personalAccountButton);
        personalAccountButton.click();
        
        // Ждем URL
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Ждем заголовок "Вход"
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            org.openqa.selenium.By.xpath("//h2[text()='Вход']")
        ));
        
        // Ждем поле email по label (обход бага #2)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            org.openqa.selenium.By.xpath("//label[text()='Email']/following-sibling::input")
        ));
    }

    @Step("Клик по разделу 'Булки'")
    public void clickBunsSection() {
        waitForClickable(bunsSection);
        bunsSection.click();
    }

    @Step("Клик по разделу 'Соусы'")
    public void clickSaucesSection() {
        waitForClickable(saucesSection);
        saucesSection.click();
    }

    @Step("Клик по разделу 'Начинки'")
    public void clickFillingsSection() {
        waitForClickable(fillingsSection);
        fillingsSection.click();
    }

    @Step("Проверка активности раздела 'Булки'")
    public boolean isBunsSectionActive() {
        try {
            waitForVisibility(activeBunsSection);
            return activeBunsSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка активности раздела 'Соусы'")
    public boolean isSaucesSectionActive() {
        try {
            waitForVisibility(activeSaucesSection);
            return activeSaucesSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка активности раздела 'Начинки'")
    public boolean isFillingsSectionActive() {
        try {
            waitForVisibility(activeFillingsSection);
            return activeFillingsSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка отображения кнопки 'Оформить заказ'")
    public boolean isOrderButtonDisplayed() {
        try {
            waitForVisibility(orderButton);
            return orderButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
