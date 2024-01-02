package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.enums.TaskStatus;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    @NotBlank
    private String header;
    @NotBlank
    private String description;
    @NotNull
    private TaskStatus taskStatus;
    @NotNull
    private TaskPriority taskPriority;
    //в json не передается. берется из authentication.
    private PersonDto author;
    @NotNull
    private PersonDto performer;



    @Override
    public String toString() {
        return "TaskDto{" +
                "header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", author=" + author +
                ", performer=" + performer +
                '}';
    }
}
