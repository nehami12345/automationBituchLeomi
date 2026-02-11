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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // הגדלנו ל-15 שניות
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("מחפש את הקישור: " + linkText);

        WebElement categoryLink;
        try {
            // ניסיון ראשון: חיפוש רגיל לפי טקסט חלקי
            categoryLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));
        } catch (TimeoutException e) {
            System.out.println("לא נמצא ב-partialLinkText, מנסה XPath...");
            // ניסיון שני: חיפוש מתקדם יותר (למשל בתוך span או div)
            categoryLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(text(), '" + linkText + "')] | //span[contains(text(), '" + linkText + "')]")
            ));
        }

        // גלילה למרכז המסך
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", categoryLink);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // לחיצה בטוחה
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
            // אופציה א': נסה למצוא את הכותרת הראשית של הדף (H1)
            // זהו האימות הכי אמין שהגענו לדף הנכון
            WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            String titleText = pageTitle.getText();
            System.out.println("נמצאה כותרת דף: " + titleText);
            return titleText;

        } catch (Exception e) {
            System.out.println("לא נמצאה כותרת H1, מנסה למצוא פירורי לחם לפי ID נפוץ...");

            try {
                // אופציה ב': חיפוש פירורי לחם לפי חלק מה-ID (נפוץ באתרי ביטוח לאומי)
                WebElement breadcrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@id, 'BreadCrumbs') or contains(@class, 'breadcrumb')]")
                ));
                return breadcrumb.getText();
            } catch (Exception ex) {
                // אופציה ג': אם כלום לא עובד, נחזיר את כותרת הדפדפן (Title tab)
                System.out.println("לא נמצא אלמנט טקסט, מחזיר את כותרת הדפדפן.");
                return driver.getTitle();
            }
        }
    }

}
