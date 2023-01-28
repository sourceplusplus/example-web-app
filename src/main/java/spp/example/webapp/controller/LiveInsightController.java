package spp.example.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveInsightController {

    @RequestMapping(value = "/slow-method", method = RequestMethod.GET)
    public void slowMethod() throws Exception {
        Thread.sleep(500);
    }

    @RequestMapping(value = "/fast-method", method = RequestMethod.GET)
    public void fastMethod() throws Exception {
        Thread.sleep(5);
    }
}
