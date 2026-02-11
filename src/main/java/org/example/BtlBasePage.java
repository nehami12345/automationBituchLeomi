package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BtlBasePage extends BasePage{

    public BtlBasePage(WebDriver driver){
        super(driver);
    }

    public void clickMainMenuItem(BtlMenuCategory category){
       String menuText = category.getMenuTitle();
        WebElement mainMenuItem = driver.findElement(By.linkText(menuText));
        mainMenuItem.click();
    }
    public void clickSubMenuItem(String subMenuText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mainMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(subMenuText)));
        mainMenuItem.click();
    }

    protected WebElement searchInput = driver.findElement(By.id("TopQuestions"));
    public void performSearch(String textToSearch) {
        searchInput.clear();
        searchInput.sendKeys(textToSearch);
        WebElement searchButton = driver.findElement(By.id("ctl00_SiteHeader_reserve_btnSearch"));
        searchButton.click();
    }
    protected WebElement branchesLink = driver.findElement(By.linkText("סניפים"));
    public BranchesPage goToBranchesPage() {
        branchesLink.click();
        return new BranchesPage(driver);
    }

    public void navigateToInsuranceInfo(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement insuranceLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("דמי ביטוח")));
        insuranceLink.click();

        WebElement subCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("דמי ביטוח לאומי")));
        subCategoryLink.click();
    }

    public void navigateToAvtalaInfo(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement insuranceLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("קצבאות והטבות")));
        insuranceLink.click();

        WebElement subCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("אבטלה")));
        subCategoryLink.click();
    }
}