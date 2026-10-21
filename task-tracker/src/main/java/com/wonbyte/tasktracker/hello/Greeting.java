package com.wonbyte.tasktracker.hello;

import java.time.Instant;

/** A record that Spring turns into JSON for us. */
public record Greeting(String message, Instant time) {
}
