package tn.legacy.monivulationws.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping(value = "/hello")
    public String sayHi(){
        return "Hi";
    }
}
