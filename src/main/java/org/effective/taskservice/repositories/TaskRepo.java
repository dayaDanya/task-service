package org.effective.taskservice.repositories;

import org.effective.taskservice.domain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author dayaDanya
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
}
