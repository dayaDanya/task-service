package org.effective.taskservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Пользователь")
public class PersonDto {
    @Schema(description = "Email пользователя")
    private String email;


    @Override
    public String toString() {
        return "PersonDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
