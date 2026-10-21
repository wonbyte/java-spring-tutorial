# Step 1: Java basics

**Goal:** Learn the Java parts you need for Spring Boot. No Spring yet.

**Code:** `task-tracker/src/main/java/com/wonbyte/tasktracker/basics/`

## Run it

```bash
cd task-tracker
mvn -q compile
java -cp target/classes com.wonbyte.tasktracker.basics.BasicsDemo
```

- `mvn compile` turns `.java` files (text you write) into `.class` files (code the JVM runs). They go in `target/classes`.
- `java -cp target/classes ...` runs the program. `-cp` means "class path": where Java looks for classes.

## What is in each file

| File | What it teaches |
|------|-----------------|
| `Priority.java` | **enum**: a type with a fixed set of values. |
| `Task.java` | **record**: a short class that only holds data. It cannot change after you make it. |
| `TaskList.java` | **class**: private data plus public methods. Also `List`, loops, `Optional` and streams. |
| `BasicsDemo.java` | **main method**: where a program starts. Also variables, `if`, `switch` and `try/catch`. |

## The 8 ideas to remember

1. **Every Java file holds one public class.** The file name must match the class name.
2. **`package`** is the folder path, with dots. It groups classes.
3. **Types.** `int` (whole number), `double` (decimal), `boolean` (true/false), `String` (text). `var` lets Java guess.
4. **Class vs object.** A class is the plan. An object is one thing made from the plan with `new`.
5. **`private` fields, `public` methods.** The class guards its own data. This is "encapsulation".
6. **Records are immutable.** To change a task, make a new one. See `markDone()`.
7. **`Optional`** means "maybe a value, maybe not". Use it in place of `null`.
8. **Streams and lambdas.** `list.stream().filter(t -> !t.done()).toList()` reads as "keep the tasks that are not done".

## Try this (5 minutes)

1. Add a new value `URGENT` to `Priority`. Compile. The `switch` in `BasicsDemo` now fails. Why? Fix it.
2. Add a method `doneTasks()` to `TaskList`. Copy `openTasks()` and change one character.
3. In `BasicsDemo`, try `list.add(null, Priority.LOW);`. What happens?

Answers: the `switch` must cover every enum value. For `doneTasks()`, remove the `!`. A `null` title throws an error from the `Task` record.
