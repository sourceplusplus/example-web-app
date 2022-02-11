package example.webapp.controller

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom

@RestController
class LiveInstrumentController {
    @RequestMapping(value = ["/primitive-local-vars"], method = [RequestMethod.GET])
    fun primitiveLocalVariables(): ResponseEntity<*> {
        val i = 1
        val c = 'h'
        val s = "hi"
        val f = 1.0f
        val max = Long.MAX_VALUE
        val b: Byte = -2
        val sh = Short.MIN_VALUE
        val d = 00.23
        val bool = true
        return ResponseEntity.ok().build<Any>()
    }

    @RequestMapping(value = ["/changing-primitive-local-vars"], method = [RequestMethod.GET])
    fun changingPrimitiveLocalVariables(): ResponseEntity<*> {
        val i = ThreadLocalRandom.current().nextInt()
        val c = (ThreadLocalRandom.current().nextInt(26) + 'a'.code).toChar()
        val s = RandomStringUtils.randomAlphanumeric(15)
        val f = ThreadLocalRandom.current().nextFloat()
        val max = ThreadLocalRandom.current().nextLong()
        val b = ThreadLocalRandom.current().nextInt(Byte.MAX_VALUE.toInt()).toByte()
        val sh = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt()).toShort()
        val d = ThreadLocalRandom.current().nextDouble()
        val bool = ThreadLocalRandom.current().nextBoolean()
        return ResponseEntity.ok().build<Any>()
    }
}