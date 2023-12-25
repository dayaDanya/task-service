package org.effective.taskservice.services;

import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.domain.models.Task;
import org.effective.taskservice.repositories.TaskRepo;
import org.effective.taskservice.util.ex.PersonNotFoundException;
import org.effective.taskservice.util.ex.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<Task> findAll() {
        return taskRepo.findAll();
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
    public List<Task> findByAuthorId(long id){
        return taskRepo.findByAuthorId(id);
    }
    public List<Task> findByPerformerId(long id){
        return taskRepo.findByPerformerId(id);
    }

}
