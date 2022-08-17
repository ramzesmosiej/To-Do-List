# Spring frameworks provides the eventing mechanism which is built around the ApplicationContext.
Agenda:
- [Existing Framework Events](#Existing-Framework-Events)
- [Listening to Multiple Events](#Listening-to-Multiple Events)
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
Starting with Spring 4.2, an event listener is not required to be a bean implementing the ApplicationListener interface — it can be registered on any public method of a managed bean via the @EventListener annotation:
