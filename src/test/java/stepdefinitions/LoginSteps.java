package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class LoginSteps {

    WebDriver driver = DriverFactory.getDriver();

    @Given("User is on the login page")
    public void userIsOnTheLoginPage() {
        driver.get("https://www.saucedemo.com");
    }

    @When("User enters valid credentials")
    public void userEnterValidCredentials() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String arg0, String arg1) {
        driver.findElement(By.id("user-name")).sendKeys(arg0);
        driver.findElement(By.id("password")).sendKeys(arg1);
    }

    @Then("User clicks on Login button")
    public void userClicksOnLoginButton() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("User should see {string}")
    public void userShouldSee(String arg0) {
        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertEquals(arg0, errorMessage);
    }


    @Given("User is logged in as {string} with password {string}")
    public void userIsLoggedInAsWithPassword(String username, String password) {
        driver.get("https://www.saucedemo.com");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
}