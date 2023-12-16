package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.PersonDto;
import org.effective.taskservice.domain.models.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDto objToDto(Person person);
    Person dtoToObj(PersonDto personDto);
}
