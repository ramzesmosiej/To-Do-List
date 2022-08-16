# Quick description of Spring AOP
AOP is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns.
It does it by adding new aspects to the existing classes. It allows to add additional behaviour to the existing classes without modifying the code itself.
### Key Parts
✔ Core Component:\
This is the class or function you want to alter.\
✔ Aspect:\
A new logic that should be added to the existing code. Simple example could be adding log messages or a timer.\
✔ JoinPoint:\
This represents a point in your application where you can plug-in the AOP aspect.\
You can also say, it is the actual place in the application where an action will be taken using Spring AOP framework.\
✔ PointCut:\
It is a logic by which an aspect knows to intercept and decorate the JoinPoint.\
A Pointcut is a predicate that helps match an Advice to be applied by an Aspect at a particular JoinPoint.\
✔ Advice:\
This is an action taken by an aspect at a particular JoinPoint\
Different types of advice include “around,” “before,” and “after.”
### Adding my own example to to measure the amount of time to create new task
#### Creating custom annotation
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskCreationExecutionTime {
}
```
#### Creating PointCut and Advice
```
@Aspect
@Component
@Slf4j
public class TaskAspect {
    @Around("@annotation(TaskCreationExecutionTime)")
    public Object aroundTaskCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Time taken for the Execution is: " + (endTime - startTime) + "ms");
        return object;
    }
}
```
