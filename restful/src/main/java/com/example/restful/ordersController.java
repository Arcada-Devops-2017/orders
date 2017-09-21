package com.example.restful;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ordersController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/orders")
    public orders orders(@RequestParam(value="name", defaultValue="World") String name) {
        return new orders(counter.incrementAndGet(),
                String.format(template, name));
    }
}
