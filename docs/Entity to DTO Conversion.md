# Often there is a conversion needed between the internal entities of application that are saved in a database and the external DTO (Data Transfer Objects) that are published back to client

### DTO used to publish object outside
We created a GrouptTaskReadModel which is TaskReadDTO used to return informations to the client. It has only description and isDone attribute
In the service there is a method that returns all the tasks using mapping to DTO
```
    public List<GroupReadModel> readAll() {
        return taskGroupRepository.findAll().stream().
                map(GroupReadModel::new).
                collect(Collectors.toList());
    }
```
We fetch all entities from database and map them into DTO using map function. Inside we use constructor defined in DTO that takes only necessary informations from Entity and creates DTO object.
```
public GroupTaskReadModel(Task source) {
        this.description = source.getDescription();
        this.isDone = source.isDone();
    }
```
### DTO used to take info from client and write to database
Corresponding method in controller:
```
    public GroupReadModel createGroup(GroupWriteModel groupWriteModel) {
        TaskGroup result = taskGroupRepository.save(groupWriteModel.toTaskGroup());
        return new GroupReadModel(result);
    }
```
This method takes some necessary info from user and uses it to save entity in database
```
    public Task toTask() {
        return new Task(description, deadline);
    }
```
