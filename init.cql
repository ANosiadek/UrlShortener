CREATE KEYSPACE IF NOT EXISTS urlshortener 
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS urlshortener.urls (
    "shortUrl" text PRIMARY KEY,
    "longUrl" text,
    "createdAt" timestamp,
    "lastUsedAt" timestamp,
    "expiryTime" timestamp
);