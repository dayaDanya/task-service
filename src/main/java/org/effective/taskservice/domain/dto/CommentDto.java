package org.effective.taskservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Входящий комментарий")
public class CommentDto {
    @Schema(description = "Текст комментария")
    @NotBlank
    private String text;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String author;

}
