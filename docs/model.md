# Pakiet ten zawiera dwie klasy TaskRepository dziedziczące z JpaRepository i klasę Task będącą obiektem @Entity
#### Spis treści:
- [TaskRepository](#task-repository)
- [Task](#task)

#### Task Repository

Klasa ta znajduje się w pliku 'src/main/java/com/ramzesaxxiome/ToDoList/model/TaskRepository.java'
Niezbędne jest dodanie do pom.xml poniższej zależności:

```
<dependencies>
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
  </dependency>
<dependencies>
  
```
Koncept użyty tu pochodzi z modułu springa, Spring Data Rest,
dokumentację można znaleźć pod adresem `https://spring.io/projects/spring-data-rest#learn`
'''
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
'''

