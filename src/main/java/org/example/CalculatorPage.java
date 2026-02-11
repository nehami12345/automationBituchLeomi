package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CalculatorPage extends BtlBasePage {

    private By yeshivaStudentLoc = By.xpath("//label[contains(text(), 'תלמיד ישיבה')]");
    private By maleOptionLoc = By.xpath("//label[contains(text(), 'זכר')]");
    private By birthDateInputLoc = By.cssSelector("input[id$='_Date']");
    private By continueBtnLoc = By.cssSelector("input[id$='_BirthDate_Date'] ~ input[type='submit'], input[value='המשך']");


    public CalculatorPage(WebDriver driver) {
        super(driver);
    }

    public void fillStepOne(String dateToEnter) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(yeshivaStudentLoc));
        clickOnBranchStatus(dateToEnter);
    }

    public void clickOnBranchStatus(String dateToEnter) {
        driver.findElement(yeshivaStudentLoc).click();
        driver.findElement(maleOptionLoc).click();
        enterBirthDate(dateToEnter);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueButton = wait.until(ExpectedConditions.presenceOfElementLocated(continueBtnLoc));
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", continueButton);
    }

    public void enterBirthDate(String dateToEnter) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(birthDateInputLoc));

        dateInput.click();
        dateInput.clear();
        dateInput.sendKeys(dateToEnter);

        dateInput.sendKeys(Keys.TAB);
        try {
            driver.findElement(By.tagName("body")).click();
        } catch (Exception e) { }
    }

    private By stepTwoHeader = By.xpath("//h2[contains(text(), 'צעד שני')]");
    private By stepIndicator = By.xpath("//*[contains(text(), 'שלב 2 מתוך 3')]");
    private By disabilityQuestion = By.xpath("//*[contains(text(), 'אני מקבל קצבת נכות')]");

    public boolean isStepTwoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(stepTwoHeader));
            boolean isIndicatorPresent = driver.findElement(stepIndicator).isDisplayed();
            boolean isQuestionPresent = driver.findElement(disabilityQuestion).isDisplayed();
            return isIndicatorPresent && isQuestionPresent;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private By disabilityNoLoc = By.xpath("//label[contains(@for, 'GetNechut_1') and contains(text(), 'לא')]");
    private By stepTwoContinueBtnLoc = By.cssSelector("input[value='המשך']");

    public void fillStepTwo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement noOption = wait.until(ExpectedConditions.presenceOfElementLocated(disabilityNoLoc));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", noOption);
        js.executeScript("arguments[0].click();", noOption);

        WebElement continueBtn = wait.until(ExpectedConditions.presenceOfElementLocated(stepTwoContinueBtnLoc));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", continueBtn);
        js.executeScript("arguments[0].click();", continueBtn);

    }
    public boolean isFinishPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            boolean textFound = wait.until(d -> {
                String pageText = d.findElement(By.tagName("body")).getText();
                return pageText.contains("תוצאת החישוב") || pageText.contains("סך הכל דמי ביטוח");
            });

            return textFound;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public String getValueByLabel(String labelText) {
        By locator = By.xpath("//*[contains(text(), '" + labelText + "')]/..");

        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    public String getTotalAmountText() {
        return getValueByLabel("סך הכל");
    }
}



