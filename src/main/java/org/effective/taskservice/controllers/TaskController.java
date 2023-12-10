package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dayaDanya
 */
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> tasks(){
        return taskService.findAll();
    }
}
