package tests;

import io.qameta.allure.Attachment;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestListener extends TestWatcher {
    private WebDriver driver;

    public TestListener(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("הטסט נכשל! מצלם מסך...");
        captureScreenshot(driver);
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] captureScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}