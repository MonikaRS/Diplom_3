# 🍔 Stellar Burgers - Автоматизация UI-тестов

![Java](https://img.shields.io/badge/Java-11-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.15.0-orange)
![Allure](https://img.shields.io/badge/Allure-2.24.0-ff69b4)
![Tests](https://img.shields.io/badge/Tests-20%2B%20passed-brightgreen)

Дипломный проект по автоматизации UI-тестирования для сервиса Stellar Burgers (Яндекс.Практикум).

## 📊 Онлайн-отчет

https://monika.github.io/Diplom3/

## 🎯 Покрытие требований

| Требование | Статус | Тестовый класс |
|------------|--------|----------------|
| ✅ Регистрация - успешная | Выполнено | `UserRegistrationUITests` |
| ✅ Регистрация - ошибка пароля | Выполнено | `UserRegistrationUITests` |
| ✅ Вход через "Войти в аккаунт" | Выполнено | `UserLoginUITests` |
| ✅ Вход через "Личный кабинет" | Выполнено | `UserLoginUITests` |
| ✅ Вход из формы регистрации | Выполнено | `UserLoginUITests` |
| ✅ Вход из восстановления пароля | Выполнено | `UserLoginUITests` |
| ✅ Конструктор - "Булки" | Выполнено | `ConstructorTest` |
| ✅ Конструктор - "Соусы" | Выполнено | `ConstructorTest` |
| ✅ Конструктор - "Начинки" | Выполнено | `ConstructorTest` |

## 🚀 Быстрый старт

```bash
# Клонировать проект
git clone https://github.com/monika/Diplom3.git
cd Diplom3

# Запустить все тесты
mvn test

# Создать Allure отчет
mvn allure:report
🛠 Технологии
Java 11

Selenium WebDriver 4.15.0

JUnit 4

Allure 2.24.0

Maven

Page Object Model

📁 Структура проекта
text
Diplom3/
├── src/test/java/com/stellar/burgers/
│   ├── config/    # Конфигурация WebDriver
│   ├── pages/     # Page Objects
│   └── tests/ui/  # Тестовые классы
├── pom.xml        # Maven конфигурация
└── README.md      # Документация
