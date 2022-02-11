package example.webapp.controller

import example.webapp.controller.WebappController
import example.webapp.model.User
import example.webapp.service.UserService
import org.apache.skywalking.apm.toolkit.trace.Trace
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class WebappController(private val userService: UserService) {
    @RequestMapping(value = ["/users"], method = [RequestMethod.GET])
    fun userList(): ResponseEntity<Iterable<User?>?> {
        log.info("Getting user list")
        return ResponseEntity.ok(userService.users)
    }

    @RequestMapping(value = ["/users/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable id: Long): ResponseEntity<User?> {
        log.info("Getting user by id: {}", id)
        return ResponseEntity.of(userService.getUser(id))
    }

    @RequestMapping(value = ["/users"], method = [RequestMethod.POST])
    fun createUser(@RequestParam firstName: String?, @RequestParam lastName: String?): ResponseEntity<User?> {
        log.info("Creating user: {} {}", firstName, lastName)
        val newUser = User()
        newUser.firstname = firstName
        newUser.lastname = lastName
        return if (userService.getUserByFirstAndLastName(firstName, lastName)!!.isPresent) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        } else {
            ResponseEntity.ok(userService.createUser(newUser))
        }
    }

    @RequestMapping(value = ["/users"], method = [RequestMethod.DELETE])
    fun deleteUser(@RequestParam firstName: String?, @RequestParam lastName: String?) {
        log.info("Deleting user: {} {}", firstName, lastName)
        val user = User()
        user.firstname = firstName
        user.lastname = lastName
        if (userService.getUserByFirstAndLastName(firstName, lastName)!!.isPresent) {
            userService.deleteUser(user)
        } else {
            log.warn("Missing user: {} {}", user.firstname, user.lastname)
        }
    }

    @RequestMapping(value = ["/throws-exception"], method = [RequestMethod.GET])
    fun throwsException() {
        log.error("Throwing exception")
        try {
            caughtException()
        } catch (ex: Exception) {
            log.error("Threw error", ex)
        }
        sometimesUncaughtException()
        uncaughtException()
    }

    @Trace
    private fun sometimesUncaughtException() {
        if (Math.random() > 0.5) {
            throw RuntimeException("Something bad happened")
        }
    }

    @Trace
    private fun caughtException() {
        "test".substring(10)
    }

    @Trace
    private fun uncaughtException() {
        throw RuntimeException("Something bad happened")
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebappController::class.java)
    }
}