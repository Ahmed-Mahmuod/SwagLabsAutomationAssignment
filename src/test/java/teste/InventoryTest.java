package teste;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;

public class InventoryTest extends BaseTest {

    @Test
    public void verifyInventoryPageElements() {
        loginWithValidUser();
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertEquals(inventoryPage.getPageTitle(), "Swag Labs", "Wrong page title");
        Assert.assertTrue(inventoryPage.isCartIconDisplayed(), "Cart icon is not displayed");
        Assert.assertEquals(inventoryPage.getProductsCount(), 6, "Wrong number of products");
    }
}


