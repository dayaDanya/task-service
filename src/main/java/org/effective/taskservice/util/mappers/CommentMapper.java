package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.text", target = "text")
    CommentDto objToDto(Comment comment);
    @Mapping(source = "commentDto.id", target = "id")
    @Mapping(source = "commentDto.text", target = "text")
    Comment dtoToObj(CommentDto commentDto);
}
