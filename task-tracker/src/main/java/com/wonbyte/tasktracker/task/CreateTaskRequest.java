package com.wonbyte.tasktracker.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The JSON the client sends to make a task. Example:
 *   {"title": "Learn REST", "priority": "HIGH"}
 *
 * This kind of class is called a "DTO" (data transfer object).
 * It is only for data that goes in or out over the web.
 *
 * The marks (@NotBlank, @Size) are "validation" rules.
 * Spring checks them before your code runs. Bad data gets a 400 error.
 */
public record CreateTaskRequest(
        @NotBlank(message = "title must not be empty")
        @Size(max = 200, message = "title must be 200 characters or less")
        String title,

        Priority priority   // optional: if missing, we use MEDIUM
) {
}
