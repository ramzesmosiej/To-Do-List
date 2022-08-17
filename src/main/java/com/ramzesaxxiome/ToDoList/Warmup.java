package com.ramzesaxxiome.ToDoList;

import com.ramzesaxxiome.ToDoList.model.Task;
import com.ramzesaxxiome.ToDoList.model.TaskGroup;
import com.ramzesaxxiome.ToDoList.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

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
