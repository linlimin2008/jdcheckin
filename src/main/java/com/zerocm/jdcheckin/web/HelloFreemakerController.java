package com.zerocm.jdcheckin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class HelloFreemakerController {

    @GetMapping
    public String helloFreemaker(ModelMap modelMap){
        modelMap.addAttribute("hello","freemaker");
        return "index";
    }
}
