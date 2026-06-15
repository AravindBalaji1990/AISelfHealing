package aiselfhealing;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelfHealingDriver_old {

    private AndroidDriver driver;

    public SelfHealingDriver_old(AndroidDriver driver){
        this.driver = driver;
    }

    public WebElement findElement(By locator){

        try{
            return driver.findElement(locator);
        }

        catch(NoSuchElementException e){

            System.out.println("Locator failed: " + locator);

            String pageSource = driver.getPageSource();

            List<By> suggestions =
                    AIHealingAgent_old.suggestLocators(locator.toString(), pageSource);

            for(By newLocator : suggestions){

                try{
                    WebElement element = driver.findElement(newLocator);

                    System.out.println("Self healed using locator: " + newLocator);

                    return element;
                }

                catch(Exception ignored){}
            }

            throw e;
        }
    }
}
