import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FindLocatorsTest {

//    Домашнее задание для урока 5 - Поиск локаторов
//
//1. Откройте страницу https://bonigarcia.dev/selenium-webdriver-java/web-form.html  (https://bonigarcia.dev/selenium-webdriver-java/web-form.html)
//2. Написать локаторы для всех элементов
//3. Использовать лучшие локаторы
//4. Задание со звездочкой: Напишите по локатору каждого типа минимум 1 раз


    //Блин, неинтересное дз. Буду наверстывать упущенное непосредственно на тестах.
    private WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @BeforeEach
    public void startUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void finishDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void findLocatorsTest() throws InterruptedException {

        driver.findElement(By.id("my-text-id")).sendKeys("ALEXANDER");


        String icon = driver.findElement(By.className("img-fluid")).getDomProperty("src");
        System.out.println(icon);
        Assertions.assertEquals("https://bonigarcia.dev/selenium-webdriver-java/img/hands-on-icon.png", icon);


        String href = driver.findElement(By.cssSelector("div.col.col-2 > a")).getDomProperty("href");
        System.out.println(href);
        Assertions.assertEquals("https://github.com/bonigarcia/selenium-webdriver-java", href);

        //Thread.sleep(100000);

    }

}
