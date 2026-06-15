package firstscript;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstDemoSampleIos_ExistingApp {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("ios");
        options.setAutomationName(AutomationName.IOS_XCUI_TEST);
        options.setUdid("00008110-0012088A2210401E");
//        options.setUdid("4B733329-44E4-4712-9AD2-8EC4E24CF059");

        // BUndle ID is nothing but the technical name given to the application
        // inorder to get the bundle id for a simualtor -
        // command - xcrun simctl listapps 4B733329-44E4-4712-9AD2-8EC4E24CF059
//        options.setBundleId("com.saucelabs.mydemo.app.ios");
        options.setBundleId("com.ikea.qatar.store");
        // URL will help us to runthe appium server
        IOSDriver driver = new IOSDriver(new URL("http://127.0.0.1:4723/"),options);

        Thread.sleep(4000);

        driver.quit();


    }
}
