package com.wonbyte.tasktracker.task;

import java.util.List;
import java.util.Optional;

/**
 * A "repository" stores and loads data.
 *
 * This is an "interface": a list of methods with no code.
 * It says WHAT we can do, not HOW.
 * Today the "how" is a map in memory (InMemoryTaskRepository).
 * In step 4 we change the "how" to a real database.
 * The service and controller do not need to change much, because they only know this interface.
 */
public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> findById(Long id);

    /** Saves a task. If the task has no id, the repository gives it a new one. */
    Task save(Task task);

    boolean deleteById(Long id);
}
