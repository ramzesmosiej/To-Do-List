package com.ramzesaxxiome.ToDoList.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll();
    Optional<Task> findById(Integer id);
    Task save(Task entity);
    Page<Task> findAll(Pageable pageable);
    List<Task> findByIsDone(@Param("state") boolean done);
    boolean existsByIsDoneIsFalseAndGroupTaskGroupId(Integer groupId);

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=?1")
    boolean existsById(Integer id);
}
