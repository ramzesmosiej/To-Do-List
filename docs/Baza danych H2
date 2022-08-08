# Używanie bazy danych H2
Baza danych H2 używana jest przy tworzeniu dodatkowego profilu deweloperskiego, ponieważ może ona działać jako baza danych zapisywana w pamięci podręcznej
lub w pliku na dysku twardym. W produkcyjnych wersjach aplikacji nie używana (PostgreSQL, Oracle). Należy dodać do pom.xml poniższe zależności:
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

Posiada ona webowy interfejs który umożliwia przeglądanie danych znajdujących się w bazie i wykonywanie zapytań SQL.
