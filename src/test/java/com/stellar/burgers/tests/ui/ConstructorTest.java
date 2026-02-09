package com.stellar.burgers.tests.ui;

import com.stellar.burgers.config.WebDriverFactory;
import com.stellar.burgers.pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Parameterized.Parameter
    public String browserName;

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> getBrowser() {
        return Arrays.asList(new Object[][] {
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver(browserName);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @Test
    public void switchToBunsSection() {
        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickSaucesSection();
        wait(1000);

        mainPage.clickBunsSection();
        wait(1000);

        assertTrue("После клика на 'Булки' должен быть активен раздел Булок",
                mainPage.isBunsSectionActive());
    }

    @Test
    public void switchToSaucesSection() {
        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickSaucesSection();
        wait(1000);

        assertTrue("После клика на 'Соусы' должен быть активен раздел Соусов",
                mainPage.isSaucesSectionActive());
    }

    @Test
    public void switchToFillingsSection() {
        mainPage.open();
        mainPage.waitForPageLoad();

        mainPage.clickFillingsSection();
        wait(1000);

        assertTrue("После клика на 'Начинки' должен быть активен раздел Начинок",
                mainPage.isFillingsSectionActive());
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}