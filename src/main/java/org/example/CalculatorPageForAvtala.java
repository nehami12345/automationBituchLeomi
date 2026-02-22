package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CalculatorPageForAvtala extends BtlBasePage {

    private static final By TITLE_LABEL = By.id("lbl_title");
    private By linkSumAvtalaLocator = By.xpath("//a[@href='/Simulators/Pages/AvtalaCalcNew.aspx']");
    private By birthDateInputLoc = By.id("ctl00_ctl43_g_2ccdbe03_122a_4c30_928f_60300c0df306_ctl00_AvtalaWizard_DynDatePicker_PiturimDate_Date");
    private By ageOver28 = By.id("ctl00_ctl43_g_2ccdbe03_122a_4c30_928f_60300c0df306_ctl00_AvtalaWizard_rdb_age_1");

    private By stepOneContinueBtnLoc = By.cssSelector("input[value='המשך']");

    public CalculatorPageForAvtala(WebDriver driver) {
        super(driver);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(TITLE_LABEL));
    }

    public void clickCalculator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement linkSumAvtala = wait.until(ExpectedConditions.elementToBeClickable(linkSumAvtalaLocator));
        linkSumAvtala.click();
    }
    public void fillStepOne(String dateToEnter) {
        enterDate(dateToEnter);
        driver.findElement(ageOver28).click();

        driver.findElement(stepOneContinueBtnLoc).click();

    }
    public void fillStepTwo(int[] arr) {
        fillAllSalaryInputs(arr);
        driver.findElement(stepOneContinueBtnLoc).click();
    }
    public boolean isResultsPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String[] requiredTexts = {
                "שכר יומי ממוצע לצורך חישוב דמי אבטלה",
                "דמי אבטלה ליום",
                "דמי אבטלה לחודש"
        };

        try {
            for (String text : requiredTexts) {
                wait.until(d -> d.getPageSource().contains(text));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public void enterDate(String dateToEnter) {
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
    public void fillAllSalaryInputs(int[] arr) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> salaryInputs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("input.txtbox_sallary.number.zeroOrMoreAchnassot")
        ));

        // מילוי כל השדות
        for (int i = 0; i < salaryInputs.size() && i < arr.length; i++) {
            WebElement input = salaryInputs.get(i);
            wait.until(ExpectedConditions.elementToBeClickable(input));
            input.click();
            input.clear();
            input.sendKeys(String.valueOf(arr[i]));
            input.sendKeys(Keys.TAB);
        }

        driver.findElement(By.tagName("body")).click();
    }
}


