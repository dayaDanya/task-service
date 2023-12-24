package org.effective.taskservice.util.ex;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("Task with this id not found!");
    }
}
