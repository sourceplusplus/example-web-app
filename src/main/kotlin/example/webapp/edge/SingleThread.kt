package example.webapp.edge

import org.springframework.stereotype.Component
import java.io.OutputStream
import java.io.PrintStream
import java.util.concurrent.*
import javax.annotation.PostConstruct

@Component
class SingleThread : Runnable {
    private val nullStream = PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    })

    @PostConstruct
    fun init() {
        val executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS)
    }

    override fun run() {
        val i = 1
        val c = 'h'
        val s = "hi"
        val f = 1.0f
        val max = Long.MAX_VALUE
        val b: Byte = -2
        val sh = Short.MIN_VALUE
        val d = 00.23
        val bool = true
        nullStream.print(i + c.code + s + f + max + b + sh + d + bool)
    }
}