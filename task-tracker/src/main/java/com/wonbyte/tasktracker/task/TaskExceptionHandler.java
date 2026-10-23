package com.wonbyte.tasktracker.task;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Turns exceptions into good HTTP responses.
 *
 * @RestControllerAdvice means: "this class helps all controllers".
 * When a controller throws an exception, Spring looks here for a method that handles it.
 *
 * We "extend" ResponseEntityExceptionHandler. That Spring class already turns the
 * standard errors (bad JSON, wrong method, ...) into "Problem Details" JSON.
 * We add our own error, and we make the validation error say which field is wrong.
 */
@RestControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

    /** TaskNotFoundException -> 404. Without this, the client gets a 500 ("server error"), which is wrong. */
    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail handleNotFound(TaskNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Task not found");
        return problem;
    }

    /**
     * @Valid failed -> 400, with a list of the bad fields. Example:
     *   {"title":"Bad Request","status":400,"detail":"Some fields are not valid",
     *    "errors":{"title":"title must not be empty"}}
     *
     * @Override means: we replace a method from the parent class with our own.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new TreeMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ProblemDetail problem = ex.getBody();
        problem.setDetail("Some fields are not valid");
        problem.setProperty("errors", errors);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
}
