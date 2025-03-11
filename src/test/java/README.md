# Automated Testing Project

## Overview
This project provides an automated testing framework for **API, Web UI, and Mobile applications** using **RestAssured, Selenium, and Appium**. It ensures system reliability, accuracy, and seamless user experience validation across different platforms.

## Prerequisites
Ensure the following dependencies are installed before executing the tests:
- **Java JDK 11+** → `java -version`
- **Maven** → `mvn -version`
- **ChromeDriver** (for Selenium-based UI tests)
- **Appium & ADB** (for mobile automation) → `appium -v`, `adb version`

---

## Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```
2. **Install dependencies**:
   ```bash
   mvn clean install
   ```
3. **Ensure required services are running**:
    - Start Appium: `appium`
    - Start Android Emulator or Connect a Real Device: `adb devices`

---

## Test Execution
### Run API Tests:
```bash
mvn test -Dtest=ApiTest
```
### Run Web UI Tests:
```bash
mvn test -Dtest=WebsiteTest
```
### Run Mobile App Tests:
```bash
mvn test -Dtest=WikipediaAppTest
```

---

## Troubleshooting
- **API Tests:** Ensure the API endpoint is reachable.
- **Web UI Tests:** Verify ChromeDriver is installed and compatible.
- **Mobile Tests:** Ensure Appium server is running and the device is connected.

For any queries or contributions, please feel free to reach out or open an issue in the repository.
