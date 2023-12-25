package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.repositories.CommentRepo;
import org.effective.taskservice.util.ex.PersonNotFoundException;
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
    public void delete(long id){
        commentRepo.deleteById(id);
    }
    public String findAuthorEmailById(long id){
        return commentRepo.findAuthorEmailById(id)
                .orElseThrow(PersonNotFoundException::new);
    }
}
