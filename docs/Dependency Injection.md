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
