package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.enums.TaskStatus;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskOutputDto {
    private String header;

    private String description;

    private TaskStatus taskStatus;

    private TaskPriority taskPriority;
    @JsonProperty("author")
    private PersonDto author;
    @JsonProperty("performer")
    private PersonDto performer;

    private List<CommentDto> comments;

}
