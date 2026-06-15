package firstscript;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class FirstDemoSampleIos_reminderApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("ios");
        options.setAutomationName(AutomationName.IOS_XCUI_TEST);
        options.setUdid("4B733329-44E4-4712-9AD2-8EC4E24CF059");
//        options.setCapability("unicodeKeyboard", true);
//        options.setCapability("resetKeyboard", true);
//        options.setCapability("hideKeyboard", true);
//        options.setApp(System.getProperty("user.dir")+"/src/test/resources/ios/My_Demo_App_IOS_Simulator.zip");

        options.setBundleId("com.apple.reminders");
        // URL will help us to runthe appium server
        IOSDriver driver = new IOSDriver(new URL("http://127.0.0.1:4723/"), options);

        Thread.sleep(4000);

        driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name=\"New Reminder\"]")).click();
        Thread.sleep(2000);
        // this will help us to hide the native keyboard that will pop up
//        driver.hideKeyboard();
//        driver.executeScript("mobile: activateApp", Map.of("bundleId","com.apple.reminders"))
        Runtime.getRuntime().exec("defaults write com.apple.iphonesimulator ConnectHardwareKeyboard -bool false");
        Runtime.getRuntime().exec("killall Simulator");

        driver.findElement(AppiumBy.xpath("//XCUIElementTypeTextField[@name=\"Quick Entry Title Field\"]")).sendKeys("test1");


        Thread.sleep(2000);


        driver.quit();


    }
}
