import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntroductionToSeleniumWebDriverTEST {
//    Домашнее задание для урока 4 - Введение в Selenium WebDriver
//
//    Написание первого UI автотеста
//
//1. Откройте страницу https://bonigarcia.dev/selenium-webdriver-java/
//2. Проверьте, что все ссылки для Chapter 3-9 доступны и при нажатии открывается соответствующая страница
//3. Проверьте заголовок страницы и url
//4. Проверьте, что все ссылки принадлежат определенному Chapter, например, WebForm в Chapter 3. WebDriver Fundamentals

    // не разбирался как сделать чтобы проходили все ассерты...
    private WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    private static final List<List<String>> ITEMS = new ArrayList<>();
    static {
        // Добавляем пункты и подпункты
        boolean practiceSite = ITEMS.add(Arrays.asList("Practice site"));
        ITEMS.add(Arrays.asList("Chapter 3. WebDriver Fundamentals",
                "Web form",
                "Navigation example",
                "Dropdown menu",
                "Mouse over",
                "Drag and drop",
                "Drawing in canvas",
                "Loading images",
                "Slow calculator"
                ));
        ITEMS.add(Arrays.asList("Chapter 4. Browser-Agnostic Features",
                "This is a long page",
                "Infinite scroll",
                "Shadow DOM",
                "Cookies",
                "NO_TAGS",
                "IFrame",
                "Dialog boxes",
                "Web storage"
        ));
        ITEMS.add(Arrays.asList("Chapter 5. Browser-Specific Manipulation",
                "Geolocation",
                "Notifications",
                "Get user media",
                "Multilanguage page",
                "Console logs"
        ));
        ITEMS.add(Arrays.asList("Chapter 7. The Page Object Model (POM)",
                "Login form",
                "Slow login form"
        ));
        ITEMS.add(Arrays.asList("Chapter 8. Testing Framework Specifics",
                "Random calculator"
        ));
        ITEMS.add(Arrays.asList("Chapter 9. Third-Party Integrations",
                "Download files",
                "A/B Testing",
                "Data types"
        ));
    }

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
    @Tag("EXAMPLE")
    public void openHomePageTest() {
        String actualTitle = driver.getTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", actualTitle);
    }

    @Test
    public void openChapterLinksTest() throws InterruptedException {
        int [] chapter = {3,4,5,7,8,9};
        for (int j : chapter) {
            System.out.println(j);
        }
        List<WebElement> chapters = driver.findElements(By.tagName("h5"));
        for (int w = 0; w < chapters.size(); w++){
            System.out.println(chapters.get(w).getText());
            List<WebElement> items = chapters.get(w).findElements(By.xpath("following-sibling::a"));
                for ( int i = 0; i < items.size(); i++) {
                    String href = items.get(i).getAttribute("href");
                    String expectedResult = ITEMS.get(w).get(i+1);
                    driver.get(href);
                  //  Thread.sleep(1000);
                    List<WebElement> h1Elements = driver.findElements(By.xpath("//h1")); // тут идет неправльное использование, потому что я не беру данные из списка, а запрашиваю заново.
                    String actualResult;
                    if (h1Elements.isEmpty()) actualResult = "NO_TAGS";
                    else actualResult = driver.findElement(By.xpath("(//h1)[2]")).getText();
                    assertEquals(expectedResult, actualResult);
                    driver.navigate().back();
                    System.out.println(items.get(i).getText() + " " + actualResult.equals(expectedResult));
                }

        }
    }

}
