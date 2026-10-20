package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private final WebDriver driver;
    private final By pageTitle = By.cssSelector(".app_logo");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");
    private final By inventoryItems = By.cssSelector(".inventory_item");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public boolean isCartIconDisplayed() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    public int getProductCount() {
        return driver.findElements(inventoryItems).size();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
