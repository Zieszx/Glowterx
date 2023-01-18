package com.glowterx.glowterx.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String firstpage() {
        return "fitnesslogin";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "fitnesslogin";
    }

    @GetMapping("/register")
    public String register() {
        return "Register";
    }
}
