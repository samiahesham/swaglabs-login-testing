import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // حددي مكان الدرايفر
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver-win64\\chromedriver.exe");
        WebDriver b = new ChromeDriver();

        // روحي لليوتيوب
        b.navigate().to("https://www.youtube.com/");

        // انتظري شوية لحد ما الصفحة تفتح
        b.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // دوري على خانة البحث واكتبي فيها
        WebElement search = b.findElement(By.name("search_query"));
        search.sendKeys("amr diab");
        search.sendKeys(Keys.ENTER);
    }
}
