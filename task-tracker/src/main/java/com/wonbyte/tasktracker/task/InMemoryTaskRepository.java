package com.wonbyte.tasktracker.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

/**
 * Stores tasks in memory. When the app stops, all tasks are lost. Step 4 fixes that.
 *
 * @Repository makes this class a Spring bean, like @RestController did in step 2.
 *
 * Why ConcurrentHashMap and AtomicLong?
 * A web server answers many requests at the same time, on many "threads".
 * A normal HashMap can break when two threads change it at once. These two classes are "thread-safe".
 */
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<Task> findAll() {
        List<Task> all = new ArrayList<>(tasks.values());
        all.sort(Comparator.comparing(Task::id));
        return all;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(Task task) {
        Task toSave = task.id() == null ? task.withId(nextId.getAndIncrement()) : task;
        tasks.put(toSave.id(), toSave);
        return toSave;
    }

    @Override
    public boolean deleteById(Long id) {
        return tasks.remove(id) != null;
    }
}
