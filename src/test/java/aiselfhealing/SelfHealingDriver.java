package aiselfhealing;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelfHealingDriver {
    private AndroidDriver driver;

    public SelfHealingDriver(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By locator) {

        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {

            System.out.println("Locator failed: " + locator);

            String pageSource = driver.getPageSource();

            List<By> newLocators =
                    AIHealingAgent.suggestLocators(locator, pageSource);

            for (By newLocator : newLocators) {

                try {
                    return driver.findElement(newLocator);
                } catch (Exception ignored) {
                }
            }

            throw e;
        }
    }

}
