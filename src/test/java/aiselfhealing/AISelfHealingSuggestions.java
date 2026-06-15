package aiselfhealing;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;

import java.net.URL;
import java.time.Duration;

public class AISelfHealingSuggestions {
    public static void main(String[] args) throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setUdid("29221JEGR00379");

        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity("io.appium.android.apis.ApiDemos");

        options.setAppWaitDuration(Duration.ofSeconds(30));
        options.setAdbExecTimeout(Duration.ofSeconds(60));

        AndroidDriver driver =
                new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

        Thread.sleep(5000);

        // Initialize self healing driver
        SelfHealingDriver healingDriver = new SelfHealingDriver(driver);

        healingDriver
                .findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Graphic']"))
                .click();

        driver.quit();
    }
}
