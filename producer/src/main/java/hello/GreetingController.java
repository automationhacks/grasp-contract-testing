package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * "@RestController" = @Controller and @ResponseBody rolled together
 * This tells that every method returns a domain object instead of a view
 */
@RestController
public class GreetingController {
    public static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /*
    Insights:

    @RequestMapping ensures HTTP reqs to /greeting are mapped to greeting function
    By default all HTTP methods are mapped, however we can narrow this by passing method in annotation

    @RequestParam binds value of query String param called name into the name parameter of greeting method with
    a default value as well
     */
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        // Spring boot would take care of converting this obj to json using jackson2
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
