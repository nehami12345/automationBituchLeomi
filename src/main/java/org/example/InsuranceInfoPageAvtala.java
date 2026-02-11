package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsuranceInfoPageAvtala extends BtlBasePage{

    public InsuranceInfoPageAvtala(WebDriver driver) { super(driver); }

    private By calculatorLinkLocator = By.xpath("//a[@href='/Simulators/AvtCalcIndex/Pages/default.aspx']");

    public CalculatorPageForAvtala clickOnCalculator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement calculatorLink = wait.until(ExpectedConditions.elementToBeClickable(calculatorLinkLocator));
        calculatorLink.click();

       return new CalculatorPageForAvtala(driver);
    }


}