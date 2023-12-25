package org.effective.taskservice.util.ex;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException() {
        super("Person with this credentials not found!");
    }
}
