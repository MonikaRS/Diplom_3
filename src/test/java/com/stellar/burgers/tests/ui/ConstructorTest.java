package com.stellar.burgers.tests.ui;

import com.stellar.burgers.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseUITest {
    private MainPage mainPage;

    @Before
    public void initPages() {
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Проверка перехода к разделу Булки в конструкторе")
    public void switchToBunsSection() {
        mainPage.open();
        mainPage.waitForPageLoad();
        
        mainPage.clickSaucesSection();
        mainPage.clickBunsSection();
        
        assertTrue("После клика на 'Булки' должен быть активен раздел Булок",
                mainPage.isBunsSectionActive());
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Проверка перехода к разделу Соусы в конструкторе")
    public void switchToSaucesSection() {
        mainPage.open();
        mainPage.waitForPageLoad();
        
        mainPage.clickSaucesSection();
        
        assertTrue("После клика на 'Соусы' должен быть активен раздел Соусов",
                mainPage.isSaucesSectionActive());
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Проверка перехода к разделу Начинки в конструкторе")
    public void switchToFillingsSection() {
        mainPage.open();
        mainPage.waitForPageLoad();
        
        mainPage.clickFillingsSection();
        
        assertTrue("После клика на 'Начинки' должен быть активен раздел Начинок",
                mainPage.isFillingsSectionActive());
    }
}
