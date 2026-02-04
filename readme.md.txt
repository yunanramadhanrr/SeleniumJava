# Selenium Java Automation - SauceDemo

Automation testing framework using Selenium Java + JUnit 5.

## 🔧 Tech Stack
- Java 17
- Selenium WebDriver
- JUnit 5
- Maven
- WebDriverManager

## 🧪 Test Scenarios
- Login success
- Login failed (wrong password)
- Login failed (multiple data - parameterized test)

## 🏗️ Project Structure
- `pages` → Page Object Model
- `tests` → Test cases
- `base` → Driver setup & teardown
- `utils` → Utilities (screenshot, wait)

## ▶️ How to Run
```bash
mvn clean test
