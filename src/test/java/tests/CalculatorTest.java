package tests;

import org.example.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

        String randomDate = infoPage.generateRandomDate();
        System.out.println("Generated Date: " + randomDate);
        calcPage.fillStepOne(randomDate);

        boolean arrivedAtStepTwo = calcPage.isStepTwoDisplayed();
        Assert.assertTrue("הטסט נכשל: לא הגענו לעמוד 'צעד שני' או שהכיתוב לא נמצא", arrivedAtStepTwo);

        calcPage.fillStepTwo();

        boolean isFinished = calcPage.isFinishPageDisplayed();
        Assert.assertTrue("הטסט נכשל: לא הגענו לעמוד תוצאות החישוב", isFinished);

        String actualAmount = infoPage.getNationalInsuranceAmount("דמי ביטוח לאומי:");
        Assert.assertEquals("הסכום לתשלום שגוי", "48", actualAmount);
        actualAmount = infoPage.getNationalInsuranceAmount("דמי ביטוח בריאות:");
        Assert.assertEquals("הסכום לתשלום שגוי", "123", actualAmount);
        actualAmount = infoPage.getNationalInsuranceAmount("סך הכל דמי ביטוח לחודש:");
        Assert.assertEquals("הסכום לתשלום שגוי", "171", actualAmount);
    }

    @Test
    public void testNavigateToCalculatorA() {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.navigateToAvtalaInfo();

        wait.until(ExpectedConditions.titleContains("אבטלה"));
        Assert.assertTrue("הכותרת לא תואמת", driver.getTitle().contains("אבטלה"));

        InsuranceInfoPageAvtala avtalaPage = new InsuranceInfoPageAvtala(driver);
        CalculatorPageForAvtala calcPage = avtalaPage.clickOnCalculator();

        calcPage.clickCalculator();

        wait.until(ExpectedConditions.urlContains("AvtalaCalcNew"));
        Assert.assertTrue("לא הגענו למחשבון",
                driver.getCurrentUrl().contains("AvtalaCalcNew"));

        calcPage.fillStepOne("10/01/2026");

        int[] salaries = {100,200,100,300,200,150};
        calcPage.fillStepTwo(salaries);

        boolean isFinished = calcPage.isResultsPageDisplayed();
        Assert.assertTrue("הטסט נכשל: לא הגענו לעמוד תוצאות החישוב", isFinished);

    }
}
















