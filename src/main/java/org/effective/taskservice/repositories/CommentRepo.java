package org.effective.taskservice.repositories;

import org.effective.taskservice.domain.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author dayaDanya
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
