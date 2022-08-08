package com.ramzesaxxiome.ToDoList.controller;

import com.ramzesaxxiome.ToDoList.model.Task;
import com.ramzesaxxiome.ToDoList.model.TaskCreationRequest;
import com.ramzesaxxiome.ToDoList.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        Task createdTask = new Task(taskCreationRequest.getDescription(), taskCreationRequest.isDone(), taskCreationRequest.getDeadline());
        repository.save(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PutMapping(path = "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> {
            task.updateFrom(toUpdate);
            repository.save(task);
        });
        return ResponseEntity.notFound().build();
    }
    @Transactional
    @PatchMapping(path = "/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/tasks")
    ResponseEntity<?> deleteAllTasks() {
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
