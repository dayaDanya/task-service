package org.effective.taskservice.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 * @author dayaDanya
 */
@Entity
@Table(schema = "entities", name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "commentator_id")
    private Person commentator;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Comment(Person commentator, Task task, String text, LocalDateTime creationDate) {

        this.commentator = commentator;
        this.task = task;
        this.text = text;
        this.creationDate = creationDate;
    }


}
