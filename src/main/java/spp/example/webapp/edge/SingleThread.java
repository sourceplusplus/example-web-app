package spp.example.webapp.edge;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SingleThread implements Runnable {

    private final PrintStream nullStream = new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    });

    @PostConstruct
    public void init() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        int i = 1;
        char c = 'h';
        String s = "hi";
        float f = 1.0f;
        long max = Long.MAX_VALUE;
        byte b = -2;
        short sh = Short.MIN_VALUE;
        double d = 00.23d;
        boolean bool = true;
        nullStream.print(i + c + s + f + max + b + sh + d + bool);
    }
}
