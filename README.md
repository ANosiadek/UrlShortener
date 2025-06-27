Przykłady użycia:

BAZA:
docker exec -it urlshortener_cassandra cqlsh urlshortener_cassandra 9042
USE urlshortener;
SELECT * FROM urls;

ZMIANA daty wpisu:
UPDATE urls SET "createdAt" = '2024-01-01 00:00:00+0000' WHERE "shortUrl" = 'AR_BqTBo';

SKRACANIE:
curl -X POST "http://localhost:8080/api/shorten?longUrl=https://example.com/badword1/test"

SPRAWDZANIE KAWKI:
docker exec -it kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic forbidden-urls --from-beginning
