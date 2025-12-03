package com.Basic_Authentication.Basic_Authentication1.controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelComeController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam("type") String type, @RequestParam("mode") String mode){
System.out.println(type + " " + mode);
//        return "Spring Security In-memory Authentication Example - Welcome " + userName;
if (mode.equals("checkauth")){
        return "success\ntoken\n12345678qwerty";
} else {
	System.out.println(type + " " + mode);
	return "";

}
    }

}
