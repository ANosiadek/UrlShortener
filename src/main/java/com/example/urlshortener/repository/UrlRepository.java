package com.example.urlshortener.repository;

import com.example.urlshortener.model.UrlEntry;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.cassandra.repository.Query;
import java.util.Date; 
import java.util.List;

@Repository
public interface UrlRepository extends CassandraRepository<UrlEntry, String> {
    // Wyszukuje wpisy URL utworzone przed podaną datą
    @Query("SELECT * FROM urls WHERE \"createdAt\" < ?0 ALLOW FILTERING")
    List<UrlEntry> findByCreatedAtBefore(Date date);

    // Wyszukuje wpisy URL ostatnio używane przed podaną datą
    @Query("SELECT * FROM urls WHERE \"lastUsedAt\" < ?0 ALLOW FILTERING")
    List<UrlEntry> findByLastUsedAtBefore(Date date);
}