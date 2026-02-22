package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class navigateFromKitbaut extends BtlBasePage{
    WebDriverWait wait;

    public navigateFromKitbaut(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void navigateToKitbaut(){
        WebElement mainLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("קצבאות והטבות")));
        mainLink.click();
    }
    public void clickOnSubCategory(String linkText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("מחפש את הקישור: " + linkText);

        WebElement categoryLink;
        try {
            categoryLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));
        } catch (TimeoutException e) {
            System.out.println("לא נמצא ב-partialLinkText, מנסה XPath...");
            categoryLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(text(), '" + linkText + "')] | //span[contains(text(), '" + linkText + "')]")
            ));
        }

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", categoryLink);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        try {
            wait.until(ExpectedConditions.elementToBeClickable(categoryLink));
            categoryLink.click();
        } catch (Exception e) {
            System.out.println("לחיצה רגילה נכשלה, לוחץ עם JS");
            js.executeScript("arguments[0].click();", categoryLink);
        }
    }


    public String getBreadcrumbsText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            String titleText = pageTitle.getText();
            System.out.println("נמצאה כותרת דף: " + titleText);
            return titleText;

        } catch (Exception e) {
            System.out.println("לא נמצאה כותרת H1, מנסה למצוא פירורי לחם לפי ID נפוץ...");

            try {
                WebElement breadcrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@id, 'BreadCrumbs') or contains(@class, 'breadcrumb')]")
                ));
                return breadcrumb.getText();
            } catch (Exception ex) {
                System.out.println("לא נמצא אלמנט טקסט, מחזיר את כותרת הדפדפן.");
                return driver.getTitle();
            }
        }
    }

}
