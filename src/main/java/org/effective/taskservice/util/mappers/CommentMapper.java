package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {


    @Mapping(source = "commentDto.text", target = "text")
    @Mapping(source = "commentDto.author", target = "author")
    Comment dtoToObj(CommentDto commentDto);
}
