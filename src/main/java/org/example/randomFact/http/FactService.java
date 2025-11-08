package org.example.randomFact.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

public class FactService {
    private static final Logger log = Logger.getLogger(FactService.class.getName());
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public FactService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Fact getRandomFact() throws FactHTTPException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://uselessfacts.jsph.pl/random.json?language=en"))
                .header("Accept", "applicate/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new FactHTTPException("HTTP ошибка: " + response.statusCode());
            }

            String json = response.body();
            log.info(String.format("Получен JSON %s", json));

            return objectMapper.readValue(json, Fact.class);
        } catch (Exception e) {
            log.warning(String.format("Ошибка при получении факта %s", e));
            throw new FactHTTPException("Ошибка: " + e.getMessage(), e);
        }
    }
}
