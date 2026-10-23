package com.wonbyte.tasktracker.task;

/**
 * We "throw" this when a task id does not exist.
 * TaskExceptionHandler catches it and turns it into a 404 response.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task " + id + " not found");
    }
}
