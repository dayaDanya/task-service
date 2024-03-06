package org.effective.taskservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.dto.PersonDto;
import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.dto.TaskOutDto;
import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.services.CommentService;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.services.TaskService;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.effective.taskservice.util.exceptions.TaskNotFoundException;
import org.effective.taskservice.util.mappers.CommentMapper;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.effective.taskservice.util.mappers.TaskOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author dayaDanya
 */
@Tag(name = "TaskController", description = "Контроллер предоставляющий эндпоинты для взаимодействие с записями Task")
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
    public TaskController(TaskService taskService, PersonService personService, CommentService commentService, TaskMapper taskMapper, TaskOutMapper taskOutputMapper, CommentMapper commentMapper) {
        this.taskService = taskService;
        this.personService = personService;
        this.commentService = commentService;
        this.taskMapper = taskMapper;
        this.taskOutputMapper = taskOutputMapper;
        this.commentMapper = commentMapper;
    }

    @Operation(
            summary = "Получение всех записей",
            description = "Позволяет получить все записи из БД"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<Slice<TaskOutDto>> tasks(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return new ResponseEntity<>(taskService
                .findAllSlice(PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех задач по id автора",
            description = "Позволяет получить все задачи по id автора"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/author/{id}")
    public ResponseEntity<Slice<TaskOutDto>> tasksByAuthorId(
            @PathVariable("id") long id,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Slice<TaskOutDto> tasks = taskService
                .findByAuthorId(id, PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение всех задач по id исполнителя",
            description = "Позволяет получить все задачи по id исполнителя"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/performer/{id}")
    public ResponseEntity<Slice<TaskOutDto>> tasksByPerformerId(
            @PathVariable("id") long id,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Slice<TaskOutDto> tasks = taskService
                .findByPerformerId(id, PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Создание новой задачи",
            description = "Позволяет создать новую задачу"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@Valid @RequestBody TaskDto taskDto) {
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

    @Operation(
            summary = "Получение задачи по ее id",
            description = "Позволяет получить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")
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

    @Operation(
            summary = "Удаление задачи по ее id",
            description = "Позволяет удалить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")
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

    @Operation(
            summary = "Изменение задачи по ее id",
            description = "Позволяет изменить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> changeTask(@PathVariable("id") long id,
                                                 @Valid @RequestBody TaskDto taskDto) {
        try {
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            Task changedTask = taskMapper.dtoToObj(taskDto);
            if (task.getAuthor().getEmail().equals(authentication.getName())) {
                taskService.save(changedTask);
                return new ResponseEntity<>(HttpStatus.OK);
            } else if (task.getPerformer().getEmail().equals(authentication.getName())) {
                task.setTaskStatus(changedTask.getTaskStatus());
                taskService.save(task);
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(
            summary = "Добавление комментария к задаче по id задачи",
            description = "Позволяет оставить комментарий под задачей"
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id,
                                                 @Valid @RequestBody CommentDto commentDto) {
        try {
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
    @Operation(
            summary = "Удаление комментария",
            description = "Позволяет удалить комментарий под задачей"
    )
    @SecurityRequirement(name = "JWT")
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
