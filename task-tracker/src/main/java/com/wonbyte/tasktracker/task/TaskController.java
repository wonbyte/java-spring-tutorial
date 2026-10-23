package com.wonbyte.tasktracker.task;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST API for tasks.
 *
 * REST rules in short:
 *  - A "resource" has an address (URL):   /api/tasks  and  /api/tasks/{id}
 *  - The HTTP method says what to do:
 *      GET    = read
 *      POST   = make a new one
 *      PUT    = replace one
 *      DELETE = remove one
 *  - The status code says what happened:
 *      200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found
 *
 * @RequestMapping on the class puts "/api/tasks" in front of every method below.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {   // dependency injection again
        this.service = service;
    }

    /** GET /api/tasks          -> all tasks
     *  GET /api/tasks?done=false -> only open tasks */
    @GetMapping
    public List<Task> list(@RequestParam(required = false) Boolean done) {
        return service.findAll(done);
    }

    /** GET /api/tasks/1 -> one task, or 404.
     *  @PathVariable reads the {id} part of the address. */
    @GetMapping("/{id}")
    public Task get(@PathVariable Long id) {
        return service.findById(id);
    }

    /** POST /api/tasks with a JSON body -> 201 Created.
     *  @RequestBody turns the JSON into a Java object.
     *  @Valid tells Spring to check the validation rules first.
     *  ResponseEntity lets us pick the status and add headers.
     *  The "Location" header tells the client the address of the new task. */
    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody CreateTaskRequest request) {
        Task created = service.create(request);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + created.id()))
                .body(created);
    }

    /** PUT /api/tasks/1 with a JSON body -> the new task, or 404. */
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        return service.update(id, request);
    }

    /** DELETE /api/tasks/1 -> 204 No Content, or 404. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
