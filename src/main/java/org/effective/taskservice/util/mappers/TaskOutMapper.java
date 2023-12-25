package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.TaskOutDto;
import org.effective.taskservice.domain.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper
public interface TaskOutMapper {
    @Mapping(source = "task.author", target = "author")
    @Mapping(source = "task.performer", target = "performer")
    @Mapping(source = "task.comments", target = "comments")
    @Mapping(source = "task.creationDate", target = "creationDate")
    TaskOutDto objToDto(Task task);

}
