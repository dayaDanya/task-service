package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.repositories.CommentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment){
        comment.setCreationDate(LocalDateTime.now());
        commentRepo.save(comment);
    }
}
