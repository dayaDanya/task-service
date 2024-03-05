package org.effective.taskservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Исходящий комментарий")
public class CommentOutDto {
    @Schema(description = "Идентификатор комментария")
    private long id;
    @Schema(description = "Автор комментария")
    private String author;
    @Schema(description = "Текст комментария")
    private String text;
    @Schema(description = "Дата создания")
    private LocalDateTime creationDate;
}
