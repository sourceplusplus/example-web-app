package example.webapp.repository

import example.webapp.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserStorage : CrudRepository<User?, Long?> {
    fun findByFirstname(firstName: String?): List<User?>?
    fun findByLastname(firstName: String?): List<User?>?
    fun findByFirstnameAndLastname(firstName: String?, lastName: String?): Optional<User?>?
}