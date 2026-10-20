package teste;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import tools.jackson.databind.JsonNode;
import utils.DataDriven;

import static org.testng.Assert.assertTrue;

public class InventoryTest extends BaseTest{


        @Test
        public void verifySuccessfulLogin() {
            JsonNode data = DataDriven.jsonReader().get("validLogin");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(data.get("username").asText(), data.get("password").asText());

            assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                    "Successful login should redirect to inventory page.");
        }

        @Test
        public void verifyInvalidLogin() {
            JsonNode data = DataDriven.jsonReader().get("invalidLogin");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(data.get("username").asText(), data.get("password").asText());

            assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                    "Invalid-login error should explain that the username/password do not match.");
        }

        @Test
        public void verifyLoginWithoutPassword() {
            JsonNode data = DataDriven.jsonReader().get("missingPassword");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(data.get("username").asText(), data.get("password").asText());

            assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                    "An error should be displayed when the password is empty.");
        }

}
