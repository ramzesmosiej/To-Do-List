package com.ramzesaxxiome.ToDoList.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int taskGroupId;
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    private boolean isDone;
    @Embedded
    private Audit audit = new Audit();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;



    public TaskGroup() {
    }

    public int getId() {
        return taskGroupId;
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

    public void setDone(boolean done) {
        isDone = done;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
