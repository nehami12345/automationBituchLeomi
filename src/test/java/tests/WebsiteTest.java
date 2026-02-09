package tests;

import org.example.BranchDetailsPage;
import org.example.BranchesPage;
import org.junit.Assert;
import org.example.HomePage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebsiteTest extends BaseTest{
    //(description = "בדיקת חיפוש באתר ביטוח לאומי")
    @Test
    public void testSearchFunctionality() {
        HomePage homePage = new HomePage(driver);
        String searchText = "חישוב סכום דמי לידה ליום";

        homePage.performSearch(searchText);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

        WebElement body = driver.findElement(By.tagName("body"));
        String pageText = body.getText();

        System.out.println("Page content found: " + pageText.substring(0, 100) + "...");

        boolean isTextFound = pageText.contains("תוצאות") || pageText.contains(searchText);
        Assert.assertTrue("החיפוש נכשל - הטקסט לא נמצא בגוף הדף", isTextFound);
    }
    @Test
    public void testNavigateToBranches() {
        HomePage homePage = new HomePage(driver);
        BranchesPage branchesPage = homePage.goToBranchesPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

        WebElement body = driver.findElement(By.tagName("body"));
        String pageText = body.getText();

        System.out.println("Page content found: " + pageText.substring(0, 100) + "...");

        boolean isTitleCorrect = pageText.contains("תוצאות") || pageText.contains("סניפים וערוצי שירות");
        Assert.assertTrue("המעבר נכשל - לא הגענו לדף סניפים", isTitleCorrect);
    }
    @Test
    public void testBranchDetails() {
        HomePage homePage = new HomePage(driver);
        BranchesPage branchesPage = homePage.goToBranchesPage();

        BranchDetailsPage detailsPage = branchesPage.clickOnBranchName("אשדוד");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'כתובת')]")));

        boolean addressExists = detailsPage.isAddressDisplayed();
        boolean receptionExists = detailsPage.isReceptionInfoDisplayed();
        boolean phoneExists = detailsPage.isPhoneInfoDisplayed();

        System.out.println("Address found: " + addressExists);
        System.out.println("Reception found: " + receptionExists);
        System.out.println("Phone found: " + phoneExists);

        Assert.assertTrue("לא נמצא מידע על כתובת", addressExists);
        Assert.assertTrue("לא נמצא מידע על קבלת קהל", receptionExists);
        Assert.assertTrue("לא נמצא מידע על מענה טלפוני", phoneExists);
    }
}

