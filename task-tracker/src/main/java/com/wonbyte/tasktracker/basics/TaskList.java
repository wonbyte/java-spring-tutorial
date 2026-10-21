package com.wonbyte.tasktracker.basics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * A normal class. It has private fields (data) and public methods (actions).
 * "private" means only code inside this class can touch the field.
 * This is called "encapsulation": the class protects its own data.
 */
public class TaskList {

    // A List can grow and shrink. ArrayList is the most common kind.
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    /** Make a new task, store it, and give it back. */
    public Task add(String title, Priority priority) {
        Task task = new Task(nextId, title, priority, false);
        nextId++;
        tasks.add(task);
        return task;
    }

    /**
     * Find a task by id.
     * "Optional" means: maybe there is a task, maybe not.
     * It is safer than returning null.
     */
    public Optional<Task> findById(long id) {
        for (Task task : tasks) {          // a "for-each" loop
            if (task.id() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    /** Mark a task as done. Returns true if we found it. */
    public boolean complete(long id) {
        for (int i = 0; i < tasks.size(); i++) {   // a classic "for" loop with an index
            Task task = tasks.get(i);
            if (task.id() == id) {
                tasks.set(i, task.markDone());     // swap the old task for the new one
                return true;
            }
        }
        return false;
    }

    /** All tasks that are not done. This uses a "stream". */
    public List<Task> openTasks() {
        return tasks.stream()
                .filter(task -> !task.done())      // "task -> ..." is a lambda: a small function
                .toList();
    }

    /** All tasks, HIGH priority first. */
    public List<Task> byPriority() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::priority).reversed())
                .toList();
    }

    public int size() {
        return tasks.size();
    }
}
