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
```
Sr. No.	Key	@Bean	@Component
1	Auto detection	It is used to explicitly declare a single bean, rather than letting Spring do it automatically. 	If any class is annotated with @Component it will be automatically detect by using classpath scan.
2	Spring Container	Bean can be created even class is outside the spring container	We can’t create bean if class is outside spring container
3	Class/Method  Level Annotation	It is a method level annotation	It is a class level annotation
4	@Configuration	It works only when class is also annotated with @Configuration	It works without@Configuration annotation
5	Use Case	We should use @bean, if you want specific implementation based on dynamic condition.	We can’t write specific implementation based on dynamic condition
```
