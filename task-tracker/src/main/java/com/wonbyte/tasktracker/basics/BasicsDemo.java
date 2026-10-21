package com.wonbyte.tasktracker.basics;

import java.util.List;

/**
 * Step 1: a plain Java program. No Spring yet.
 *
 * Run it from the task-tracker folder:
 *   mvn -q compile
 *   java -cp target/classes com.wonbyte.tasktracker.basics.BasicsDemo
 */
public class BasicsDemo {

    // "main" is where a Java program starts.
    // "static" means it belongs to the class, not to one object.
    public static void main(String[] args) {

        // --- 1. Variables and types ---
        int count = 3;                 // whole number
        double hours = 1.5;            // number with a decimal point
        boolean ready = true;          // true or false
        String name = "Task Tracker";  // text
        var title = "Learn Java";      // "var" lets Java guess the type (String here)

        System.out.println("App: " + name);
        System.out.println("count=" + count + ", hours=" + hours + ", ready=" + ready + ", title=" + title);

        // --- 2. Objects: make a TaskList and add tasks ---
        TaskList list = new TaskList();   // "new" makes an object from a class
        list.add("Install Java", Priority.HIGH);
        list.add("Learn records", Priority.MEDIUM);
        list.add("Learn Spring Boot", Priority.HIGH);
        list.add("Drink water", Priority.LOW);

        System.out.println("\nAll tasks, HIGH first:");
        for (Task task : list.byPriority()) {
            System.out.println("  " + task);
        }

        // --- 3. Change data: mark task 1 as done ---
        list.complete(1);

        // --- 4. Optional: find a task that may or may not exist ---
        list.findById(1).ifPresent(t -> System.out.println("\nFound: " + t.title() + " done=" + t.done()));
        String missing = list.findById(99).map(Task::title).orElse("(no task with id 99)");
        System.out.println("Looking for 99: " + missing);

        // --- 5. Streams: only open tasks ---
        List<Task> open = list.openTasks();
        System.out.println("\nOpen tasks: " + open.size() + " of " + list.size());

        // --- 6. if / else ---
        if (open.isEmpty()) {
            System.out.println("All done. Go save humanity.");
        } else {
            System.out.println("Still work to do.");
        }

        // --- 7. Exceptions: bad data is stopped by the Task record ---
        try {
            list.add("   ", Priority.LOW);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError caught: " + e.getMessage());
        }

        // --- 8. switch: pick an action from the enum value ---
        for (Task task : list.byPriority()) {
            String advice = switch (task.priority()) {
                case HIGH -> "do it now";
                case MEDIUM -> "do it today";
                case LOW -> "do it later";
            };
            System.out.println(task.title() + " -> " + advice);
        }
    }
}
