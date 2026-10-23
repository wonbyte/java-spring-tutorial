package com.wonbyte.tasktracker.task;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * The "service" holds the business rules. Examples:
 *  - a new task is not done
 *  - a missing priority becomes MEDIUM
 *  - a missing task is an error
 *
 * The controller talks HTTP. The repository talks storage. The service sits in the middle.
 * This split is called "layers": controller -> service -> repository.
 */
@Service
public class TaskService {

    private final TaskRepository repository;

    /**
     * "Dependency injection" (DI):
     * We do not write "new InMemoryTaskRepository()".
     * We ask for a TaskRepository in the constructor, and Spring gives us the bean it made.
     * If a class has only one constructor, Spring uses it. No extra mark is needed.
     */
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /** All tasks. If "done" is not null, only tasks with that done value. */
    public List<Task> findAll(Boolean done) {
        List<Task> all = repository.findAll();
        if (done == null) {
            return all;
        }
        return all.stream().filter(task -> task.done() == done).toList();
    }

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(CreateTaskRequest request) {
        Priority priority = request.priority() != null ? request.priority() : Priority.MEDIUM;
        Task task = new Task(null, request.title().strip(), priority, false, Instant.now());
        return repository.save(task);
    }

    public Task update(Long id, UpdateTaskRequest request) {
        Task old = findById(id);  // throws TaskNotFoundException if missing
        Task updated = new Task(old.id(), request.title().strip(), request.priority(), request.done(), old.createdAt());
        return repository.save(updated);
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new TaskNotFoundException(id);
        }
    }
}
