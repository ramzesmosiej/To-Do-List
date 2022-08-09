# This file will focus on common Spring Data Annotations used in this project 
### First example wil be based on the @Entity - Task
```
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
```
```
@Entity
@Table(name = "tasks")
```
Means that the class definition will correspond to a database table with a name specified in the @Table annotation.
```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
```
@Id designates that this attribute will uniquely identify the entity in the database (Primary Key)
Second annotation is used to decide how the primary key will be generated
```
@Column(name = "desc", )
```
Used to specify table details such as name, length
```
@Embedded
private Audit audit = new Audit();
```
Used to abstract out the attribute details into seperate class Audit to improve re-usability. 

Good docs are here: [@Embeddable and @Embedded Example](https://www.callicoder.com/hibernate-spring-boot-jpa-embeddable-demo/)
```
@ManyToOne
@JoinColumn(name = "task_group_id")
private TaskGroup group;
```
Many to one refers to the relationship where many different task can be in the same project but one single task can belong to unique project. So in this case the task 
has the join column which is a foreign key to the parent - Project (Meaning refers to the primary key of the parent table). In the @JoinColumn annotation we specify the name of the column.
### Next Example are few attributes from TaskGroup Entity
```
@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

```
