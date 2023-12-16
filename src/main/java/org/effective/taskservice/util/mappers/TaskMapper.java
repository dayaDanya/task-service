package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.TaskDto;
import org.effective.taskservice.domain.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {
    @Mapping(source = "task.author", target = "author")
    @Mapping(source = "task.performer", target = "performer")
    TaskDto objToDto(Task task);

    @Mapping(source = "taskDto.author", target = "author")
    @Mapping(source = "taskDto.performer", target = "performer")
    Task dtoToObj(TaskDto taskDto);
}
