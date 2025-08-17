package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.DriverFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class SortingSteps {
    WebDriver driver = DriverFactory.getDriver();

    @Given("User is on the products page")
    public void userIsOnTheProductsPage() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new IllegalStateException(
                    "User is not on the products page. Expected: " + expectedUrl + " but was: " + actualUrl
            );
        }
    }

    @When("User sorts products by {string}")
    public void userSortsProductsBy(String sortOption) {
        WebElement sortDropdown = driver.findElement(By.cssSelector("[data-test='product-sort-container']"));
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
    }

    @Then("products should be sorted by {string} correctly")
    public void productsShouldBeSortedByCorrectly(String sortOption) {
        if (sortOption.contains("Name")) {
            List<WebElement> nameElements = driver.findElements(By.className("inventory_item_name"));
            List<String> actualNames = new ArrayList<>();
            for (WebElement el : nameElements) {
                actualNames.add(el.getText().trim());
            }

            List<String> expectedNames = new ArrayList<>(actualNames);
            if (sortOption.contains("A to Z")) {
                Collections.sort(expectedNames);
            } else {
                expectedNames.sort(Collections.reverseOrder());
            }

            Assertions.assertEquals(
                    expectedNames,
                    actualNames,
                    "Product name sorting failed for: " + sortOption
            );
        } else if (sortOption.contains("Price")) {
            List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
            List<Double> actualPrices = new ArrayList<>();
            for (WebElement el : priceElements) {
                actualPrices.add(Double.parseDouble(el.getText().replace("$", "")));
            }

            List<Double> expectedPrices = new ArrayList<>(actualPrices);
            if (sortOption.contains("low to high")) {
                Collections.sort(expectedPrices);
            } else {
                expectedPrices.sort(Collections.reverseOrder());
            }

            Assertions.assertEquals(
                    expectedPrices,
                    actualPrices,
                    "Product price sorting failed for: " + sortOption
            );
        }
    }
}
