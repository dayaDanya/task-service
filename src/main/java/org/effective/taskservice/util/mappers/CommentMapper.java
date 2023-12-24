package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.CommentDto;
import org.effective.taskservice.domain.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(source = "comment.commentatorId", target = "commentatorId")
    @Mapping(source = "comment.text", target = "text")
    @Mapping(source = "comment.creationDate", target = "creationDate")
    CommentDto objToDto(Comment comment);
    @Mapping(source = "commentDto.commentatorId", target = "commentatorId")
    @Mapping(source = "commentDto.text", target = "text")
    @Mapping(source = "commentDto.creationDate", target = "creationDate")
    Comment dtoToObj(CommentDto commentDto);
}
