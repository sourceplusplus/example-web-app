package spp.example.webapp.controller;

import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spp.example.webapp.model.User;
import spp.example.webapp.service.UserService;

@RestController
public class WebappController {

    private static final Logger log = LoggerFactory.getLogger(WebappController.class);

    private final UserService userService;

    public WebappController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> userList() {
        log.info("Getting user list");
        return ResponseEntity.ok(userService.getUsers());
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable long id) {
        log.info("Getting user by id: {}", id);
        return ResponseEntity.of(userService.getUser(id));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Creating user: {} {}", firstName, lastName);
        User newUser = new User();
        newUser.setFirstname(firstName);
        newUser.setLastname(lastName);

        if (userService.getUserByFirstAndLastName(firstName, lastName).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok(userService.createUser(newUser));
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Deleting user: {} {}", firstName, lastName);
        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);

        if (userService.getUserByFirstAndLastName(firstName, lastName).isPresent()) {
            userService.deleteUser(user);
        } else {
            log.warn("Missing user: {} {}", user.getFirstname(), user.getLastname());
        }
    }

    @RequestMapping(value = "/throws-exception", method = RequestMethod.GET)
    public void throwsException() {
        log.error("Throwing exception");
        try {
            caughtException();
        } catch (Exception ex) {
            log.error("Threw error", ex);
        }

        sometimesUncaughtException();
        uncaughtException();
    }

    @Trace
    private void sometimesUncaughtException() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Something bad happened");
        }
    }

    @Trace
    private void caughtException() {
        "test".substring(10);
    }

    @Trace
    private void uncaughtException() {
        throw new RuntimeException("Something bad happened");
    }
}
