package org.effective.taskservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Schema(description = "Имя пользователя")
    @NotEmpty
    private String username;
    @Schema(description = "Email")
    @NotEmpty
    private String email;
    @Schema(description = "Пароль")
    @NotEmpty
    private String password;
}