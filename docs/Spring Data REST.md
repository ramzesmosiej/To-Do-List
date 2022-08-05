# Sekcja 3 kursu wprowadza korzystanie z modułu springa Spring Data REST i używanie go do zbudowanie prostego REST API.
Koncept raczej nie używany w komercyjnych projektach dlatego nie będę poświęcał mu dużo uwagi.
### Spis treści:
- [TaskRepository](#task-repository)
- [Task](#task)
- [API-Reference](#api-reference)

### Task Repository

Klasa ta znajduje się w pliku 'src/main/java/com/ramzesaxxiome/ToDoList/model/TaskRepository.java' i jest to przykładowe repozytorium.

Niezbędne jest dodanie do pom.xml poniższych zależności:

```
<dependencies>
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
  </dependency>
  <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>
<dependencies>
  
```
Koncept użyty tu pochodzi z modułu springa, Spring Data Rest, używanego do wywoływania metod http na repository bez konieczności pisania oddzielnego controllera ((adnotacja @RepositoryRestResource ))
dokumentację można znaleźć pod adresem [https://spring.io/projects/spring-data-rest#learn](https://spring.io/projects/spring-data-rest#learn).
```
@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Override
    @RestResource(exported = false)
    void delete(Task task);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @RestResource(path = "done", rel = "done")
    List<Task> findByIsDone(@Param("state") boolean done);
}
```
#### Task

Klasa z adnotacją @Entity, oznacza to że jest to tabela w relacyjnej bazie danych, w tym przypadku jest to baza danych H2 (nazwa tabeli to tasks).
Każdy task ma automatycznie wygenerowane ID, opis, a także boolean isDone.
```
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "desc")
    @NotBlank(message = "Task description must be not null and not empty")
    private String description;
    private boolean isDone;

    Task() {
    }
```
Spring automatycznie tworzy nam endpoint /tasks (ponieważ Jpa repo trzyma obiekt Task) i REST API którym możemy się posługiwać i wysyłać żądania do bazy danych.


## API Reference

#### Get all tasks

```http
  GET /tasks
```
```
Postman response:
{
    "_embedded": {
        "tasks": []
    },
    "_links": {
        "self": {
            "href": "http://localhost:8081/tasks"
        },
        "profile": {
            "href": "http://localhost:8081/profile/tasks"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 0,
        "totalPages": 0,
        "number": 0
    }
}
```
W odpowiedzi widzimy JSONa który zawiera listę
wszystkich tasków (aktualnie zero), a także inne URL
dostępne dla tego resource.

Po dodaniu do repository sygnatury metody dostępnej w JpaRepository
przykładowo:
```
List<Task> findByIsDone(@Param("state") boolean isDone);
```

możemy wybierać taski które są zrobione lub nie.

```http
  GET /tasks/search/findByIsDone?state=false
```
Można również używać parametrów sort, page,
dostosowywać endpointy, szczegóły w poniższych linkach:
[https://www.baeldung.com/spring-data-rest-intro]
[https://progressivecoder.com/exposing-repositories-as-rest-resources-using-spring-boot/]



