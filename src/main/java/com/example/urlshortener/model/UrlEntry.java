package com.example.urlshortener.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

// Tworzenie wpisu URL w Cassandra
@Table("urls")
public class UrlEntry {

    @PrimaryKey
    @Column("shortUrl")
    private String shortUrl;

    @Column("longUrl")
    private String longUrl;

    @Column("createdAt")
    private Date createdAt;

    @Column("lastUsedAt")
    private Date lastUsedAt;

    @Column("expiryTime")
    private Date expiryTime;

    public UrlEntry() {}

    public UrlEntry(String shortUrl, String longUrl, Date createdAt, Date lastUsedAt, Date expiryTime) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.lastUsedAt = lastUsedAt;
        this.expiryTime = expiryTime;
    }

    // Pobiera skrócony adres URL
    public String getShortUrl() {
        return shortUrl;
    }

    // Ustawia skrócony adres URL
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    // Pobiera oryginalny długi adres URL
    public String getLongUrl() {
        return longUrl;
    }

    // Ustawia oryginalny długi adres URL
    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    // Pobiera datę utworzenia wpisu
    public Date getCreatedAt() {
        return createdAt;
    }

    // Ustawia datę utworzenia wpisu
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Pobiera datę ostatniego użycia wpisu
    public Date getLastUsedAt() {
        return lastUsedAt;
    }

    // Ustawia datę ostatniego użycia wpisu
    public void setLastUsedAt(Date lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    // Pobiera datę wygaśnięcia wpisu
    public Date getExpiryTime() {
        return expiryTime;
    }

    // Ustawia datę wygaśnięcia wpisu
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}