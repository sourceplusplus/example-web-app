package example.webapp.service

import example.webapp.model.User
import java.util.*

interface UserService {
    val users: Iterable<User?>
    fun getUser(userId: Long): Optional<User?>
    fun createUser(user: User): User
    fun deleteUser(user: User)
    fun getUserByFirstAndLastName(firstName: String?, lastName: String?): Optional<User?>?
}