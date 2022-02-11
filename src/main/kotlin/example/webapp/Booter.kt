package example.webapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object Booter {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(Booter::class.java, *args)
    }
}