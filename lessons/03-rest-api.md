# Step 3: REST API for tasks

**Goal:** Make, read, change and delete tasks over HTTP. Learn layers and dependency injection.

**Code:** `task-tracker/src/main/java/com/wonbyte/tasktracker/task/`

Note: the `basics` package from step 1 stays as a demo. The real app now uses the `task` package.

## The layers

A request goes down through 3 layers. The answer comes back up.

```
HTTP request
    |
    v
TaskController   talks HTTP: addresses, JSON, status codes
    |
    v
TaskService      business rules: "a new task is not done", "missing task = error"
    |
    v
TaskRepository   stores and loads data (today: a map in memory)
```

Each layer has one job. So you can change one layer and not touch the others. In step 4 we swap the repository for a real database.

## The files

| File | Job |
|------|-----|
| `Task.java` | The model: one task. |
| `Priority.java` | LOW, MEDIUM or HIGH. |
| `CreateTaskRequest.java` | The JSON that comes in for POST. Has validation rules. |
| `UpdateTaskRequest.java` | The JSON that comes in for PUT. All fields are required. |
| `TaskRepository.java` | An **interface**: what storage can do, with no code. |
| `InMemoryTaskRepository.java` | The code for that interface. Uses a thread-safe map. |
| `TaskService.java` | The business rules. |
| `TaskController.java` | The web addresses. |
| `TaskNotFoundException.java` | Our own error for "no task with this id". |
| `TaskExceptionHandler.java` | Turns errors into clean JSON with the right status code. |

## The API

| Method | Address | Body | Answer |
|--------|---------|------|--------|
| GET | `/api/tasks` | | 200 and a list |
| GET | `/api/tasks?done=false` | | 200 and only open tasks |
| GET | `/api/tasks/{id}` | | 200, or 404 |
| POST | `/api/tasks` | `{"title":"...","priority":"HIGH"}` | 201 and the new task |
| PUT | `/api/tasks/{id}` | `{"title":"...","priority":"LOW","done":true}` | 200, 400 or 404 |
| DELETE | `/api/tasks/{id}` | | 204, or 404 |

## Run it

Terminal 1:

```bash
cd task-tracker
mvn spring-boot:run
```

Terminal 2. Run these one at a time and read each answer:

```bash
# Make two tasks. -i shows the headers. Look for "201" and "Location".
curl -i -X POST localhost:8080/api/tasks -H 'Content-Type: application/json' -d '{"title":"Learn REST","priority":"HIGH"}'
curl -X POST localhost:8080/api/tasks -H 'Content-Type: application/json' -d '{"title":"Drink water"}'

# Read them.
curl localhost:8080/api/tasks
curl localhost:8080/api/tasks/1

# Mark task 1 as done.
curl -X PUT localhost:8080/api/tasks/1 -H 'Content-Type: application/json' -d '{"title":"Learn REST","priority":"HIGH","done":true}'

# Only open tasks.
curl "localhost:8080/api/tasks?done=false"

# Errors.
curl localhost:8080/api/tasks/99
curl -X POST localhost:8080/api/tasks -H 'Content-Type: application/json' -d '{"title":""}'

# Delete. -i shows the 204 status.
curl -i -X DELETE localhost:8080/api/tasks/2
```

Tip: install `jq` to make JSON easy to read: `sudo apt install -y jq`, then add `| jq` after a `curl` command.

## The 6 ideas to remember

1. **Beans.** `@RestController`, `@Service` and `@Repository` all tell Spring "make one object of this class". The different names only tell people (and some tools) what the layer is.
2. **Dependency injection.** `TaskService` asks for a `TaskRepository` in its constructor. Spring finds the bean and gives it. You never write `new`.
3. **Program to an interface.** `TaskService` knows only the `TaskRepository` interface. It does not know about the map. This makes the swap in step 4 easy.
4. **DTOs.** `CreateTaskRequest` is what the client may send. `Task` is what we store. They are different on purpose: the client must not set `id` or `createdAt`.
5. **Validation.** `@Valid` plus `@NotBlank` stops bad data before your code runs.
6. **Status codes matter.** 201 for "made", 204 for "deleted, nothing to send", 400 for "your data is bad", 404 for "not here".

## Try this (10 minutes)

1. Stop the app and start it again. Call `GET /api/tasks`. Where did your tasks go? (Step 4 fixes this.)
2. Add a filter `?priority=HIGH` to `GET /api/tasks`. Change `TaskController.list` and `TaskService.findAll`.
3. Add `PATCH /api/tasks/{id}/done`. It marks one task as done, with no body. Use `@PatchMapping("/{id}/done")`.
4. Remove `@Service` from `TaskService`. Start the app. Read the error. Spring cannot find a bean to inject into `TaskController`. Put `@Service` back.
