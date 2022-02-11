package example.webapp.service

import example.webapp.model.User
import example.webapp.repository.UserStorage
import example.webapp.service.UserServiceImpl
import lombok.SneakyThrows
import org.apache.skywalking.apm.toolkit.trace.Trace
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Service
class UserServiceImpl(private val userStorage: UserStorage) : UserService {
    //pretend work
    @get:SneakyThrows
    @get:Trace
    override val users: Iterable<User?>
        get() {
            Thread.sleep(ThreadLocalRandom.current().nextInt(250).toLong()) //pretend work
            log.info("Getting all users")
            return userStorage.findAll()
        }

    @Trace
    @SneakyThrows
    override fun getUser(userId: Long): Optional<User?> {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250).toLong()) //pretend work
        val user = userStorage.findById(userId)
        if (user.isPresent) {
            log.info("Found user: {}", user)
        } else {
            log.warn("Could not find user: {}", userId)
        }
        return user
    }

    @Trace
    @SneakyThrows
    override fun createUser(user: User): User {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250).toLong()) //pretend work
        log.info("Saving user: {} {}", user.firstname, user.lastname)
        val createdUser = userStorage.save(user)
        log.info("Created user: {}", createdUser)
        return createdUser
    }

    @Trace
    @SneakyThrows
    override fun deleteUser(user: User) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250).toLong()) //pretend work
        userStorage.delete(user)
        log.info("Deleted user: {} {}", user.firstname, user.lastname)
    }

    @Trace
    @SneakyThrows
    override fun getUserByFirstAndLastName(firstName: String?, lastName: String?): Optional<User?>? {
        Thread.sleep(ThreadLocalRandom.current().nextInt(250).toLong()) //pretend work
        log.info("Getting user by first and last name")
        val user = userStorage.findByFirstnameAndLastname(firstName, lastName)
        if (user!!.isPresent) {
            log.info("Found user: {}", user)
        } else {
            log.warn("Could not find user: {} {}", firstName, lastName)
        }
        return user
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
}