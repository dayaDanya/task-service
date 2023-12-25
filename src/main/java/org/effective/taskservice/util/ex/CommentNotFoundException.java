package org.effective.taskservice.util.ex;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super("Comment with this id not found!");
    }
}
