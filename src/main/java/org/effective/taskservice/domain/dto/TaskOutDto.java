package org.effective.taskservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Входящая задача")
public class TaskOutDto {
    @Schema(description = "Заголовок задачи")
    private String header;
    @Schema(description = "Описание задачи")
    private String description;
    @Schema(description = "Статус задачи", example = "PENDING/PROGRESS/COMPLETED")
    private TaskStatus taskStatus;
    @Schema(description = "Приоритет выполнения задачи", example = "LOW/MIDDLE/HIGH")
    private TaskPriority taskPriority;
    @Schema(description = "Email автора задачи")
    private PersonDto author;
    @Schema(description = "Email исполнителя задачи")
    private PersonDto performer;
    @Schema(description = "Комментарии под задачей")
    private List<CommentOutDto> comments;
    @Schema(description = "Дата создания задачи")
    private LocalDateTime creationDate;

}
