package firstscript;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirstDemoSampleAndroid_ExistingApp {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        // since we are using the andorid - uiautomater2options
        // uiautoamter2options is specific to andorid
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setUdid("29221JEGR00379");
//        options.setUdid("emulator-5554");

        // for the android application we need to get App Package name and App Activity name
        options.setAppPackage("io.appium.android.apis");
//        options.setAppPackage("com.alaneesqatar.qa");
//        options.setAppActivity("com.web2native.SplashActivity");
//        options.setAppActivity("com.web2native.MainActivity");
        options.setAppActivity("io.appium.android.apis.app.TranslucentActivity");

        options.setAppWaitDuration(Duration.ofSeconds(30));// this will wait for the app to be found
        options.setAdbExecTimeout(Duration.ofSeconds(60));

        AndroidDriver driver =new AndroidDriver(new URL("http://127.0.0.1:4723/"),options);

        //static sleeper to wait for 5 milliseconds irrespectove of the ooutput from previous steps
        Thread.sleep(5000);

        //close the driver instance created
        driver.quit();

    }
}
