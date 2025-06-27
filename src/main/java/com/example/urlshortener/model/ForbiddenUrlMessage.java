package com.example.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonFormat; 
import java.time.Instant;

public class ForbiddenUrlMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    private Instant timestamp; 
    private String url; 
    private String forbiddenWord;

// Inicjalizacja
public ForbiddenUrlMessage(Instant timestamp, String url, String forbiddenWord) {
    this.timestamp = timestamp;
    this.url = url;
    this.forbiddenWord = forbiddenWord;
}

// Pobiera czas wykrycia zakazanego adresu URL
public Instant getTimestamp() {
    return timestamp;
}

// Ustawia czas dla wiadomości o zakazanym adresie URL
public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
}

// Pobiera adres URL zawierający zakazane słowo
public String getUrl() {
    return url;
}

// Ustawia adres URL
public void setUrl(String url) {
    this.url = url;
}

// Pobiera zakazane słowo znalezione w adresie URL
public String getForbiddenWord() {
    return forbiddenWord;
}

// Ustawia zakazane słowo w Kafce
public void setForbiddenWord(String forbiddenWord) {
    this.forbiddenWord = forbiddenWord;
}
}
