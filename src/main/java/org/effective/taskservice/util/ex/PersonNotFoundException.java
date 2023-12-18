package org.effective.taskservice.util.ex;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
