package org.effective.taskservice.services.implementations;

import org.effective.taskservice.domain.models.Comment;
import org.effective.taskservice.repositories.CommentRepo;
import org.effective.taskservice.services.CommentService;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;

    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }
    @Transactional
    public void save(Comment comment){
        comment.setCreationDate(LocalDateTime.now());
        commentRepo.save(comment);
    }
    @Transactional
    public void delete(long id){
        commentRepo.deleteById(id);
    }
    public String findAuthorEmailById(long id){
        return commentRepo.findAuthorEmailById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
