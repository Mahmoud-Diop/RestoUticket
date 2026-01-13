package sn.ucad.restou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Bienvenue sur RestoU - Tickets !";
    }

    @GetMapping("/api/hello")
    public String helloApi() {
        return "Hello from Spring Boot API !";
    }
}
