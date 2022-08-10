# Often there is a conversion needed between the internal entities of application that are saved in a database and the external DTO (Data Transfer Objects) that are published back to client

### Usage in this project
We created a GrouptTaskReadModel which is TaskReadDTO used to return informations to the client. It has only description and isDone attribute
In the service there is a method that returns all the tasks using mapping to DTO
```
    public List<GroupReadModel> readAll() {
        return taskGroupRepository.findAll().stream().
                map(GroupReadModel::new).
                collect(Collectors.toList());
    }
```
We fetch all entities from database and map them into DTO using map function. Inside we use function defined in DTO that takes onle necessary informations from Entity and creates DTO object.
