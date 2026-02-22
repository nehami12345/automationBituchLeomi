package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.navigateFromKitbaut;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class navigateTest{

    private static WebDriver driver;

    private String linkToClick;
    private String expectedResult;

    public navigateTest(String linkToClick, String expectedResult) {
        this.linkToClick = linkToClick;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "ילד נכהגגגגגגגגגגגגגג", "ילד נכהגגגגגגגגגגגגג" },
                { "אזרח ותיק", "אזרח ותיק" },
                { "נפגעי עבודה", "נפגעי עבודה" },
                { "הבטחת הכנסה", "הבטחת הכנסה" },
                {"מענק לימודים", "מענק לימודים"}
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
        try {
            driver.get("https://www.btl.gov.il/benefits/Pages/default.aspx");

            navigateFromKitbaut page = new navigateFromKitbaut(driver);

            System.out.println("בודק כעת את הקטגוריה: " + this.linkToClick);

            page.clickOnSubCategory(this.linkToClick);

            String actualBreadcrumb = page.getBreadcrumbsText();

            Assert.assertTrue("שגיאה! הטקסט בדף לא תואם.\n" +
                            "ציפינו לראות: " + this.expectedResult + "\n" +
                            "קיבלנו בפועל: " + actualBreadcrumb,
                    actualBreadcrumb.contains(this.expectedResult));
        }
        catch (AssertionError | Exception e) {
            new TestListener(driver).captureScreenshot(driver);
            throw e;
        }
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}