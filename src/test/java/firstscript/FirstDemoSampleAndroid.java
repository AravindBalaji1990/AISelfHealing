package firstscript;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirstDemoSampleAndroid {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        // since we are using the andorid - uiautomater2options
        // uiautoamter2options is specific to andorid
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setUdid("29221JEGR00379");
//        options.setUdid("emulator-5554");

        // whether you want to install the app or invoke the app from the device
        // to instell the app to the device use the path of the apk
//        options.setApp("/Users/aravindbalaji/Documents/Appium/SampleApp/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");
        options.setAppWaitDuration(Duration.ofSeconds(30));// this will wait for the app to be found
        options.setAdbExecTimeout(Duration.ofSeconds(60));

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

        // accessibilityId,xpath - common for both andorid and ios
        // id,name, - this can be specific to andorid and ios

        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]")).sendKeys("standard_user");
        Thread.sleep(2000);
        driver.findElement(AppiumBy.accessibilityId("test-Username")).clear();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Username\")")).sendKeys("standard_user");
        Thread.sleep(2000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"test-Password\")")).sendKeys("secret_sauce");
        Thread.sleep(2000);
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        //static sleeper to wait for 5 milliseconds irrespectove of the ooutput from previous steps
        Thread.sleep(5000);

        //close the driver instance created
        driver.quit();

    }
}
