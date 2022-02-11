package example.webapp.edge

import org.springframework.stereotype.Component
import java.io.OutputStream
import java.io.PrintStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Component
class TwoMbObject : Runnable {
    private val nullStream = PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    })
    private val TWO_MB_ARR = ByteArray(1024 * 1024 * 2)
    @PostConstruct
    fun init() {
        val executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS)
    }

    override fun run() {
        nullStream.println(TWO_MB_ARR)
        nullStream.print("nothing")
    }
}