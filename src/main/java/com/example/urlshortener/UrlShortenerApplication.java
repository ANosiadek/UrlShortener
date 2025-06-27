package com.example.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Główna klasa aplikacji Spring Boot.
 * Uruchamia całą aplikację i jej kontekst Springa.
 */
@SpringBootApplication
@EnableScheduling
public class UrlShortenerApplication {
    // Uruchomienie Spring Boot
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
    // Uruchomienie puli 5 wątków dla harmonogramu zadań
    @Bean public ThreadPoolTaskScheduler taskScheduler() 
    { ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler(); scheduler.setPoolSize(5); scheduler.setThreadNamePrefix("scheduled-task-"); scheduler.initialize(); return scheduler; }
}
