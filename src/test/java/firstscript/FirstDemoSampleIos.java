package firstscript;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FirstDemoSampleIos {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("ios");
        options.setAutomationName(AutomationName.IOS_XCUI_TEST);
        options.setUdid("4B733329-44E4-4712-9AD2-8EC4E24CF059");
        options.setCapability("unicodeKeyboard", true);
        options.setCapability("resetKeyboard", true);
        options.setCapability("hideKeyboard", true);
//        options.setApp(System.getProperty("user.dir")+"/src/test/resources/ios/My_Demo_App_IOS_Simulator.zip");

        options.setBundleId("com.saucelabs.mydemo.app.ios");
        // URL will help us to runthe appium server
        IOSDriver driver = new IOSDriver(new URL("http://127.0.0.1:4723/"), options);

        Thread.sleep(4000);

        driver.findElement(AppiumBy.xpath("//XCUIElementTypeImage[@name=\"Menu Icons\"]")).click();
        Thread.sleep(4000);
        driver.findElement(AppiumBy.iOSNsPredicateString("name == \"LogOut-menu-item\"")).click();
        Thread.sleep(2000);

//        List<WebElement> textfields  =driver.findElements(AppiumBy.xpath("//XCUIElementTypeStaticText[@value='Password']/preceeding-sibling::XCUIElementTypeOther"));
//
//        for(int i =0 ; i<textfields.size();i++){
//            if(i ==0 ) {
//                textfields.get(0).click();
//                textfields.get(0).sendKeys("bob@example.com");
//            }
//            if (i == 1){
//                textfields.get(1).click();
//                textfields.get(1).sendKeys("10203040");
//            }
//        }

        driver.findElement(AppiumBy.xpath("//XCUIElementTypeApplication[@name=\"My Demo App\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]")).sendKeys("bob@example.com");
        driver.findElement(AppiumBy.xpath("//XCUIElementTypeApplication[@name=\"My Demo App\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]")).sendKeys("10203040");

        Thread.sleep(2000);

        // this will help us to hide the native keyboard that will pop up
//        driver.hideKeyboard();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name=\"Login\"]")).click();

        driver.quit();


    }
}
