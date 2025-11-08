package org.example.randomCatFact.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CatFact(
        String fact
) implements Serializable {
}
