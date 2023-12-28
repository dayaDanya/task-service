package org.effective.taskservice.repositories;

import org.effective.taskservice.domain.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dayaDanya
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    Slice<Task> findByAuthorId(long id, Pageable pageable);
    Slice<Task> findByPerformerId( long id, Pageable pageable);
    @Query(
            value = "select * from entities.task",
            nativeQuery = true)
    Slice<Task> findAllSlice(Pageable pageable);
}
