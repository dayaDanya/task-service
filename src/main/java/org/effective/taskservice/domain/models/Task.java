package org.effective.taskservice.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @author dayaDanya
 */
//Каждая задача должна содержать
//        заголовок, описание, статус (например, "в ожидании", "в процессе", "завершено") и
//        приоритет (например, "высокий", "средний", "низкий"), а также автора задачи и
//        исполнителя. Реализовать необходимо только API.
@Entity
@Table(schema = "entities", name = "task")
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name="header")
    private String header;
    @Column(name="description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name="task_status")
    private TaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    @Column(name="task_priority")
    private TaskPriority taskPriority;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;
    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Person performer;
    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getPerformer() {
        return performer;
    }

    public void setPerformer(Person performer) {
        this.performer = performer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", author=" + author +
                ", performer=" + performer +
                ", comments=" + comments +
                ", creationDate=" + creationDate +
                '}';
    }
}
