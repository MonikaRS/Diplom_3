package com.stellar.burgers.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.time.Duration;

public class WebDriverFactory {
    
    public static WebDriver createDriver(String browserName) {
        WebDriver driver;
        
        String projectDir = System.getProperty("user.dir");
        String driversDir = projectDir + "/drivers/";

        switch (browserName.toLowerCase()) {
            case "yandex":
                driver = createYandexDriver(driversDir);
                break;
            case "chrome":
            default:
                driver = createChromeDriver(driversDir);
                break;
        }

        // Явные ожидания для всего проекта
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        System.out.println("✅ WebDriver создан. Браузер: " + browserName);
        return driver;
    }

    public static WebDriver createDriver() {
        // Браузер берется из системной переменной, по умолчанию yandex
        String browserName = System.getProperty("browser", "yandex");
        return createDriver(browserName);
    }

    private static WebDriver createChromeDriver(String driversDir) {
        String chromeDriverPath = driversDir + "chromedriver";
        File chromeDriverFile = new File(chromeDriverPath);
        
        if (!chromeDriverFile.exists()) {
            throw new RuntimeException("❌ ChromeDriver не найден: " + chromeDriverPath);
        }
        
        // Устанавливаем права на выполнение
        if (!chromeDriverFile.setExecutable(true)) {
            System.out.println("⚠️ Не удалось установить права на выполнение для ChromeDriver");
        }
        
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.out.println("✅ Использую ЛОКАЛЬНЫЙ ChromeDriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver(String driversDir) {
        String yandexDriverPath = driversDir + "yandexdriver";
        File yandexDriverFile = new File(yandexDriverPath);
        
        if (!yandexDriverFile.exists()) {
            throw new RuntimeException("❌ YandexDriver не найден: " + yandexDriverPath);
        }
        
        // Устанавливаем права на выполнение
        if (!yandexDriverFile.setExecutable(true)) {
            System.out.println("⚠️ Не удалось установить права на выполнение для YandexDriver");
        }
        
        System.setProperty("webdriver.chrome.driver", yandexDriverPath);
        System.out.println("✅ Использую ЛОКАЛЬНЫЙ YandexDriver");

        ChromeOptions options = new ChromeOptions();
        
        String yandexPath = findYandexBrowser();
        if (yandexPath != null) {
            options.setBinary(yandexPath);
            System.out.println("✅ Яндекс браузер найден: " + yandexPath);
        } else {
            System.out.println("⚠️ Яндекс браузер не найден, используется Chrome");
        }

        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }

    private static String findYandexBrowser() {
        String[] possiblePaths = {
                "/Applications/Yandex.app/Contents/MacOS/Yandex",
                "/Applications/Yandex.app/Contents/MacOS/Yandex Browser",
                System.getProperty("user.home") + "/Applications/Yandex.app/Contents/MacOS/Yandex"
        };

        for (String path : possiblePaths) {
            File browserFile = new File(path);
            if (browserFile.exists()) {
                return path;
            }
        }
        return null;
    }
}
