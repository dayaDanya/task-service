package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.repositories.TaskRepo;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author dayaDanya
 */
@Service
public class TaskService {
    private final TaskRepo taskRepo;

    private final TaskMapper taskMapper;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
        taskMapper = Mappers.getMapper(TaskMapper.class);
    }


    public List<Task> findAll(){
        return taskRepo.findAll();
    }
}
