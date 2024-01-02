package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.repositories.TaskRepo;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.effective.taskservice.util.exceptions.TaskNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author dayaDanya
 */
@Service
public class TaskService {
    private final TaskRepo taskRepo;

    private final PersonService personService;

    public TaskService(TaskRepo taskRepo, PersonService personService) {
        this.taskRepo = taskRepo;

        this.personService = personService;
    }

    public Slice<Task> findAllSlice(Pageable pageable) {
        return taskRepo.findAllSlice(pageable);
    }

    public void save(Task task) throws PersonNotFoundException {
        Person author =
                personService.findByEmail(
                        task.getAuthor().getEmail());
        Person performer =
                personService.findByEmail(
                        task.getPerformer().getEmail());

        task.setAuthor(author);
        task.setPerformer(performer);
        task.setCreationDate(LocalDateTime.now());
        System.out.println(task.getPerformer().getEmail());
        taskRepo.save(task);
    }

    public Task findById(long id) throws TaskNotFoundException {
        return taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public void delete(long id) {
        taskRepo.deleteById(id);
    }

    //TODO проверить исключения
    public Slice<Task> findByAuthorId(long id, Pageable pageable) {
        return taskRepo.findByAuthorId(id, pageable);
    }

    public Slice<Task> findByPerformerId(long id, Pageable pageable) {
        return taskRepo.findByPerformerId(id, pageable);
    }

}
