package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dayaDanya
 */
@RestController
public class TaskController {
    private final TaskService taskService;

    private final PersonService personService;


    @Autowired
    public TaskController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
    }

    @GetMapping("/tasks")
    public List<Task> tasks(){
        return taskService.findAll();
    }
    //json format
    // {
    // "header" : "",
    // "description" : "",
    // "taskStatus" : "",
    // "taskPriority" : "",
    // "author" : "", (здесь id)
    // "performer" : "" (здесь id)
    // }
    @PostMapping("/tasks")
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskDto taskDto){
        System.out.println(taskDto.toString());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
