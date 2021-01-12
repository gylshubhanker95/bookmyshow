package com.bookmyshow.bookmyshowapi.controllers;

import com.bookmyshow.bookmyshowapi.models.User;
import com.bookmyshow.bookmyshowapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/registerUser")
public class RegisterUserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addUser(@RequestParam(name = "mobile") String mobile,
                          @RequestParam(name = "name") String name){
        Optional<User> users = userRepository.findByMobile(mobile);
        if(users.isPresent()){
            return "User already created";
        }
        User user = User.builder().
                mobile(mobile).name(name).build();
        userRepository.save(user);

        return "User with id-"+user.getId()+" is added";

    }

    @GetMapping("/{moblie}")
    public User getUser(@PathVariable(name = "mobile") String mobile){
        Optional<User> users = userRepository.findByMobile(mobile);
        if(users.isEmpty()){
            System.out.println("User not registered");
            return null;
        }
        return users.get();
    }
}
