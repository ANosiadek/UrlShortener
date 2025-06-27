package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlEntry;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date; 
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;

    @Value("${url.shortener.ttl-days:30}") // Domyślny czas życia (30 dni)
    private long ttlDays;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
    // Skraca podany długi adres URL i zapisuje wpis w bazie danych
    public String shortenUrl(String longUrl) {
        String shortUrl = encodeBase62(longUrl);
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + ttlDays * 24 * 60 * 60 * 1000L); // TTL w dniach
        UrlEntry urlEntry = new UrlEntry(shortUrl, longUrl, now, now, expiryTime);
        urlRepository.save(urlEntry);
        return shortUrl; //zwraca skrócony adres
    }

    // Aktualizacja daty utworzenia i czasu życia, jeśli ponownie użyto ten sam URL
    public String getOriginalUrl(String shortUrl) {
        Optional<UrlEntry> urlEntryOpt = urlRepository.findById(shortUrl)
            .filter(entry -> entry.getExpiryTime().after(new Date()));
    urlEntryOpt.ifPresent(entry -> {
        entry.setLastUsedAt(new Date());
        urlRepository.save(entry);
    });
    return urlEntryOpt.map(UrlEntry::getLongUrl).orElse(null);
}
    // Hashowanie URL na 8 znaków ciągu
    private String encodeBase62(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return base64.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Błąd podczas generowania hasha", e);
        }
    }
}