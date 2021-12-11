package com.example.userTickets.controllers;

import com.example.userTickets.entity.User;
import com.example.userTickets.repository.UserRepository;
import com.example.userTickets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping("")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id")Long id, @RequestBody User user) {
        service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
    }
}
