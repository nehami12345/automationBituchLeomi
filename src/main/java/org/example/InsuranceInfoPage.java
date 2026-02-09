package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InsuranceInfoPage extends BtlBasePage{

    private WebElement calculatorLink = driver.findElement(By.xpath("//a[contains(@href, 'Insurance_NotSachir')]"));
    public InsuranceInfoPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnCalculator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(calculatorLink));
        calculatorLink.click();
    }
}
