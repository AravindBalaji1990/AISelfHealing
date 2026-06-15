package aiselfhealing;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class GeminiService {
    public static String askGemini(String prompt) throws Exception {

        String apiKey = "AIzaSyBIUOlNOOJp8rzPZa7KDYqoJC5U08hZTI8";//System.getenv("GEMINI_API_KEY");


        // initialisation
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS) // Extends deadline waiting for Gemini response
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        JSONObject part = new JSONObject();
        part.put("text", prompt);

        JSONArray parts = new JSONArray();
        parts.put(part);

        JSONObject content = new JSONObject();
        content.put("parts", parts);

        JSONArray contents = new JSONArray();
        contents.put(content);

        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("contents", contents);

        RequestBody body = RequestBody.create(
                requestBodyJson.toString(),
                MediaType.parse("application/json")
        );

        // Using the current standard Gemini 3 Flash model
        String modelName = "gemini-2.5-flash";
        String apiVersion = "v1beta"; // Or "v1" for the fully stable production release

        Request request = new Request.Builder()
//        https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b:generateContent?key=YOUR_API_KEY
                .url("https://generativelanguage.googleapis.com/" + apiVersion + "/models/" + modelName + ":generateContent?key=" + apiKey)
                .header("Content-Type", "application/json") // Ensure this header is present
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string(); // Get the raw string from OkHttp
        JSONObject obj = new JSONObject(jsonResponse);

// Drill down to the text
//        String geminiText = obj.getJSONArray("candidates")
//                .getJSONObject(0)
//                .getJSONObject("content")
//                .getJSONArray("parts")
//                .getJSONObject(0)
//                .getString("text");

        System.out.println("--- Gemini Output ---");
        System.out.println(jsonResponse);

//        return response.body().string();
        return jsonResponse;
    }
}
