package com.www.serverWWW;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Controller {
    @GetMapping("/home")
    public static void co(){
        System.out.println("co");
    }
    @GetMapping("/loginFailure")
    public String loginFailure() {
        System.out.println("error");
        return "error.html";
    }
}
