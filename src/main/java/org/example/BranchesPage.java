package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BranchesPage extends BtlBasePage{

    public BranchesPage(WebDriver driver) {
        super(driver);
    }
    public BranchDetailsPage clickOnBranchName(String branchName) {
        WebElement branchLink = driver.findElement(By.partialLinkText(branchName));
        branchLink.click();
        return new BranchDetailsPage(driver);
    }
}