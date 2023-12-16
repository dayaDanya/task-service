package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dayaDanya
 */
@RestController
public class TaskController {
    private final TaskService taskService;

    private final PersonService personService;

    private final TaskMapper taskMapper;


    @Autowired
    public TaskController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
        taskMapper = Mappers.getMapper(TaskMapper.class);
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
        Person author =
                personService.findByEmail(
                        taskDto.getAuthor().getEmail())
                        .orElseThrow();

        Person performer =
                personService.findByEmail(
                                taskDto.getPerformer().getEmail())
                        .orElseThrow();
        Task task = taskMapper.dtoToObj(taskDto);
        task.setAuthor(author);
        task.setPerformer(performer);
        task.setCreationDate(LocalDateTime.now());
        taskService.save(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
