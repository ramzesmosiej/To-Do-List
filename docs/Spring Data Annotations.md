# This file will focus on common Spring Data Annotations used in this project 
### First example wil be based on the @Entity - Task
```
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@Colmn(name = "desc")
@NotBlank(message = "Task description must be not null and not empty")
private String description;
private boolean isDone;
private LocalDateTime deadline;
@Embedded
private Audit audit = new Audit();
@ManyToOne
@JoinColumn(name = "task_group_id")
private TaskGroup group;

```
