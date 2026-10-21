package com.wonbyte.tasktracker.basics;

/**
 * A "record" is a short way to write a class that only holds data.
 * Java writes the constructor, the getters, equals(), hashCode() and toString() for you.
 *
 * Records are "immutable": you cannot change a field after you make the object.
 * To "change" a task, you make a new Task with the new values.
 */
public record Task(long id, String title, Priority priority, boolean done) {

    /**
     * This is a "compact constructor". It runs every time someone makes a Task.
     * We use it to stop bad data from getting in.
     */
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be empty");
        }
        if (priority == null) {
            priority = Priority.MEDIUM; // a default value
        }
    }

    /** Returns a NEW task that is the same, but done. The old task does not change. */
    public Task markDone() {
        return new Task(id, title, priority, true);
    }
}
