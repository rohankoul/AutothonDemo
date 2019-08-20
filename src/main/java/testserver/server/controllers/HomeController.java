package testserver.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    
    @RequestMapping("/")
    public String postiveResponse(){
        return "<h1>Welcome to Tests</h1>";
    }
}