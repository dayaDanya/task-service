package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.repositories.CommentRepo;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepo commentRepo;
    public void save(Comment comment){
        commentRepo.save(comment);
    }
}
