package org.effective.taskservice.util.ex;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
