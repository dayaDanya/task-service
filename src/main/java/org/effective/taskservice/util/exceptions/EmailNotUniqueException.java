package org.effective.taskservice.util.exceptions;

public class EmailNotUniqueException extends RuntimeException{
    private String field;
    public EmailNotUniqueException(String field) {
        super("Email is already used: ");
        this.field = field;
    }
    public String getErrorMessageWithField(){
        return this.getMessage() + field;
    }
}
