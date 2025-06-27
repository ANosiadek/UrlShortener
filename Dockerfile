# Dockerfile dla aplikacji Spring Boot
FROM openjdk:21-jdk-slim
WORKDIR /app

# Aktualizacja pakietów i instalacja netcat
RUN apt-get update && apt-get install -y netcat-traditional && rm -rf /var/lib/apt/lists/*

# Kopiowanie plików aplikacji
COPY target/urlshortener-0.0.1.jar app.jar
COPY wait-for-cassandra.sh wait-for-cassandra.sh

# Nadanie uprawnień do wykonywania skryptu
RUN chmod +x wait-for-cassandra.sh

# Ustawienie zmiennej środowiskowej dla profilu produkcyjnego
ENV SPRING_PROFILES_ACTIVE=prod

# Uruchamianie aplikacji z opóźnieniem czekającym na Cassandrę
ENTRYPOINT ["./wait-for-cassandra.sh", "urlshortener_cassandra:9042", "java", "-jar", "app.jar"]

EXPOSE 8080