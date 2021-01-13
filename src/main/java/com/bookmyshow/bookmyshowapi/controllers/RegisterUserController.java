package com.bookmyshow.bookmyshowapi.controllers;

import com.bookmyshow.bookmyshowapi.exceptions.UserAlreadyPresentException;
import com.bookmyshow.bookmyshowapi.exceptions.UserNotFoundException;
import com.bookmyshow.bookmyshowapi.models.User;
import com.bookmyshow.bookmyshowapi.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/registerUser")
public class RegisterUserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addUser(@RequestParam(name = "mobile") String mobile,
                          @RequestParam(name = "name") String name){
        Optional<User> users = userRepository.findByMobile(mobile);
        log.info("Received Request to add New User with mobile - "+mobile);
        if(users.isPresent()){
            log.info("User already present with mobile - "+mobile);
            throw new UserAlreadyPresentException("User already present with mobile - "+mobile);
        }
        User user = User.builder().
                mobile(mobile).name(name).build();
        userRepository.save(user);
        log.info("User with id-"+user.getId()+" is added");
        return "User with id-"+user.getId()+" is added";

    }

    @GetMapping("/{moblie}")
    public User getUser(@PathVariable(name = "mobile") String mobile){
        Optional<User> users = userRepository.findByMobile(mobile);
        log.info("Request received to get user with mobile - "+mobile);
        if(users.isEmpty()){
            throw new UserNotFoundException("User with mobile - "+mobile+" is not found");
        }
        return users.get();
    }
}
