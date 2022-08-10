# In Spring Boot we can define our Beans and their Dependency Injection using Spring Framework
### Inversion Of Control
Inversion of Control (IoC) is a process in which an object defines its dependencies without creating them. This object delegates the job of constructing such dependencies to an IoC container.
### Example:
```
@Component
public class Company {
    ...
}
```
```
@Configuration
@ComponentScan(basePackageClasses = Company.class)
public class Config {
    @Bean
    public Address getAddress() {
        return new Address("High Street", 1000);
    }
}
```
The configuration class produces a bean of type Address. It also carries the @ComponentScan annotation, which instructs the container to look for beans in the package containing the Company class.
When a Spring IoC container constructs objects of those types, all the objects are called Spring beans, as they are managed by the IoC container.
### @Bean vs @Component
@Component auto detects and configures the beans using classpath scanning whereas @Bean explicitly declares a single bean, rather than letting Spring do it automatically.
-----------------
@Component does not decouple the declaration of the bean from the class definition where as @Bean decouples the declaration of the bean from the class definition.
------------------
@Component is a class level annotation whereas @Bean is a method level annotation and name of the method serves as the bean name.
-------------------
@Component need not to be used with the @Configuration annotation where as @Bean annotation has to be used within the class which is annotated with @Configuration.
--------------------
We cannot create a bean of a class using @Component, if the class is outside spring container whereas we can create a bean of a class using @Bean even if the class is present outside the spring container.
---------------
@Component has different specializations like @Controller, @Repository and @Service whereas @Bean has no specializations
--------------------
