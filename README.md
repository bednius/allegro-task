# Allegro Task

Allegro Summer E-Xperience

Zadanie rekrutacyjne nr 3 

## Wymagania
- Maven
- Docker

## Uruchamianie

Przechodzimy do głównego katalogu (z plikiem Dockerfile) i wykonujemy kolejno:

```sh
mvn clean install
docker build --tag allegro-task .
docker run -d -p 8080:8080 allegro-task
```

## Api

Aplikacja nasłuchuje na porcie 8080

Udostępnia 2 endpointy:

1) Listowanie repozytorów danego użytkownika wraz z prostą paginacją:
- GET http://localhost:8080/{username}/repos
- GET http://localhost:8080/{username}/repos?page=5&limit=10
2) Zwraca sume gwiazdek we wszystkich repozytoriach
 - GET http://localhost:8080/{username}/stargazers/sum



## Pomysły na rozwój
- Obsługa autoryzacji użytkownika, ze względu na ograniczenia api githuba. Najprościej można ją zrealizować używając tokena Bearer, który można wygenerować w serwisie github
- Aby zmniejszyć obciążenie sieci można zaimplementować cache



