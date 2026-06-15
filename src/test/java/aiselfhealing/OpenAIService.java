
package aiselfhealing;

import okhttp3.*;
import org.json.JSONObject;

public class OpenAIService {
    public static String askGPT(String prompt) throws Exception {

        String apiKey = "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";

        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4.1-nano");
        json.put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", prompt)));

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
