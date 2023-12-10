package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.repositories.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author dayaDanya
 */
@Service
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }


    public List<Task> findAll(){
        return taskRepo.findAll();
    }
}
