package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.effective.taskservice.domain.models.Task;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @JsonProperty("commentatorId")
    private long commentatorId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("creationDate")
    private LocalDateTime creationDate;
}
