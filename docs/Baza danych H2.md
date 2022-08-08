# Używanie bazy danych H2
Baza danych H2 używana jest przy tworzeniu dodatkowego profilu deweloperskiego, ponieważ może ona działać jako baza danych zapisywana w pamięci podręcznej
lub w pliku na dysku twardym. W produkcyjnych wersjach aplikacji nie używana (PostgreSQL, Oracle). Należy dodać do pom.xml poniższe zależności:
### Spis treści:
- [Dependencies](#dependencies)
- [Konsola oparta na przeglądarce](#konsola-h2)
- [Konfiguracja połączenia](#konfiguracja-połączenia)
- [Wykorzystanie h2 w tym projekcie](#projekt)
### Dependencies
Należy dodać do pom.xml poniższe zależności:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
### Konsola H2
H2 posiada ona webowy interfejs który umożliwia przeglądanie danych znajdujących się w bazie i wykonywanie zapytań SQL.
W pliku application.properties należy dodać:
```
spring.h2.console.enabled=true
spring.h2.console.path=/console (default=/h2-console)
```
### Konfiguracja połączenia
Adres bazy danych można znaleźć w logach aplikacji podczas uruchamiania aplikacji, adres ten należy podać w konsoli aby połączyć się z bazą.
Domyślna nazwa użytkownika to sa, hasło puste. Wszystko można zmieniać za pomocą odpowiednich wpisów w application.properties
Skonfigurowałem domyślny adres bazy za pomocą wpisu:
```
jdbc:h2:mem:my_database
```
### Projekt
Spring Boot automatycznie generuje dwie relacje Groups i Tasks na podstawie obiektów @Entity (nazwa tabeli bierze się z @Table(name=[..]).
Po wykonaniu kilku zapytań POST można wyświetlić wszystkie rekordy w tabeli tasks:
ID  	CREATED_ON  	     UPDATED_ON  	    DEADLINE  	       DESC  	   IS_DONE TASK_GROUP_ID  
1	2022-08-08 13:39:59.352211	null	2020-12-23 23:59:59.999	adescription	TRUE	null
2	2022-08-08 13:40:01.330516	null	2020-12-23 23:59:59.999	adescription	TRUE	null
3	2022-08-08 13:40:02.073986	null	2020-12-23 23:59:59.999	adescription	TRUE	null
Następnie po wykonaniu zapytań PUT:
ID  	     CREATED_ON  	           UPDATED_ON  	              DEADLINE  	     DESC  	             IS_DONE  	TASK_GROUP_ID  
1	2022-08-08 13:59:34.915081	null	                    2020-12-23 23:59:59.999	adescription	        TRUE	null
2	2022-08-08 13:59:35.94143	2022-08-08 13:59:44.419067	2022-10-23 23:19:19.111	differentdescccc	    TRUE	null
3	2022-08-08 13:59:36.632958	2022-08-08 13:59:52.942063	2022-10-23 23:19:19.111	differentdescccdfdffdc	FALSE	null

Nie jest jeszcze możliwe w kontrolerze dodawaniu tasków do grup(soon) i nie ma jeszcze logiki biznesowej dotyczącej drugiej relacji groups.



