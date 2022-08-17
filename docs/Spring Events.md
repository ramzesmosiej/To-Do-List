# Spring frameworks provides the eventing mechanism which is built around the ApplicationContext.
Links that can be helpful:
https://reflectoring.io/spring-boot-application-events-explained/
https://www.tutorialspoint.com/spring/event_handling_in_spring.htm
https://www.baeldung.com/spring-events
https://www.baeldung.com/spring-context-events#3-contextstoppedevent


Agenda:
- [Existing Framework Events](#Existing-Framework-Events)
- [Listening to Multiple Events](#Listening-to-Multiple-Events)
- [Annotation driven event-listener](#Annotation-driven-event-listener)
### Existing Framework Events
Spring itself publishes a variety of events out of the box. For example, the ApplicationContext will fire various framework events: ContextRefreshedEvent, ContextStartedEvent, RequestHandledEvent etc.
These events provide application developers an option to hook into the life cycle of the application and the context and add in their own custom logic where needed.
In the course we created a custom ApplicationEventListener and overrided the method onApplicationEvent.
The class Warmup is the listener for context refreshers and should be a spring bean.
```
@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    public Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if (!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Adding it!");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(new Task("Context refreshed", null, null),
                    new Task("Context refreshed2", null, null)));
            groupRepository.save(group);
        }
    }
}
```
There are other listeners that enable to control app life cycle:\
ContextStartedEvent, ContextStoppedEvent, ContextClosedEvent
### Listening to Multiple Events
Sometimes it is required to listen to multiple events:
We make use of classes attribute and add more than one onApplicationevent method
```
@EventListener(classes = { ContextStartedEvent.class, ContextStoppedEvent.class })
public void handleMultipleEvents() {
    System.out.println("Multi-event listener invoked");
}
```
### Annotation driven event-listener
Starting with Spring 4.2, an event listener is not required to be a bean implementing the ApplicationListener interface — it can be registered on any public method of a managed bean via the @EventListener annotation\
I tried to create a mechanism that listens for task toggling.\ When the task is done, the mail to the owner of the task will be sent. I will simplify it by logging in the console which task with its description will be used to send a mail.\
I have created TaskChangedToDoneEvent class: 
```
public class TaskChangedToDoneEvent extends ApplicationEvent {
    private final Task task;

    public TaskChangedToDoneEvent(Task task, Object source) {
        super(source);
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
```
And added a patch method that makes the task done and publishes the event using ApplicationEventPublisher.
```
    @PatchMapping(path = "/toggle/{id}")
    public void toggleTask2(@PathVariable int id) {
        Task task = repository.findById(id).get();
        TaskChangedToDoneEvent taskChangedToDoneEvent = new TaskChangedToDoneEvent(task, this);
        applicationEventPublisher.publishEvent(taskChangedToDoneEvent);
    }
```
There is also a need for the listener. @EventListener adnotation is used for it.
```
@Slf4j
@Component
public class ChangedEventToDoneListener {

    @EventListener
    public void on(TaskChangedToDoneEvent toDoneEvent) {
        log.info("The mail will be prepared with the following details: description: "
        + toDoneEvent.getTask().getDescription() + "id: " + toDoneEvent.getTask().getId());
    }
}
```
We get following after posting a task and toggling it with patch method
INFO 26892 --- [nio-8081-exec-6] c.r.T.r.ChangedEventToDoneListener       : 
The mail will be prepared with the following details: description: adescriptionSHOULDLOGid: 6


