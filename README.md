# REMAKE-EduHubBE

## Obiettivo

Il progetto REMAKE-EduHubBE è un'applicazione backend per la gestione di un sistema scolastico. Consente di gestire studenti, insegnanti, corsi, lezioni, iscrizioni e valutazioni in modo centralizzato e sicuro.

## Tecnologie

- **Java 21**: Linguaggio di programmazione principale.
- **Spring Boot 3.2.5**: Framework per lo sviluppo rapido di applicazioni Java.
- **H2 Database**: Database relazionale in memoria per lo sviluppo e il testing.
- **Spring Security**: Per la gestione della sicurezza e dell'autenticazione.
- **Swagger/OpenAPI**: Per la documentazione delle API.
- **Lombok**: Per ridurre il boilerplate del codice.
- **Apache POI**: Per l'esportazione di dati in formato Excel.
- **iText**: Per la generazione di documenti PDF.
- **Apache Commons CSV**: Per l'esportazione di dati in formato CSV.

## Architettura

L'applicazione segue un'architettura a livelli sfruttando patter MVC (model-view-controller):

- **Controller**: Gestisce le richieste HTTP e invoca i servizi appropriati.
- **Service**: Contiene la logica di business.
- **Repository**: Interagisce con il database utilizzando Spring Data JPA.
- **Model**: Contiene le entità del dominio.
- **DTO**: Utilizzati per trasferire dati tra i livelli.

## Sicurezza e autenticazione

- **Spring Security**: Implementa l'autenticazione e l'autorizzazione basate su ruoli (ADMIN, TEACHER, STUDENT).
- **JWT (JSON Web Token)**: Utilizzato per l'autenticazione stateless.

## Funzionalità principali

- Gestione di studenti, insegnanti, corsi, lezioni, iscrizioni e valutazioni (per valutazioni si intende un unica valutazione di uscita tipo voto finale di uscita dal corso).
- Esportazione di dati in formato CSV, Excel e PDF (per il momento implementate solo su student).
- API documentate con Swagger/OpenAPI.
- Validazione dei dati tramite Spring Validation.
- Gestione delle eccezioni centralizzata.
- Logging dettagliato con Log4j.

## Funzionalità aggiuntive


## Testing

- **JUnit 5**: Per i test unitari e di integrazione.
- **Spring Boot Test**: Per il testing delle componenti Spring.

## Avvio del progetto

1. Clonare il repository.
2. Assicurarsi di avere Maven e Java 21 installati.
3. Eseguire il comando `mvn spring-boot:run` per avviare l'applicazione.
4. Accedere alla documentazione delle API su `http://localhost:8080/swagger-ui.html`.

## Dataset iniziale

Un dataset iniziale viene caricato all'avvio dell'applicazione tramite il metodo `CommandLineRunner` in `ScuolaApplication.java`. Questo include utenti, insegnanti, corsi, lezioni, iscrizioni e valutazioni di esempio.

## Design Pattern utilizzati

- **Singleton**: Per la gestione dei bean Spring.
- **DTO (Data Transfer Object)**: Per trasferire dati tra i livelli.

## Note finali

Ultimo progetto solo back end del corso di sviluppo software alla infobasic
