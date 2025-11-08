package org.example.randomCatFact.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

public class CatFactService {
    private static final Logger log = Logger.getLogger(CatFactService.class.getName());
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CatFactService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public CatFact getRandomFact() throws CatFactHTTPException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://catfact.ninja/fact"))
                .header("Accept", "applicate/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new CatFactHTTPException("HTTP ошибка: " + response.statusCode());
            }

            String json = response.body();
            log.info(String.format("Получен JSON %s", json));

            return objectMapper.readValue(json, CatFact.class);
        } catch (Exception e) {
            log.warning(String.format("Ошибка при получении факта о котах %s", e));
            throw new CatFactHTTPException("Ошибка: " + e.getMessage(), e);
        }
    }
}
