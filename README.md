# Test Automation Project

## Overview
This project is a test automation suite developed to evaluate the programming skills of a Test Automation Engineer for Validaide.
It uses Selenium WebDriver, Cucumber, and JUnit to automate functional tests for the SauceDemo e-commerce website.
The tests cover key functionalities such as login, product sorting, and checkout processes, ensuring robust validation of the application's behavior.

## Project Structure
- **src/main/java/utils/**: Contains utility classes for managing WebDriver instances and helper functions.
- **src/main/java/stepdefinitions/**: Contains Cucumber step definitions for test scenarios.
- **src/main/java/runners/**: Contains the Cucumber test runner.
- **src/test/resources/features/**: Contains Cucumber feature files defining test scenarios.
- **target/**: Output directory for test reports (e.g., `test-results.html`).

## Prerequisites
- **Java**: JDK 11 or higher
- **Maven**: For dependency management and test execution

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Install Dependencies**:
   Run the following command to download all required Maven dependencies:
   ```bash
   mvn clean install
   ```

3. **Configure ChromeDriver**:
   The `WebDriverManager` library automatically handles ChromeDriver setup. Ensure an active internet connection for the initial setup.

4. **Run Tests**:
   Execute the tests using Maven (parallel execution is enabled by default):
   ```bash
   mvn clean test
   ```
   To run in headless mode, use:
   ```bash
   mvn clean test -Dheadless=true
   ```
   To disable parallel execution, use:
   ```bash
   mvn clean test -Dcucumber.execution.parallel.enabled=false
   ```

5. **View Test Reports**:
   After test execution, an HTML report is generated at `target/test-results.html`.

## Future Improvements
- Add support for other browsers (e.g., Firefox, Edge) in `DriverFactory`.
- Enhance reporting with screenshots for failed tests.
- Optimize parallel test execution for improved performance.

## Defect was found in application
- **Partially Clickable Sort Button**:
    - **Description**: The sort button (dropdown arrow) on the inventory page is only partially clickable, preventing the display of sorting options.
    - **Steps to Reproduce**:
        1. Log in successfully with valid credentials and navigate to the products page.
        2. Attempt to sort products by clicking the arrow on the sort button.
    - **Expected Result**: Sorting options (e.g., Name A to Z, Price low to high) are displayed.
    - **Actual Result**: Sorting options are not displayed due to the sort button being only partially clickable.