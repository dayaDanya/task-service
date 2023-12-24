package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.effective.taskservice.domain.enums.TaskPriority;
import org.effective.taskservice.domain.enums.TaskStatus;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String header;

    private String description;

    private TaskStatus taskStatus;

    private TaskPriority taskPriority;
    @JsonProperty("author")
    private PersonDto author;
    @JsonProperty("performer")
    private PersonDto performer;

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

    public PersonDto getAuthor() {
        return author;
    }

    public void setAuthor(PersonDto author) {
        this.author = author;
    }

    public PersonDto getPerformer() {
        return performer;
    }

    public void setPerformer(PersonDto performer) {
        this.performer = performer;
    }

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
