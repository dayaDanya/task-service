package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private String email;


    @Override
    public String toString() {
        return "PersonDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
