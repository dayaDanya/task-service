package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Comment;

public interface CommentService {
    void save(Comment comment);

    void delete(long id);

    String findAuthorEmailById(long id);
}
