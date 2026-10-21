package com.wonbyte.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 2: the start point of the Spring Boot app.
 *
 * @SpringBootApplication does 3 jobs:
 *  1. It marks this class as the main config.
 *  2. It turns on "auto-configuration": Spring sees the web library and starts a web server for you.
 *  3. It turns on "component scan": Spring finds classes like @RestController in this package
 *     and in all packages below it (for example com.wonbyte.tasktracker.hello).
 */
@SpringBootApplication
public class TaskTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }
}
