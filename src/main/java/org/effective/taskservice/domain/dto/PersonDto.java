package org.effective.taskservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    @JsonProperty("username")
    private String username;


    @Override
    public String toString() {
        return "PersonDto{" +
                "username='" + username + '\'' +
                '}';
    }
}
