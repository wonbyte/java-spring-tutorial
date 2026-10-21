package com.wonbyte.tasktracker.hello;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A "controller" answers web requests.
 *
 * @RestController tells Spring two things:
 *  1. Make one object of this class and keep it (this object is called a "bean").
 *  2. What the methods return goes straight back to the caller as the response body.
 */
@RestController
public class HelloController {

    /**
     * GET http://localhost:8080/hello
     * GET http://localhost:8080/hello?name=Ada
     *
     * @RequestParam reads "?name=..." from the web address.
     * If there is no name, Spring uses the default value.
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    /**
     * GET http://localhost:8080/hello/json?name=Ada
     *
     * We return a Java object. Spring (with a library called Jackson) turns it into JSON.
     */
    @GetMapping("/hello/json")
    public Greeting helloJson(@RequestParam(defaultValue = "World") String name) {
        return new Greeting("Hello, " + name + "!", Instant.now());
    }
}
