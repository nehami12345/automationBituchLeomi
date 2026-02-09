package tests;

import org.example.CalculatorPage;
import org.example.HomePage;
import org.example.InsuranceInfoPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorTest extends BaseTest{
    @Test
    public void testNavigateToCalculator() {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.navigateToInsuranceInfo();

        wait.until(ExpectedConditions.titleContains("דמי ביטוח"));
        Assert.assertTrue("הכותרת לא תואמת", driver.getTitle().contains("דמי ביטוח לאומי"));

        InsuranceInfoPage infoPage = new InsuranceInfoPage(driver);
        infoPage.clickOnCalculator();

        WebElement mainHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        String headerText = mainHeader.getText();

        System.out.println("כותרת המחשבון בפועל: " + headerText);

        boolean isTitleCorrect = headerText.contains("חישוב דמי ביטוח עבור עצמאי");
        Assert.assertTrue("לא הגענו לדף המחשבון הנכון", isTitleCorrect);

        CalculatorPage calcPage = new CalculatorPage(driver);

        String randomDate = generateRandomDate();
        System.out.println("Generated Date: " + randomDate);
        calcPage.fillStepOne(randomDate);

        boolean arrivedAtStepTwo = calcPage.isStepTwoDisplayed();
        Assert.assertTrue("הטסט נכשל: לא הגענו לעמוד 'צעד שני' או שהכיתוב לא נמצא", arrivedAtStepTwo);

        calcPage.fillStepTwo();

        boolean isFinished = calcPage.isFinishPageDisplayed();
        Assert.assertTrue("הטסט נכשל: לא הגענו לעמוד תוצאות החישוב", isFinished);

        String actualAmount = getNationalInsuranceAmount("דמי ביטוח לאומי:");
        Assert.assertEquals("הסכום לתשלום שגוי", "48", actualAmount);
        actualAmount = getNationalInsuranceAmount("דמי ביטוח בריאות:");
        Assert.assertEquals("הסכום לתשלום שגוי", "123", actualAmount);
        actualAmount = getNationalInsuranceAmount("סך הכל דמי ביטוח לחודש:");
        Assert.assertEquals("הסכום לתשלום שגוי", "171", actualAmount);
    }
    public String generateRandomDate() {
        LocalDate now = LocalDate.now();

        LocalDate maxDate = now.minusYears(18).minusDays(1);
        LocalDate minDate = now.minusYears(70);

        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getNationalInsuranceAmount(String labelToSearch) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // שלב 1: המתנה שהטקסט הספציפי יופיע על המסך (ולא סתם שהדף ייטען)
            WebElement labelElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), '" + labelToSearch + "')]")
            ));

            // שלב 2: שליפת הטקסט.
            // אנו לוקחים את האלמנט שמצאנו ואת האלמנט שעוטף אותו (Parent)
            // כדי לוודא שאנחנו תופסים את המספר גם אם הוא נמצא בתגית ליד (למשל בטבלה)
            WebElement parentElement = labelElement.findElement(By.xpath("./.."));
            String fullText = parentElement.getText();

            System.out.println("השורה שנמצאה: " + fullText);

            // שלב 3: חילוץ המספר באמצעות Regex
            // התבנית מחפשת את הטקסט, נקודותיים (אופציונלי), רווחים, ואז מספר
            Pattern pattern = Pattern.compile(labelToSearch + ".*?:?\\s*(\\d+)");
            Matcher matcher = pattern.matcher(fullText);

            if (matcher.find()) {
                return matcher.group(1);
            } else {
                // ניסיון גיבוי: אם לא מצאנו בתוך ההורה, ננסה בכל זאת בתוך כל ה-Body אבל רק אחרי שווידאנו שהאלמנט קיים
                String pageText = driver.findElement(By.tagName("body")).getText();
                matcher = pattern.matcher(pageText);
                if (matcher.find()) return matcher.group(1);
            }

            return "0";

        } catch (Exception e) {
            System.out.println("שגיאה בחילוץ הסכום: " + e.getMessage());
            return "0";
        }
    }

    @Test
    public void testNavigateToCalculatorA() {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.navigateToAvtalaInfo();

        wait.until(ExpectedConditions.titleContains("אבטלה"));
        Assert.assertTrue("הכותרת לא תואמת", driver.getTitle().contains("אבטלה"));
    }
}





























