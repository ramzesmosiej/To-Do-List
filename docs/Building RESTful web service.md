### RestController
In Spring, HTTP Requests are handled by Controller. These are identified by 
@RestController annotation, which combines two annotations: @Controller and @ResponseBody(binds a method return value to the web response body)
Example from course:
```
@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(TaskRepository taskRepository) {
        this.repository = taskRepository;
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
}

```
We have routes for each HTTP operation: (@GetMapping, @PostMapping, @PutMapping and @DeleteMapping, corresponding to HTTP GET, POST, PUT, and DELETE calls)
