package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.effective.taskservice.util.exceptions.TaskNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TaskService {
    Slice<Task> findAllSlice(Pageable pageable);

    void save(Task task) throws PersonNotFoundException;

    Task findById(long id) throws TaskNotFoundException;

    void delete(long id);

    Slice<Task> findByAuthorId(long id, Pageable pageable);

    Slice<Task> findByPerformerId(long id, Pageable pageable);
}
