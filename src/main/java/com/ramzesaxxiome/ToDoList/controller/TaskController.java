package com.ramzesaxxiome.ToDoList.controller;

import com.ramzesaxxiome.ToDoList.model.Task;
import com.ramzesaxxiome.ToDoList.model.TaskCreationRequest;
import com.ramzesaxxiome.ToDoList.model.TaskRepository;
import com.ramzesaxxiome.ToDoList.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    private final TaskService taskService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskController(TaskRepository taskRepository, TaskService taskService, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = taskRepository;
        this.taskService = taskService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping(path="/hateoas")
    CollectionModel<EntityModel<Task>> getAllTasks() {
        List<EntityModel<Task>> allTasks = repository.findAll().stream().map(
                task -> EntityModel.of(task,
                        linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskController.class).getAllTasks()).withRel("tasks"))
        ).collect(Collectors.toList());
        return CollectionModel.of(allTasks, linkTo(methodOn(TaskController.class).
                getAllTasks()).withSelfRel());
    }
    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        return taskService.findAllAsync().thenApplyAsync(ResponseEntity::ok);
    }
    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        logger.info("Custom Pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }
    @TaskCreationExecutionTime
    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        Task createdTask = new Task(taskCreationRequest.getDescription(), taskCreationRequest.isDone(), taskCreationRequest.getDeadline());
        repository.save(createdTask);
        return ResponseEntity.created(URI.create("/" + createdTask.getId())).body(createdTask);
    }
    @PutMapping(path = "/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> {
            task.updateFrom(toUpdate);
            repository.save(task);
        });
        return ResponseEntity.noContent().build();
    }
    @Transactional
    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.noContent().build();
        }
        repository.findById(id).map(Task::toggle)
                .ifPresent(applicationEventPublisher::publishEvent);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "false") boolean state) {
        return ResponseEntity.ok(repository.findByIsDone(state));
    }
    @DeleteMapping
    ResponseEntity<?> deleteAllTasks() {
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
