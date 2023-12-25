package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.dto.TaskOutputDto;
import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.CommentService;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.ex.PersonNotFoundException;
import org.effective.taskservice.util.ex.TaskNotFoundException;
import org.effective.taskservice.util.mappers.CommentMapper;
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

    private final CommentService commentService;

    private final TaskMapper taskMapper;

    private final TaskOutputMapper taskOutputMapper;

    private final CommentMapper commentMapper;

    @Autowired
    public TaskController(TaskService taskService, PersonService personService, CommentService commentService) {
        this.taskService = taskService;
        this.personService = personService;
        this.commentService = commentService;
        taskOutputMapper = Mappers.getMapper(TaskOutputMapper.class);
        taskMapper = Mappers.getMapper(TaskMapper.class);
        commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @GetMapping("/tasks")
    public List<Task> tasks() {
        return taskService.findAll();
    }

    //TODO переделать json и сделать так чтобы author брался из аутентификации
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

    //TODO добавить еще один ид
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

    @DeleteMapping("/{id}")
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

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id,
                                                 @RequestBody CommentDto commentDto) {
        try {
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            Comment comment = commentMapper.dtoToObj(commentDto);
            comment.setAuthorEmail(authentication.getName());
            comment.setTask(task);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/{cid}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id,
                                                    @PathVariable("cid") long cid) {
        try {
            taskService.findById(id);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        try {
            if (commentService.findAuthorEmailById(cid).equals(authentication.getName())) {
                commentService.delete(cid);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
