package com.ramzesaxxiome.ToDoList.model;


import com.ramzesaxxiome.ToDoList.model.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;


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
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    Task() {
    }

    public Task(String description, boolean isDone, LocalDateTime deadline) {
        this.description = description;
        this.isDone = isDone;
        this.deadline = deadline;
    }

    public Task(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String description, LocalDateTime deadline, TaskGroup taskGroup) {
        this.description = description;
        this.deadline = deadline;
        if (taskGroup != null) {
            this.group = taskGroup;
        }
    }


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public TaskEvent toggle() {
        this.isDone = !this.isDone;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(Task source) {
        this.description = source.description;
        this.isDone = source.isDone;
        this.deadline = source.deadline;
    }
}

