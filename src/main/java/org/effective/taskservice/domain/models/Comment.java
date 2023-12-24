package org.effective.taskservice.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * @author dayaDanya
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "entities", name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    @JsonProperty("commentatorId")
    private long commentatorId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "text")
    @JsonProperty("text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    @JsonProperty("creationDate")
    private LocalDateTime creationDate;



}
