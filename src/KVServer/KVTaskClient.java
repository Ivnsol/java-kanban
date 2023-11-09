package KVServer;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class KVTaskClient {
    private final String apiUrl;
    Gson gson = new Gson();

    public KVTaskClient(String apiUrl) throws IOException {
        this.apiUrl = apiUrl;
    }

    private String apiToken() throws IOException {
        URL url = new URL("http://localhost:8078/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        return body;
    }

    public void put(String key, String json) {
        try {
            System.out.println(apiToken());
            URL saveURL = new URL(apiUrl + "/save/" + key + "?API_TOKEN=" + apiToken());
            HttpURLConnection connection = (HttpURLConnection) saveURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); // Устанавливаем разрешение записи данных в запрос

            System.out.println("apiUrl: " + apiUrl);
            System.out.println("saveURL: " + saveURL);

            try (OutputStream os = connection.getOutputStream()) {
                System.out.println("Sending data: " + json);
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
            URL saveURL = new URL(apiUrl + "/load/" + key + "?API_TOKEN=" + apiToken());
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

}