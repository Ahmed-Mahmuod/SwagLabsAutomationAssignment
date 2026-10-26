package teste;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import utils.DataDriven;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class CartTest extends BaseTest {

    @Test
    public void verifySocialLinks() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.clickLinkedIn();
        Assert.assertTrue(getUrlOfNewTab("linkedin").contains("linkedin"), "LinkedIn URL is wrong");

        inventoryPage.clickFacebook();
        Assert.assertTrue(getUrlOfNewTab("facebook").contains("facebook"), "Facebook URL is wrong");

        inventoryPage.clickX();
        Assert.assertTrue(getUrlOfNewTab("x.com").contains("x.com"), "X URL is wrong");
    }

    private String getUrlOfNewTab(String expectedPart) {
        String mainTab = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            if (!tab.equals(mainTab)) {
                driver.switchTo().window(tab);
            }
        }

        try {
            wait.until(ExpectedConditions.urlContains(expectedPart));
        } catch (Exception e) {
        }
        String url = driver.getCurrentUrl();

        driver.close();
        driver.switchTo().window(mainTab);
        return url;
    }

    @Test
    public void verifyCartIsEmpty() {
        loginWithValidUser();
        new InventoryPage(driver).openCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getItemsCount(), 0, "Cart should be empty");
    }

    @Test
    public void verifyAddThreeProducts() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> products = DataDriven.getCartProducts(); // from JSON

        for (String product : products) {
            inventoryPage.addProductToCart(product);
        }
        inventoryPage.openCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemNames(), products,
                "Cart items are different from the added items (or in a different order)");
    }

    @Test
    public void verifyRemoveOneProduct() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> products = DataDriven.getCartProducts();

        for (String product : products) {
            inventoryPage.addProductToCart(product);
        }
        inventoryPage.openCart();

        CartPage cartPage = new CartPage(driver);
        String removedProduct = "Sauce Labs Bolt T-Shirt";
        cartPage.removeProduct(removedProduct);
        cartPage.clickContinueShopping(); // back to inventory

        Assert.assertEquals(inventoryPage.getButtonText(removedProduct), "Add to cart",
                "Removed product should show 'Add to cart'");

        for (String product : products) {
            if (!product.equals(removedProduct)) {
                Assert.assertEquals(inventoryPage.getButtonText(product), "Remove",
                        product + " should still show 'Remove'");
            }
        }
    }

    @Test
    public void verifyCartTotalPrice() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> products = DataDriven.getCartProducts();

        double expectedSum = 0;
        for (String product : products) {
            expectedSum += inventoryPage.getProductPrice(product);
        }

        for (String product : products) {
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.openCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
        cartPage.fillCheckoutInfo(
                DataDriven.getValue("checkoutInfo", "firstName"),
                DataDriven.getValue("checkoutInfo", "lastName"),
                DataDriven.getValue("checkoutInfo", "postalCode"));
        cartPage.clickContinue();

        Assert.assertEquals(cartPage.getItemTotal(), expectedSum, 0.01,
                "Item total is different from the sum of the prices");
    }

    @Test
    public void verifyCheckoutWithEmptyCart() {
        loginWithValidUser();
        new InventoryPage(driver).openCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getItemsCount(), 0, "Cart should be empty");

        cartPage.clickCheckout();

        Assert.assertTrue(cartPage.getCurrentUrl().contains("checkout-step-one"),
                "Expected the site to allow opening checkout with an empty cart");
    }

    @Test
    public void verifyCartStateAfterLogoutAndLogin() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> products = DataDriven.getCartProducts();

        inventoryPage.addProductToCart(products.get(0));
        inventoryPage.addProductToCart(products.get(1));

        inventoryPage.logout();
        loginWithValidUser();

        new InventoryPage(driver).openCart();
        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(cartPage.getItemsCount(), 2,
                "Expected the 2 items to stay in the cart after logout/login");
    }
}
