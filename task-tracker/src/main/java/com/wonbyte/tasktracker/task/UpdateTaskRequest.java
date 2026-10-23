package com.wonbyte.tasktracker.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The JSON the client sends to replace a task (HTTP PUT). Example:
 *   {"title": "Learn REST", "priority": "LOW", "done": true}
 *
 * PUT means "replace all of it", so every field is required.
 */
public record UpdateTaskRequest(
        @NotBlank(message = "title must not be empty")
        @Size(max = 200, message = "title must be 200 characters or less")
        String title,

        @NotNull(message = "priority is required")
        Priority priority,

        @NotNull(message = "done is required")
        Boolean done
) {
}
