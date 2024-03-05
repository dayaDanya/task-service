package org.effective.taskservice.security.dto;


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
@Schema(description = "Данные пользователя")
public class AuthenticationRequest {
    @Schema(description = "Email")
    @NotEmpty
    private String email;
    @Schema(description = "Пароль")
    @NotEmpty
    String password;
}