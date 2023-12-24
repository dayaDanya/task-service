package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.dto.TaskOutputDto;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.ex.PersonNotFoundException;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dayaDanya
 */
@RestController
@RequestMapping("/tasks")
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

    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> findTask(@PathVariable("id") long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskDto taskDto) throws PersonNotFoundException{
        System.out.println(taskDto.toString());
        Task task = taskMapper.dtoToObj(taskDto);

        taskService.save(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
