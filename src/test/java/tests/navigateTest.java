package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.HomePage;
import org.example.navigateFromKitbaut;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class navigateTest{

    private static WebDriver driver;

    private String linkToClick;
    private String expectedResult;

    // 3. בנאי (Constructor) - מקבל את הפרמטרים מהרשימה למטה
    public navigateTest(String linkToClick, String expectedResult) {
        this.linkToClick = linkToClick;
        this.expectedResult = expectedResult;
    }

    // 4. הגדרת הנתונים - כל שורה היא ריצה נפרדת של הטסט
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // { "הטקסט עליו לוחצים", "הטקסט המצופה בכותרת/פירורי לחם" }
                { "ילד נכה", "ילד נכה" },
                { "אזרח ותיק", "אזרח ותיק" },
                { "נפגעי עבודה", "נפגעי עבודה" },
                { "מזונות", "מזונות" }
        });
    }
    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testNavigateAndVerifyBreadcrumbs() {
        // 1. ניווט ידני לדף הראשי כדי להבטיח שאנחנו במקום הנכון לפני יצירת האובייקט
        driver.get("https://www.btl.gov.il/benefits/Pages/default.aspx");

        // 2. כעת בטוח ליצור את האובייקט (כי אנחנו בדף הנכון)
        navigateFromKitbaut page = new navigateFromKitbaut(driver);

        System.out.println("בודק כעת את הקטגוריה: " + this.linkToClick);

        // 3. לחיצה על הקטגוריה
        page.clickOnSubCategory(this.linkToClick);

        // 4. בדיקה
        String actualBreadcrumb = page.getBreadcrumbsText();

        Assert.assertTrue("שגיאה! הטקסט בדף לא תואם.\n" +
                        "ציפינו לראות: " + this.expectedResult + "\n" +
                        "קיבלנו בפועל: " + actualBreadcrumb,
                actualBreadcrumb.contains(this.expectedResult));
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}