package teste;


import base.BaseTest;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import pages.LoginPage;
import tools.jackson.databind.JsonNode;
import utils.DataDriven;



public class LoginTest extends BaseTest {
    @Test
    public void loginTestSuccessfulLogin(){
        JsonNode data = DataDriven.jsonReader().get("validLogin");
        var loginPage =new LoginPage(driver);
        loginPage.login(data.get("username").asText(),data.get("password").asText());

        assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Successful login should redirect to inventory page.");
    }

    @Test
    public void loginTestVerifyInvalidLogin(){
        JsonNode data = DataDriven.jsonReader().get("invalidLogin");
        var loginPage = new LoginPage(driver);
        loginPage.login(data.get("username").asText(),data.get("password").asText());

        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Invalid-login error should explain that the username/password do not match.");
    }
    @Test
    public void loginTestVerifyLoginWithoutPassword(){
        JsonNode data = DataDriven.jsonReader().get("missingPassword");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(data.get("username").asText(), data.get("password").asText());

        assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "An error should be displayed when the password is empty.");
    }

}
