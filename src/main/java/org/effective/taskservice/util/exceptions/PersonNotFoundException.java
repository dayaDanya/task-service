package org.effective.taskservice.util.exceptions;

public class PersonNotFoundException extends RuntimeException{
    private String field;
    public PersonNotFoundException(String field) {
        super("Person with this credentials not found: ");
        this.field = field;
    }

    public PersonNotFoundException(long field) {
        super("Person with this credentials not found: ");
        this.field = String.valueOf(field);
    }

    public String getErrorMessageWithField(){
        return this.getMessage() + field;
    }
}
