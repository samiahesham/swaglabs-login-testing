import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InvalidLoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }

    // ===== COMPONENT TEST CASES =====

    @Test // TC01: invalid username, valid password - Component
    public void TC01_invalidUsername_validPassword() {
        login("invalid_user", "secret_sauce");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC02: valid username, invalid password - Component
    public void TC02_validUsername_invalidPassword() {
        login("standard_user", "wrong_password");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC03: empty username and password - Component
    public void TC03_emptyUsernameAndPassword() {
        login("", "");
        assertErrorContains("Username is required");
    }

    @Test // TC04: locked out user - Component
    public void TC04_lockedOutUser() {
        login("locked_out_user", "secret_sauce");
        assertErrorContains("Sorry, this user has been locked out.");
    }

    @Test // TC07: invalid username with special characters - Component
    public void TC05_invalidUsernameWithSpecialChars() {
        login("invalid@user", "secret_sauce");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC08: invalid password with special characters - Component
    public void TC06_invalidPasswordWithSpecialChars() {
        login("standard_user", "wrong@password!");
        assertErrorContains("Username and password do not match any user in this service");
    }
    @Test //  TC6: spaces in both fields - Component
    public void TC03_spacesInFields() {
        login("     ", "     ");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC09: empty password - Component
    public void TC07_emptyPassword() {
        login("standard_user", "");
        assertErrorContains("Password is required");
    }

    @Test // TC10: invalid password with leading/trailing whitespace - Component
    public void TC08_invalidPasswordWithWhitespaces() {
        login("standard_user", " wrong_password ");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC11: username length below minimum limit - Component
    public void TC09_usernameLengthBelowMinLimit() {
        login("a", "secret_sauce");
        assertErrorContains("Username must be at least 3 characters long");
    }

    @Test // TC12: password length below minimum limit - Component
    public void TC10_passwordLengthBelowMinLimit() {
        login("standard_user", "ab");
        assertErrorContains("Password must be at least 6 characters long");
    }


    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    private void assertErrorContains(String expectedMessage) {
        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(errorMessage.getText().contains(expectedMessage),
                "Expected error message not found. Actual: " + errorMessage.getText());
    }

    @AfterMethod
    public void tearDown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error while quitting the driver: " + e.getMessage());
            }
        }
    }
}
