import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginHappyTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }

    // ===== COMPONENT TEST CASES =====

    @Test //  TC1: valid credentials - Component
    public void TC01_validLogin() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed with valid credentials.");
    }

    @Test //  TC5: both username and password are incorrect - Component
    public void TC02_wrongUsernameAndPassword() {
        login("wrong_user", "wrong_pass");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test //  TC6: spaces in both fields - Component
    public void TC03_spacesInFields() {
        login("     ", "     ");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC7: special characters in username - Component
    public void TC04_specialCharsInUsername() {
        login("!@#$%", "secret_sauce");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC8: special characters in password - Component
    public void TC05_specialCharsInPassword() {
        login("standard_user", "!@#$%");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC9: Arabic username - Component
    public void TC06_arabicUsername() {
        login("تجربة", "secret_sauce");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test //  TC10: Arabic password - Component
    public void TC07_arabicPassword() {
        login("standard_user", "كلمةالسر");
        assertErrorContains("Username and password do not match any user in this service");
    }

    @Test // TC11: locked_out_user - Component
    public void TC08_lockedOutUser() {
        login("locked_out_user", "secret_sauce");
        assertErrorContains("Sorry, this user has been locked out.");
    }

    @Test //  TC12: problem_user - Component
    public void TC09_problemUserLogin() {
        login("problem_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed for problem_user.");
    }

    @Test //  TC13: performance_glitch_user - Component
    public void TC10_performanceGlitchUserLogin() {
        login("performance_glitch_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed for performance_glitch_user.");
    }

    // ===== INTEGRATION TEST CASES =====

    @Test //  TC2: login from multiple devices/browsers - Integration
    public void TC11_multipleDevices() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed on multiple devices.");
    }

    @Test // TC3: slow/unstable internet - Integration
    public void TC12_slowInternetSimulation() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed on slow connection.");
    }

    // ===== SYSTEM TEST CASES =====

    @Test // System Test Case 1
    public void TC13_systemTest1() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "System test case 1 failed.");
    }

    @Test //  System Test Case 2
    public void TC14_systemTest2() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "System test case 2 failed.");
    }



    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

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
            Thread.sleep(5000);
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
