package spp.example.webapp.service;

import lombok.SneakyThrows;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spp.example.webapp.model.User;
import spp.example.webapp.repository.UserStorage;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Trace
    @Override
    @SneakyThrows
    public Iterable<User> getUsers() {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250)); //pretend work
        log.info("Getting all users");
        return userStorage.findAll();
    }

    @Trace
    @Override
    @SneakyThrows
    public Optional<User> getUser(long userId) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250)); //pretend work
        Optional<User> user = userStorage.findById(userId);
        if (user.isPresent()) {
            log.info("Found user: {}", user);
        } else {
            log.warn("Could not find user: {}", userId);
        }
        return user;
    }

    @Trace
    @Override
    @SneakyThrows
    public User createUser(User user) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250)); //pretend work
        log.info("Saving user: {} {}", user.getFirstname(), user.getLastname());
        User createdUser = userStorage.save(user);
        log.info("Created user: {}", createdUser);
        return createdUser;
    }

    @Trace
    @Override
    @SneakyThrows
    public void deleteUser(User user) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250)); //pretend work
        userStorage.delete(user);
        log.info("Deleted user: {} {}", user.getFirstname(), user.getLastname());
    }

    @Trace
    @Override
    @SneakyThrows
    public Optional<User> getUserByFirstAndLastName(String firstName, String lastName) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250)); //pretend work
        log.info("Getting user by first and last name");
        Optional<User> user = userStorage.findByFirstnameAndLastname(firstName, lastName);
        if (user.isPresent()) {
            log.info("Found user: {}", user);
        } else {
            log.warn("Could not find user: {} {}", firstName, lastName);
        }
        return user;
    }
}
