package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import com.example.urlshortener.service.UrlValidationService; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping({"/api"})
public class UrlShortenerController {
   private final UrlShortenerService urlShortenerService;
   private final UrlValidationService urlValidationService;

   // Inicjalizacja zależności serwisów
   public UrlShortenerController(UrlShortenerService urlShortenerService, UrlValidationService urlValidationService) {
      this.urlShortenerService = urlShortenerService;
      this.urlValidationService = urlValidationService;
   }

   // Obsługa żądań do skracania długiego URL
   @PostMapping({"/shorten"})
   public ResponseEntity<String> shortenUrl(@RequestParam String longUrl) {
      if (!urlValidationService.isUrlValid(longUrl)) //walidacja słowa
      { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL zawiera zakazane słowo!"); }
      String shortUrl = this.urlShortenerService.shortenUrl(longUrl);
      return ResponseEntity.ok(shortUrl);
   }

   // Obsługa żądań do przekierowania ze skróconego URL na oryginalny
   @GetMapping({"/{shortUrl}"})
   public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
      String longUrl = this.urlShortenerService.getOriginalUrl(shortUrl);
      return longUrl != null ? ((ResponseEntity.BodyBuilder)ResponseEntity.status(302).header("Location", new String[]{longUrl})).build() : ResponseEntity.notFound().build();
   }
}
