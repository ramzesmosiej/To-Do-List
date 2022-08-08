# How to build RESTful web service in Spring Boot
### Agneda:
- [RestController](#rest-controller)
- [Task](#task)
- [API-Reference](#api-reference)


### RestController
In Spring, HTTP Requests are handled by Controller. These are identified by 
@RestController annotation, which combines two annotations: @Controller and @ResponseBody(binds a method return value to the web response body)
Example from course with two my methods added:
```
@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    @GetMapping(path="tasks/hateoas")
    CollectionModel<EntityModel<Task>> getAllTasks() {
        List<EntityModel<Task>> allTasks = repository.findAll().stream().map(
                task -> EntityModel.of(task,
                        linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskController.class).getAllTasks()).withRel("tasks"))
        ).collect(Collectors.toList());
        return CollectionModel.of(allTasks, linkTo(methodOn(TaskController.class).
                getAllTasks()).withSelfRel());
    }
    @GetMapping(path = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping(path = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        logger.info("Custom Pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());
    }
    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }
    @PostMapping(path = "/tasks")
    ResponseEntity<Task> createTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        Task createdTask = new Task(taskCreationRequest.getDescription(), taskCreationRequest.isDone());
        repository.save(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PutMapping(path = "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.ok(toUpdate);
    }
    @DeleteMapping(path = "/tasks")
    ResponseEntity<?> deleteAllTasks() {
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

```
We have routes for each HTTP operation: (@GetMapping, @PostMapping, @PutMapping and @DeleteMapping, corresponding to HTTP GET, POST, PUT, and DELETE calls)
All the endpoints could be tested with Postman
### Spring HATEOAS
According to spring documentation a crucial aspect of building RESTful Api is adding links to relevant operations meaning adding relevant links to JSON Response
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```
I have written an example in addition to the code from this udemy course, adding another get requests using hateoas
official docs showing this feature: [https://spring.io/guides/tutorials/rest/](https://spring.io/guides/tutorials/rest/)
```
    @GetMapping(path="tasks/hateoas")
    CollectionModel<EntityModel<Task>> getAllTasks() {
        List<EntityModel<Task>> allTasks = repository.findAll().stream().map(
                task -> EntityModel.of(task, 
                        linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskController.class).getAllTasks()).withRel("tasks"))
        ).collect(Collectors.toList());
        return CollectionModel.of(allTasks, linkTo(methodOn(TaskController.class).
                getAllTasks()).withSelfRel());
    }
 ```
 ```http
  GET /tasks/hateoas
```
 Response from postman after adding two tasks before:
 ```
 {
    "_embedded": {
        "tasks": [
            {
                "description": "adescription",
                "done": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/tasks/1"
                    },
                    "tasks": {
                        "href": "http://localhost:8081/tasks/hateoas"
                    }
                }
            },
            {
                "description": "adescription",
                "done": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/tasks/2"
                    },
                    "tasks": {
                        "href": "http://localhost:8081/tasks/hateoas"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8081/tasks/hateoas"
        }
    }
}
```
 
