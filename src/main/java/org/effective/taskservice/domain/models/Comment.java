package org.effective.taskservice.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "author_email")
    private String author;

    @ManyToOne(fetch = FetchType.EAGER)
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
