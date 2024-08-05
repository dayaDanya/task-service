package org.effective.taskservice.util.mappers;

import org.effective.taskservice.domain.dto.CommentOutDto;
import org.effective.taskservice.domain.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentOutMapper {
    @Mapping(source = "comment.text", target = "text")
    @Mapping(source = "comment.author", target = "author")
    @Mapping(source = "comment.creationDate", target = "creationDate")
    CommentOutDto objToDto(Comment comment);

}
