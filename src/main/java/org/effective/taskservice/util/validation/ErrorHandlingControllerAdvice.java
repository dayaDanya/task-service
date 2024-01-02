package org.effective.taskservice.util.validation;

import jakarta.validation.ConstraintViolationException;
import org.effective.taskservice.security.dto.AuthenticationResponse;
import org.effective.taskservice.util.exceptions.EmailNotUniqueException;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.effective.taskservice.util.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
    //todo зарефакторить следующие методы
    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<AuthenticationResponse> onEmailNotFoundException(
            EmailNotUniqueException e
    ) {
        System.out.println(e.getErrorMessageWithField());
        return new ResponseEntity<>(new AuthenticationResponse(e.getErrorMessageWithField()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<AuthenticationResponse> onPersonNotFoundException(
            PersonNotFoundException e
    ) {
        System.out.println(e.getErrorMessageWithField());
        return new ResponseEntity<>(new AuthenticationResponse(e.getErrorMessageWithField()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<AuthenticationResponse> onTaskNotFoundException(
            TaskNotFoundException e
    ) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new AuthenticationResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
