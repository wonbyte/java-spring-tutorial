# Step 2: First Spring Boot app

**Goal:** Start a web server and answer a web request.

**Code:**
- `task-tracker/pom.xml`
- `task-tracker/src/main/java/com/wonbyte/tasktracker/TaskTrackerApplication.java`
- `task-tracker/src/main/java/com/wonbyte/tasktracker/hello/`

## Run it

Terminal 1: start the app.

```bash
cd task-tracker
mvn spring-boot:run
```

Wait for this line: `Started TaskTrackerApplication`.

Terminal 2: call the app.

```bash
curl localhost:8080/hello
curl "localhost:8080/hello?name=Ada"
curl "localhost:8080/hello/json?name=Ada"
```

You can also open `http://localhost:8080/hello` in your Windows browser. WSL2 shares `localhost` with Windows.

Stop the app with `Ctrl+C` in terminal 1.

## What each part does

| Part | Job |
|------|-----|
| `pom.xml` | The Maven file. It lists the libraries ("dependencies") and the Java version. |
| `spring-boot-starter-parent` | Picks versions for all Spring libraries, so they work together. |
| `spring-boot-starter-webmvc` | Adds a web server (Tomcat) and Spring MVC (the code that sends requests to your methods). |
| `@SpringBootApplication` | Starts Spring. Finds your classes. Sets up the web server for you. |
| `@RestController` | Marks a class that answers web requests. |
| `@GetMapping("/hello")` | Connects the web address `/hello` (HTTP GET) to a method. |
| `@RequestParam` | Reads `?name=...` from the web address. |
| `application.properties` | Settings, for example the port. |

## The big idea: beans and "inversion of control"

You never write `new HelloController()`. Spring does it.

- Spring scans your packages at start.
- It finds classes with marks like `@RestController`.
- It makes one object of each. Spring calls these objects **beans**.
- It keeps them and connects them together.

This is **inversion of control**: Spring controls when objects are made, not you. In step 3 you will see the next part, **dependency injection**: Spring gives one bean to another.

## Why JSON "just works"

`helloJson` returns a `Greeting` record. Spring sees that the caller wants data. A library called Jackson turns the record into JSON. You write no JSON code.

## Try this (5 minutes)

1. Change `server.port` to `9090` in `application.properties`. Restart. Call `localhost:9090/hello`.
2. Add a new method: `@GetMapping("/bye")` that returns `"Bye!"`. Restart and call it.
3. Add a field `int length` to `Greeting`. Fill it with `name.length()`. Check the new JSON.
4. Call `localhost:8080/nothing-here`. Read the error. Spring gives a 404 ("not found") by itself.
