package com.example.urlshortener.service;
import com.example.urlshortener.model.UrlEntry; 
import com.example.urlshortener.repository.UrlRepository; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Service;

import java.util.Date; import java.util.List;

@Service public class UrlCleanupService {
    private static final Logger logger = LoggerFactory.getLogger(UrlCleanupService.class);

    private final UrlRepository urlRepository;

    @Value("${url.cleanup.created-ttl-days:365}") // Domyślnie 1 rok od utworzenia
    private long createdTtlDays;

    @Value("${url.cleanup.last-used-ttl-days:90}") // Domyślnie 3 miesiące od użycia
    private long lastUsedTtlDays;

    public UrlCleanupService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    //Harmonogram sprawdzający stare wpisy
    @Scheduled(cron = "${url.cleanup.cron:0 */10 * * * * *}") // Co 10 sekund
    public void cleanupOldUrls() {
        logger.info("Rozpoczynanie czyszczenia starych adresów URL...");

        // Konwersja dni na milisekundy
        Date now = new Date();
        long createdTtlMillis = createdTtlDays * 24 * 60 * 60 * 1000L;
        long lastUsedTtlMillis = lastUsedTtlDays * 24 * 60 * 60 * 1000L;

        // Usuwanie na podstawie createdAt - data utworzenia
        Date createdThreshold = new Date(now.getTime() - createdTtlMillis);
        List<UrlEntry> oldByCreated = urlRepository.findByCreatedAtBefore(createdThreshold);
        for (UrlEntry entry : oldByCreated) {
            urlRepository.delete(entry);
            logger.info("Usunięto URL o skróconej nazwie: {} (utworzonej przed: {})", entry.getShortUrl(), createdThreshold);
        }

        // Usuwanie na podstawie lastUsedAt - ostatnie użycie
        Date lastUsedThreshold = new Date(now.getTime() - lastUsedTtlMillis);
        List<UrlEntry> oldByLastUsed = urlRepository.findByLastUsedAtBefore(lastUsedThreshold);
        for (UrlEntry entry : oldByLastUsed) {
            urlRepository.delete(entry);
            logger.info("Usunięto URL o skróconej nazwie: {} (ostatnio używany przed: {})", entry.getShortUrl(), lastUsedThreshold);
        }
        // Wysyłanie informacji o czyszczeniu wpisów
        logger.info("Czyszczenie zakończone. Usunięto {} adresów na podstawie daty utworzenia oraz {} adresów na podstawie daty ostatniego użycia",
                oldByCreated.size(), oldByLastUsed.size());
    }
}
