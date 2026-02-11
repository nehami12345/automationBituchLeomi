package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            WebElement labelElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), '" + labelToSearch + "')]")
            ));

            WebElement parentElement = labelElement.findElement(By.xpath("./.."));
            String fullText = parentElement.getText();

            System.out.println("השורה שנמצאה: " + fullText);

            Pattern pattern = Pattern.compile(labelToSearch + ".*?:?\\s*(\\d+)");
            Matcher matcher = pattern.matcher(fullText);

            if (matcher.find()) {
                return matcher.group(1);
            } else {
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
}
