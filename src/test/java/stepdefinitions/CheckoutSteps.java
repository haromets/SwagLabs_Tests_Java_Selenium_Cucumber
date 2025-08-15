package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverFactory;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckoutSteps {
    WebDriver driver = DriverFactory.getDriver();
    List<String> itemName = new ArrayList<>();
    List<Double> itemPrice = new ArrayList<>();

    @When("User adds {string} to the cart")
    public void userAddsToTheCart(String productName) {
        itemName.add(productName);
        String xpath = "//div[@class='inventory_item'][.//div[text()='" + productName + "']]//button";
        String xpathPrice = "//div[@class='inventory_item'][.//div[text()='" + productName + "']]//div[@class='inventory_item_price']";
        itemPrice.add(Double.parseDouble(driver.findElement(By.xpath(xpathPrice)).getText().replace("$", "")));
        driver.findElement(By.xpath(xpath)).click();
    }

    @When("cart icon has total amount {string}")
    public void cartIconHasTotalAmount(String amountOfItems) {
        By badge = By.className("shopping_cart_badge");
        WaitUtils.waitForTextInElement(driver, badge, amountOfItems, 5);
        String totalAmountOfItems = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(
                "The amount of items in the cart does not match the expected total!",
                amountOfItems,
                totalAmountOfItems
        );
    }

    @And("User clicks on cart icon to proceeds to checkout")
    public void userClicksOnCartIconToProceedsToCheckout() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Given("User is on the cart page")
    public void userIsOnTheCartPage() {
        String expectedUrl = "https://www.saucedemo.com/cart.html";
        String actualUrl = driver.getCurrentUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new IllegalStateException(
                    "User is not on the cart page. Expected: " + expectedUrl + " but was: " + actualUrl
            );
        }
    }

    @And("verify items and prices on checkout step")
    public void verifyItemsAndPricesOnCheckoutStepOne() {
        // Locate all product name elements
        List<WebElement> nameElements = driver.findElements(By.xpath("//div[@class='cart_item']//div[@class='inventory_item_name']"));

        // Locate all price elements
        List<WebElement> priceElements = driver.findElements(By.xpath("//div[@class='cart_item']//div[@class='inventory_item_price']"));

        // Store names
        List<String> actualNames = new ArrayList<>();
        for (WebElement name : nameElements) {
            actualNames.add(name.getText());
        }

        // Store prices
        List<Double> actualPrices = new ArrayList<>();
        for (WebElement price : priceElements) {
            String priceText = price.getText().replace("$", "").trim();
            actualPrices.add(Double.parseDouble(priceText));
        }
        for (int i = 0; i < itemName.size(); i++) {
            String expectedName = itemName.get(i);
            String actualName = actualNames.get(i);

            Double expectedPrice = itemPrice.get(i);
            Double actualPrice = actualPrices.get(i);

            // Verify name
            Assert.assertEquals(
                    "Mismatch in product name at index " + i +
                            " | Expected: " + expectedName + " | Actual: " + actualName,
                    expectedName, actualName
            );

            // Verify price
            Assert.assertEquals(
                    "Mismatch in product price at index " + i +
                            " (" + expectedName + ")" +
                            " | Expected: " + expectedPrice + " | Actual: " + actualPrice,
                    expectedPrice, actualPrice
            );

        }
    }

    @Then("User clicks on Checkout button")
    public void userClicksOnCheckoutButton() {
        driver.findElement(By.id("checkout")).click();
    }

    @And("User enters First Name {string}, Last Name {string} and Postal Code {string} and clicks Continue button")
    public void userEntersFirstNameLastNameAndPostalCodeAndClicksContinueButton(String firstName, String lastName, String postalCode) {
        WaitUtils.waitForElementToBeVisible(driver, By.id("first-name"), 5).sendKeys(firstName);
        WaitUtils.waitForElementToBeVisible(driver, By.id("last-name"), 5).sendKeys(lastName);
        WaitUtils.waitForElementToBeVisible(driver, By.id("postal-code"), 5).sendKeys(postalCode);
        WaitUtils.waitForElementToBeClickable(driver, By.id("continue"), 5).click();
    }

    @Then("verify the total price without tax")
    public void verifyTheTotalPriceWithoutTax() {
        Double actualTotalPrice = 0.0;
        for (Double price : itemPrice) {
            actualTotalPrice += price;
        }
        String totalPrice = driver.findElement(By.className("summary_subtotal_label")).getText().replace("Item total: $", "");
        Double expectedTotalPrice = Double.parseDouble(totalPrice);
        Assert.assertEquals("Total price does not match", expectedTotalPrice, actualTotalPrice);
    }

    @Then("verify the final price including tax")
    public void verifyTheFinalPriceIncludingTax() {
        String subtotalText = driver.findElement(By.className("summary_subtotal_label"))
                .getText().replace("Item total: $", "");
        String taxText = driver.findElement(By.className("summary_tax_label"))
                .getText().replace("Tax: $", "");
        String totalText = driver.findElement(By.className("summary_total_label"))
                .getText().replace("Total: $", "");

        double subtotal = Double.parseDouble(subtotalText);
        double tax = Double.parseDouble(taxText);
        double expectedTotal = subtotal + tax;

        Assert.assertEquals("Final price including tax does not match",
                expectedTotal, Double.parseDouble(totalText), 0.01);
    }

    @When("User clicks on Finish button")
    public void userClicksOnFinishButton() {
        driver.findElement(By.id("finish")).click();
    }

    @And("verify the order should be completed successfully")
    public void verifyTheOrderShouldBeCompletedSuccessfully() {
        String completeHeader = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals("Thank you for your order!", completeHeader);
    }

}
