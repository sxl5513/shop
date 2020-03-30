package com.anber;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anber
 */
@RestController
public class Hello {

    @GetMapping("/hello")
    public String hello(){
        return "hello world !";
    }
}
