package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class CartPage {

    private WebDriver driver;

    private By cartItems = By.className("cart_item");
    private By itemNames = By.className("inventory_item_name");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By itemTotalLabel = By.className("summary_subtotal_label");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getItemsCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(itemNames)) {
            names.add(element.getText());
        }
        return names;
    }

    public void removeProduct(String productName) {
        String id = "remove-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(id)).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }


    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public double getItemTotal() {
        String text = driver.findElement(itemTotalLabel).getText();
        return Double.parseDouble(text.split("\\$")[1]);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}