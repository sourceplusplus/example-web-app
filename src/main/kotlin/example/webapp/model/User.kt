package example.webapp.model

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User : Serializable {
    @Id
    @GeneratedValue
    var id: Long = -1
    var firstname: String? = null
    var lastname: String? = null
    override fun toString(): String {
        return String.format("[#UserObject] Id: %s", id)
    }
}