package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BranchDetailsPage extends BtlBasePage{

    public BranchDetailsPage(WebDriver driver) {
        super(driver);
    }

    private WebElement addressInfo = driver.findElement(By.xpath("//*[contains(text(), 'כתובת')]"));
    private WebElement receptionInfo = driver.findElement(By.xpath("//*[contains(text(), 'קבלת קהל')]"));
    private WebElement phoneInfo = driver.findElement(By.xpath("//*[contains(text(), 'מענה טלפוני')]"));

    public boolean isAddressDisplayed() {
        addressInfo.findElement(By.xpath("//*[contains(text(), 'כתובת')]"));
        return addressInfo.isDisplayed();
    }
    public boolean isReceptionInfoDisplayed() {
        receptionInfo.findElement(By.xpath("//*[contains(text(), 'קבלת קהל')]"));
        return receptionInfo.isDisplayed();
    }
    public boolean isPhoneInfoDisplayed() {
        phoneInfo.findElement(By.xpath("//*[contains(text(), 'מענה טלפוני')]"));
        return phoneInfo.isDisplayed();
    }
}
