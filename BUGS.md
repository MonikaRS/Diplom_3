# 🐞 Обнаруженные баги в приложении Stellar Burgers

**Дата тестирования:** 13.02.2026  
**Версия приложения:** production  
**Тестировщик:** Моника Сапкина  
**Проект:** Diplom3

---

## Баг #1: Неправильный атрибут name у поля Email на странице регистрации

| Поле | Значение |
|------|----------|
| **URL** | `https://stellarburgers.education-services.ru/register` |
| **Что должно быть** | `<input name="email">` |
| **Что на самом деле** | `<input name="name">` |
| **Селектор в тесте** | `//label[text()='Email']/following-sibling::input` |
| **Влияние** | Тесты не могут найти поле по стандартному селектору |
| **Статус** | Открыт |

**Затронутые тесты:**
- `UserRegistrationUITests.successfulRegistration()`
- `UserRegistrationUITests.registrationWithShortPassword()`

---

## Баг #2: Поле Email на странице логина имеет неправильный атрибут name

| Поле | Значение |
|------|----------|
| **URL** | `https://stellarburgers.education-services.ru/login` |
| **Что должно быть** | `<input name="email">` |
| **Что на самом деле** | `<input name="name">` |
| **Селектор в тесте** | `//label[text()='Email']/following-sibling::input` |
| **Влияние** | Тесты не могут найти поле по стандартному селектору |
| **Статус** | Открыт |

**Затронутые тесты:**
- `UserLoginUITests.loginFromPersonalAccountButton()`
- `UserLoginUITests.loginFromRegistrationForm()`
- `UserLoginUITests.loginFromForgotPasswordForm()`

---

## Баг #3: Поле Пароль на странице логина содержит русские буквы в атрибуте name

| Поле | Значение |
|------|----------|
| **URL** | `https://stellarburgers.education-services.ru/login` |
| **Что должно быть** | `<input name="password">` |
| **Что на самом деле** | `<input name="Пароль">` (русскими буквами!) |
| **Селектор в тесте** | `//label[text()='Пароль']/following-sibling::input` |
| **Влияние** | Тесты не могут найти поле по стандартному селектору |
| **Статус** | Критический |

**Затронутые тесты:**
- `UserLoginUITests.loginFromPersonalAccountButton()`
- `UserLoginUITests.loginFromRegistrationForm()`
- `UserLoginUITests.loginFromForgotPasswordForm()`

---

## 📊 Статистика багов

| Приоритет | Количество | Баги |
|-----------|------------|------|
| Критический | 1 | Баг #3 |
| Высокий | 2 | Баги #1, #2 |
| Низкий | 0 | — |

---

## 🔧 Использованные обходные решения

Во всех тестах используются селекторы по `label`:

```java
@FindBy(xpath = "//label[text()='Email']/following-sibling::input")
@FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")