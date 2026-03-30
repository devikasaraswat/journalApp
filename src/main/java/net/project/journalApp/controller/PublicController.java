package net.project.journalApp.controller;

import net.project.journalApp.entity.User;
import net.project.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User newUser){
        //  userService.saveEntry(newUser);
        userService.saveNewUser(newUser);
        // return true;
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Healthy";
    }
}
