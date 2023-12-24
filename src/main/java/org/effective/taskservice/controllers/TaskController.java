package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.dto.TaskOutputDto;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.ex.PersonNotFoundException;
import org.effective.taskservice.util.ex.TaskNotFoundException;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.effective.taskservice.util.mappers.TaskOutputMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    private final TaskOutputMapper taskOutputMapper;

    @Autowired
    public TaskController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
        taskOutputMapper = Mappers.getMapper(TaskOutputMapper.class);
        taskMapper = Mappers.getMapper(TaskMapper.class);
    }

    @GetMapping("/tasks")
    public List<Task> tasks() {
        return taskService.findAll();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskDto taskDto) {
        System.out.println(taskDto.toString());
        Task task = taskMapper.dtoToObj(taskDto);
        try {
            taskService.save(task);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskOutputDto> getTask(@PathVariable("id") long id) {
        try {
            Task task = taskService.findById(id);
            TaskOutputDto taskOutputDto = taskOutputMapper.objToDto(task);
            return new ResponseEntity<>(taskOutputDto, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
        try {
            taskService.findById(id);
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            System.out.println(authentication.getName());
            if (taskService.findById(id).getAuthor().getEmail().equals(authentication.getName())) {
                taskService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
