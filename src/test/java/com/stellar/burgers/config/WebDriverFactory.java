package com.stellar.burgers.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;

public class WebDriverFactory {
    // Флаг для выбора способа инициализации драйвера
    private static final boolean USE_WEBDRIVER_MANAGER = false; // Поменяйте на true если хотите через WebDriverManager

    public static WebDriver createDriver(String browserName) {
        System.out.println("\n" + "🚀".repeat(30));
        System.out.println("СОЗДАНИЕ ДРАЙВЕРА: " + browserName.toUpperCase());
        System.out.println("Режим: " + (USE_WEBDRIVER_MANAGER ? "WebDriverManager" : "Локальный драйвер"));
        System.out.println("🚀".repeat(30));

        String projectDir = System.getProperty("user.dir");
        String driversDir = projectDir + "/drivers/";

        // Создаем папку для драйверов если её нет
        File driversFolder = new File(driversDir);
        if (!driversFolder.exists() && !driversFolder.mkdirs()) {
            System.err.println("⚠️ Не удалось создать папку для драйверов: " + driversDir);
        }

        WebDriver driver = null;

        switch (browserName.toLowerCase()) {
            case "chrome":
                System.out.println("🌐 Браузер: Google Chrome");

                try {
                    if (USE_WEBDRIVER_MANAGER) {
                        System.out.println("🔄 Использую WebDriverManager...");
                        // Пытаемся использовать WebDriverManager если доступен
                        try {
                            Class.forName("io.github.bonigarcia.wdm.WebDriverManager");
                            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                            System.out.println("✅ WebDriverManager успешно настроил ChromeDriver");
                        } catch (ClassNotFoundException e) {
                            System.out.println("⚠️ WebDriverManager не найден, использую локальный драйвер");
                            setupLocalChromeDriver(driversDir);
                        }
                    } else {
                        setupLocalChromeDriver(driversDir);
                    }

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

                    driver = new ChromeDriver(chromeOptions);
                    System.out.println("✅ Chrome драйвер создан");

                } catch (Exception e) {
                    System.err.println("❌ Ошибка создания Chrome драйвера: " + e.getMessage());
                    throw new RuntimeException("Не удалось создать Chrome драйвер", e);
                }
                break;

            case "yandex":
                System.out.println("🌐 Браузер: Яндекс.Браузер");

                try {
                    // Для Яндекс всегда используем локальный драйвер
                    setupLocalYandexDriver(driversDir);

                    ChromeOptions yandexOptions = new ChromeOptions();

                    // Ищем Яндекс браузер
                    String yandexPath = findYandexBrowser();
                    if (yandexPath != null) {
                        yandexOptions.setBinary(yandexPath);
                        System.out.println("✅ Яндекс браузер найден: " + yandexPath);
                    } else {
                        System.out.println("⚠️ Яндекс браузер не найден, использую Chrome");
                    }

                    yandexOptions.addArguments("--start-maximized");
                    yandexOptions.addArguments("--no-sandbox");
                    yandexOptions.addArguments("--disable-dev-shm-usage");
                    yandexOptions.addArguments("--disable-blink-features=AutomationControlled");

                    driver = new ChromeDriver(yandexOptions);
                    System.out.println("✅ Яндекс драйвер создан");

                } catch (Exception e) {
                    System.err.println("❌ Ошибка создания Яндекс драйвера: " + e.getMessage());
                    throw new RuntimeException("Не удалось создать Яндекс драйвер", e);
                }
                break;

            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }

        // Настраиваем таймауты
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(30));

        System.out.println("✅ Драйвер успешно создан");
        System.out.println("🚀".repeat(30));
        return driver;
    }

    // Метод для настройки локального ChromeDriver
    private static void setupLocalChromeDriver(String driversDir) {
        String chromeDriverPath = driversDir + "chromedriver";
        File chromeDriverFile = new File(chromeDriverPath);

        if (!chromeDriverFile.exists()) {
            throw new RuntimeException("ChromeDriver не найден: " + chromeDriverPath +
                    "\nСкачайте с: https://chromedriver.chromium.org/");
        }

        // Даем права на выполнение (для Unix систем)
        if (!chromeDriverFile.canExecute() && !chromeDriverFile.setExecutable(true)) {
            System.err.println("⚠️ Не удалось установить права на выполнение для: " + chromeDriverPath);
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.out.println("✅ Использую локальный ChromeDriver: " + chromeDriverPath);
    }

    // Метод для настройки локального YandexDriver
    private static void setupLocalYandexDriver(String driversDir) {
        String yandexDriverPath = driversDir + "yandexdriver";
        File yandexDriverFile = new File(yandexDriverPath);

        if (!yandexDriverFile.exists()) {
            // Если нет yandexdriver, попробуем использовать chromedriver
            String chromeDriverPath = driversDir + "chromedriver";
            File chromeDriverFile = new File(chromeDriverPath);

            if (chromeDriverFile.exists()) {
                System.out.println("⚠️ YandexDriver не найден, использую ChromeDriver");
                yandexDriverPath = chromeDriverPath;
                yandexDriverFile = chromeDriverFile;
            } else {
                throw new RuntimeException("Драйвер не найден. Поместите chromedriver или yandexdriver в папку: " + driversDir);
            }
        }

        // Даем права на выполнение
        if (!yandexDriverFile.canExecute() && !yandexDriverFile.setExecutable(true)) {
            System.err.println("⚠️ Не удалось установить права на выполнение для: " + yandexDriverPath);
        }

        System.setProperty("webdriver.chrome.driver", yandexDriverPath);
        System.out.println("✅ Использую драйвер: " + yandexDriverPath);
    }

    // Метод для поиска Яндекс браузера
    private static String findYandexBrowser() {
        String[] possiblePaths = {
                "/Applications/Yandex.app/Contents/MacOS/Yandex",
                "/Applications/Yandex.app/Contents/MacOS/Yandex Browser",
                System.getProperty("user.home") + "/Applications/Yandex.app/Contents/MacOS/Yandex",
                "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe"
        };

        for (String path : possiblePaths) {
            File browserFile = new File(path);
            if (browserFile.exists()) {
                return path;
            }
        }
        return null;
    }

    // Метод для быстрого тестирования
    public static void main(String[] args) {
        try {
            WebDriver driver = createDriver("chrome");
            driver.get("https://stellarburgers.nomoreparties.site/");
            System.out.println("Страница загружена: " + driver.getTitle());
            Thread.sleep(3000);
            driver.quit();
            System.out.println("✅ Тест пройден успешно!");
        } catch (Exception e) {
            System.err.println("❌ Тест не пройден: " + e.getMessage());
            // Вместо printStackTrace используем логгирование
            System.err.println("Стек вызовов:");
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().contains("stellar")) {
                    System.err.println("    at " + element);
                }
            }
        }
    }
}