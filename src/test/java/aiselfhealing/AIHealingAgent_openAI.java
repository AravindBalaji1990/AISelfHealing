package aiselfhealing;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AIHealingAgent_openAI {
    public static List<By> suggestLocators(By failedLocator, String pageSource) {

        List<By> suggestions = new ArrayList<>();

        try {

            String prompt = """
                    A mobile test locator failed.
                    
                    Failed locator:
                    """ + failedLocator + """
                    
                    Android page source XML:
                    """ + pageSource + """
                    
                    Suggest 3 better Appium locators.
                    Return only locator values.
                    """;

            String response = OpenAIService.askGPT(prompt);

            System.out.println("AI Response: " + response);

            // Simplified example parsing
//            suggestions.add(AppiumBy.accessibilityId("Graphics"));
//            suggestions.add(AppiumBy.xpath("//android.widget.TextView[@text='Graphics']"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }
}
