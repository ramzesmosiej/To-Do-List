package com.ramzesaxxiome.ToDoList.controller;

import com.ramzesaxxiome.ToDoList.model.Task;
import com.ramzesaxxiome.ToDoList.model.projection.GroupReadModel;
import com.ramzesaxxiome.ToDoList.model.projection.GroupTaskReadModel;
import com.ramzesaxxiome.ToDoList.model.projection.GroupWriteModel;
import com.ramzesaxxiome.ToDoList.service.TaskGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
@IllegalExceptionProcessing
@RestController
@RequestMapping(path = "/groups")
public class TaskGroupController {
    private final TaskGroupService taskGroupService;
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);

    public TaskGroupController(TaskGroupService taskGroupService) {
        this.taskGroupService = taskGroupService;
    }

    @GetMapping()
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @PatchMapping(path = "/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable int id) {
//        try {
//            taskGroupService.toggleGroup(id);
//        }
//        catch (IllegalStateException e) {
//            return ResponseEntity.badRequest().body("Group has unclosed tasks");
//        }
//        catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel groupWriteModel) {
        GroupReadModel result = taskGroupService.createGroup(groupWriteModel);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(path="/{id}/tasks")
    ResponseEntity<List<GroupTaskReadModel>> getAllTasksFromGroup(@PathVariable int id) {
        if (!taskGroupService.groupExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(taskGroupService.getAllTasksFromGroup(id));
    }
}
