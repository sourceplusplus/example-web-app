package spp.example.webapp.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class LiveInstrumentController {

    @RequestMapping(value = "/primitive-local-vars", method = RequestMethod.GET)
    public ResponseEntity primitiveLocalVariables() {
        int i = 1;
        char c = 'h';
        String s = "hi";
        float f = 1.0f;
        long max = Long.MAX_VALUE;
        byte b = -2;
        short sh = Short.MIN_VALUE;
        double d = 00.23d;
        boolean bool = true;
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/changing-primitive-local-vars", method = RequestMethod.GET)
    public ResponseEntity changingPrimitiveLocalVariables() {
        int i = ThreadLocalRandom.current().nextInt();
        char c = (char) (ThreadLocalRandom.current().nextInt(26) + 'a');
        String s = RandomStringUtils.randomAlphanumeric(15);
        float f = ThreadLocalRandom.current().nextFloat();
        long max = ThreadLocalRandom.current().nextLong();
        byte b = (byte) ThreadLocalRandom.current().nextInt(Byte.MAX_VALUE);
        short sh = (short) ThreadLocalRandom.current().nextInt(Short.MAX_VALUE);
        double d = ThreadLocalRandom.current().nextDouble();
        boolean bool = ThreadLocalRandom.current().nextBoolean();
        return ResponseEntity.ok().build();
    }
}
