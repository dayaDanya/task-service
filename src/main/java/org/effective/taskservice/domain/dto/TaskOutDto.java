package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskOutDto {
    private String header;

    private String description;

    private TaskStatus taskStatus;

    private TaskPriority taskPriority;

    private PersonDto author;

    private PersonDto performer;

    private List<CommentOutDto> comments;

    private LocalDateTime creationDate;

}
