package org.effective.taskservice.repositories;

import org.effective.taskservice.domain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dayaDanya
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByAuthorId(long id);
    List<Task> findByPerformerId(long id);
}
