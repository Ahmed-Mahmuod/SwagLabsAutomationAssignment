package teste;


import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DataDriven;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                DataDriven.getValue("validUser", "username"),
                DataDriven.getValue("validUser", "password"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "User was not redirected to the inventory page");
    }

    @Test
    public void verifyInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                DataDriven.getValue("invalidUser", "username"),
                DataDriven.getValue("invalidUser", "password"));

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Wrong error message for invalid login");
    }

    @Test
    public void verifyLoginWithoutPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                DataDriven.getValue("noPasswordUser", "username"),
                DataDriven.getValue("noPasswordUser", "password"));

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Password is required"),
                "Wrong error message when password is empty");
    }
}

