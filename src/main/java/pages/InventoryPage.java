package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By products = By.className("inventory_item");
    private By cartIcon = By.className("shopping_cart_link");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");
    private By linkedinIcon = By.cssSelector(".social_linkedin a");
    private By facebookIcon = By.cssSelector(".social_facebook a");
    private By xIcon = By.cssSelector(".social_x a");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isCartIconDisplayed() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    public int getProductsCount() {
        return driver.findElements(products).size();
    }


    private String toId(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }

    public void addProductToCart(String productName) {
        driver.findElement(By.id("add-to-cart-" + toId(productName))).click();
    }

    public void removeProduct(String productName) {
        driver.findElement(By.id("remove-" + toId(productName))).click();
    }

    public String getButtonText(String productName) {
        return driver.findElement(productBlock(productName, "//button")).getText();
    }

    public double getProductPrice(String productName) {
        String priceText = driver.findElement(
                productBlock(productName, "//div[@class='inventory_item_price']")).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    private By productBlock(String productName, String innerPath) {
        return By.xpath("//div[contains(@class,'inventory_item_name') and text()='" + productName
                + "']/ancestor::div[@class='inventory_item']" + innerPath);
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }



    public void logout() {
        driver.findElement(menuButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }



    public void clickLinkedIn() {
        driver.findElement(linkedinIcon).click();
    }

    public void clickFacebook() {
        driver.findElement(facebookIcon).click();
    }

    public void clickX() {
        driver.findElement(xIcon).click();
    }
}
