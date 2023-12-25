package org.effective.taskservice.controllers;

import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.dto.PersonDto;
import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.dto.TaskOutDto;
import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.CommentService;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.ex.PersonNotFoundException;
import org.effective.taskservice.util.ex.TaskNotFoundException;
import org.effective.taskservice.util.mappers.CommentMapper;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.effective.taskservice.util.mappers.TaskOutMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    private final TaskOutMapper taskOutputMapper;

    private final CommentMapper commentMapper;

    @Autowired
    public TaskController(TaskService taskService, PersonService personService, CommentService commentService) {
        this.taskService = taskService;
        this.personService = personService;
        this.commentService = commentService;
        taskOutputMapper = Mappers.getMapper(TaskOutMapper.class);
        taskMapper = Mappers.getMapper(TaskMapper.class);
        commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @GetMapping()
    public List<TaskOutDto> tasks() {
        return taskService.findAll()
                .stream()
                .map(taskOutputMapper::objToDto)
                .collect(Collectors.toList());
    }
    //todo продумать другие варианты поиска
    @GetMapping("/author/{id}")
    public List<TaskOutDto> tasksByAuthorId(@PathVariable("id") long id){
        return taskService.findByAuthorId(id)
                .stream()
                .map(taskOutputMapper::objToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/performer/{id}")
    public List<TaskOutDto> tasksByPerformerId(@PathVariable("id") long id){
        return taskService.findByPerformerId(id)
                .stream()
                .map(taskOutputMapper::objToDto)
                .collect(Collectors.toList());
    }

    //здесь в dto есть author, однако мы его игнорируем и берем из аутентификации
    @PostMapping()
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskDto taskDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //so-so
        taskDto.setAuthor(new PersonDto(authentication.getName()));
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
    public ResponseEntity<TaskOutDto> getTask(@PathVariable("id") long id) {
        try {
            Task task = taskService.findById(id);
            TaskOutDto taskOutputDto = taskOutputMapper.objToDto(task);
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
    public ResponseEntity<HttpStatus> changeTask(@PathVariable("id") long id,
                                                   @RequestBody TaskDto taskDto) {
        try {
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            Task changedTask = taskMapper.dtoToObj(taskDto);
            if (task.getAuthor().getEmail().equals(authentication.getName())) {
                taskService.save(changedTask);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else if(task.getPerformer().getEmail().equals(authentication.getName())){
                task.setTaskStatus(changedTask.getTaskStatus());
                taskService.save(task);
                return new ResponseEntity<>(HttpStatus.OK);
            }
                else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //todo добавить валидацию(проверку на правильность входящих dto)
    @PatchMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id,
                                                 @RequestBody CommentDto commentDto) {
        try {
            System.out.println("\nмы там где NE надо\n");
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            Comment comment = commentMapper.dtoToObj(commentDto);
            comment.setAuthor(authentication.getName());
            System.out.println(comment.getAuthor() + "\n");
            comment.setTask(task);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO узнать как делать dto в таком случае, мб чекнуть алишева
    @DeleteMapping("/{id}/comments/{cid}")
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
