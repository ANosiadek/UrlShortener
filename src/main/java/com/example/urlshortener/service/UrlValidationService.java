package com.example.urlshortener.service;

import com.example.urlshortener.model.ForbiddenUrlMessage; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.kafka.core.KafkaTemplate; 
import org.springframework.stereotype.Service;

import java.time.Instant; 
import java.util.List;

@Service public class UrlValidationService {
    private static final Logger logger = LoggerFactory.getLogger(UrlValidationService.class);

private final KafkaTemplate<String, ForbiddenUrlMessage> kafkaTemplate;
private final List<String> forbiddenWords;

// Szablon Kafki
public UrlValidationService(KafkaTemplate<String, ForbiddenUrlMessage> kafkaTemplate,
                           @Value("${url.forbidden.words}") List<String> forbiddenWords) {
    this.kafkaTemplate = kafkaTemplate;
    this.forbiddenWords = forbiddenWords;
    logger.info("Załadowano zakazane słowa: {}", forbiddenWords);
}
// Sprawdzenie czy adres zawiera zakazane słowo
public boolean isUrlValid(String url) {
    if (url == null) {
        return false;
    }
    String urlLowerCase = url.toLowerCase();
    for (String forbiddenWord : forbiddenWords) {
        if (urlLowerCase.contains(forbiddenWord.toLowerCase())) {
            logger.warn("Znaleziono zakazane słowo '{}' w adresie URL: {}", forbiddenWord, url);
            sendForbiddenUrlMessage(url, forbiddenWord);
            return false;
        }
    }
    return true;
}

// Wysyłanie adresu do Kafki jeśli zawiera zakazane słowo
private void sendForbiddenUrlMessage(String url, String forbiddenWord) {
    ForbiddenUrlMessage message = new ForbiddenUrlMessage(Instant.now(), url, forbiddenWord);
    kafkaTemplate.send("forbidden-urls", message);
    logger.info("Wysłano do Kafki 'forbidden-urls': {}", message);
}
}
