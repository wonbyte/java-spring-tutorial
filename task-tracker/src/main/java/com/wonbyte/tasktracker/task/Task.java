package com.wonbyte.tasktracker.task;

import java.time.Instant;

/**
 * One task. This is the "model": the data the app works with.
 *
 * It is a record, so it cannot change. To change a task, we make a new one.
 * In step 4 this becomes a database "entity".
 */
public record Task(Long id, String title, Priority priority, boolean done, Instant createdAt) {

    /** Returns a copy of this task with a new id. */
    public Task withId(Long newId) {
        return new Task(newId, title, priority, done, createdAt);
    }
}
