package KVServer;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KVTaskClient {
    private final String apiUrl;
    private final String apiToken;
    Gson gson = new Gson();

    public KVTaskClient(String apiUrl) {
        this.apiUrl = apiUrl;
        this.apiToken = register();
    }

    private String register() {
        try {
            URL registerUrl = new URL(apiUrl + "/register");
            HttpURLConnection connection = (HttpURLConnection) registerUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                if (scanner.hasNext()) {
                    return scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void put(String key, String json) {
        try {
            URL saveURL = new URL(apiUrl + "/save/" + key + "?API_TOKEN=" + apiToken);
            HttpURLConnection connection = (HttpURLConnection) saveURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); // Устанавливаем разрешение записи данных в запрос

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to send data to the server. Response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String load(String key){
        try {
            URL saveURL = new URL(apiUrl + "/load/" + key + "?API_TOKEN=" + apiToken);
            HttpURLConnection connection = (HttpURLConnection) saveURL.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                if (scanner.hasNext()) {
                    return gson.toJson(scanner.next().substring(("Значение для ключа " + key + " - ").length()));
                }
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getApiToken() {
        return apiToken;
    }
}