# Sekcja 3 kursu wprowadza korzystanie z modułu springa Spring Data REST.
Koncept raczej nie używany w komercyjnych projektach dlatego nie będę poświęcał mu dużo uwagi
### Spis treści:
- [TaskRepository](#task-repository)
- [Task](#task)

### Task Repository

Klasa ta znajduje się w pliku 'src/main/java/com/ramzesaxxiome/ToDoList/model/TaskRepository.java' i jest to przykładowe repozytorium

Niezbędne jest dodanie do pom.xml poniższej zależności:

```
<dependencies>
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
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

Klasa z adnotacją @Entity, oznacza to że jest to tabela w relacyjnej bazie danych, w tym przypadku jest to baza danych H2 (nazwa tabeli to tasks)
```
Entity
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
Spring automatycznie tworzy nam endpoint /tasks (ponieważ Jpa repo trzyma obiekt Task) i REST API którym możemy się posługiwać i wysyłać żądania do bazy danych



