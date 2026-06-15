package aiselfhealing;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;

import java.util.ArrayList;
import java.util.List;

public class AIHealingAgent {
    public static List<By> suggestLocators(By failedLocator, String pageSource) {

        List<By> locators = new ArrayList<>();

        try {

            String prompt =
                    "An Appium locator failed.\n\n" +
                            "Failed locator:\n" + failedLocator + "\n\n" +
                            "Android UI XML:\n" + pageSource + "\n\n" +
                            "Suggest 3 better Appium locators using resource-id, text or accessibility id and it shouldbe unique and avoid index usage.";

            String response = GeminiService.askGemini(prompt);

            System.out.println("Gemini Response:\n" + response);

            // Example fallback locators
//            locators.add(AppiumBy.accessibilityId("Graphics"));
//            locators.add(AppiumBy.xpath("//android.widget.TextView[@text='Graphics']"));

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return locators;
    }
}
