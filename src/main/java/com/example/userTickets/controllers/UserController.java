package com.example.userTickets.controllers;

import com.example.userTickets.entity.User;
import com.example.userTickets.exceptions.UserNotFoundException;
import com.example.userTickets.loggers.ProjectLogger;
import com.example.userTickets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private ProjectLogger logger = ProjectLogger.getLogger(this.getClass().getName());

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("")
    public List<User> getUsers() {
        List<User> result = service.getUsers();
        logger.info("Made request to get users");
        return result;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        try {
            return service.getUserById(id);
        }catch (UserNotFoundException e) {
            logger.info("Cannot find user with id {}", id);
            throw e;
        }
    }

    @PostMapping("")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
        logger.info("User {} was added.", user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id")Long id, @RequestBody User user) {
        service.updateUser(id, user);
        logger.info("User {} was updated", user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        logger.info("User with id {} was deleted.", id);
    }
}
