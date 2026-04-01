package net.project.journalApp.controller;

import net.project.journalApp.api.response.WeatherResponse;
import net.project.journalApp.entity.User;
import net.project.journalApp.repository.UserRepository;
import net.project.journalApp.service.UserService;
import net.project.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<User> getAllUsers() {
       return userService.getAll();
    }

    @PostMapping
    public void addNewUser(@RequestBody User newUser){
        //  userService.saveEntry(newUser);
        userService.saveNewUser(newUser);
       // return true;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =    authentication.getName();
        User userInDb = userService.findByUsername(username);
        if(userInDb != null ) {
            userInDb.setUsername(newUser.getUsername() !=null && newUser.getUsername()!= "" ? newUser.getUsername() : userInDb.getUsername()); ;
            userInDb.setPassword(newUser.getPassword() !=null && newUser.getPassword()!= "" ? newUser.getPassword() : userInDb.getPassword()); ;
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =    authentication.getName();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(null != weather) {
            greeting = " , Weather feels like " + weather.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName() +greeting, HttpStatus.NOT_FOUND);
    }
}
