package spp.example.webapp.edge;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TwoMbObject implements Runnable {

    private final PrintStream nullStream = new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    });

    private final byte[] TWO_MB_ARR = new byte[1024 * 1024 * 2];

    @PostConstruct
    public void init() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        nullStream.println(TWO_MB_ARR);
        nullStream.print("nothing");
    }
}
