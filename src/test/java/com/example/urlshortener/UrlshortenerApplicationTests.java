package com.example.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@SuppressWarnings("resource")
class UrlshortenerApplicationTests {

    @Container
    public static final CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:4.1")
            .withInitScript("init.cql");

    static {
        cassandraContainer.start(); // Uruchomienie w bloku statycznym
        System.setProperty("spring.cassandra.contact-points", cassandraContainer.getHost());
        System.setProperty("spring.cassandra.port", String.valueOf(cassandraContainer.getFirstMappedPort()));
        System.setProperty("spring.cassandra.keyspace-name", "urlshortener");
        System.setProperty("spring.cassandra.username", "cassandra");
        System.setProperty("spring.cassandra.password", "cassandra");
        System.setProperty("spring.cassandra.local-datacenter", "datacenter1");
    }

    @Test
    void contextLoads() {
    }
}