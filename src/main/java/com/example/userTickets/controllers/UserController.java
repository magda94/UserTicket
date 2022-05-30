package com.example.userTickets.controllers;

import com.example.userTickets.entity.User;
import com.example.userTickets.exceptions.UserNotFoundException;
import com.example.userTickets.kafka.MessageProducer;
import com.example.userTickets.kafka.message.UserMessage;
import com.example.userTickets.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final MessageProducer messageProducer;

    @GetMapping("")
    public List<User> getUsers() {
        List<User> result = service.getUsers();
        log.info("Made request to get users");
        return result;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        try {
            return service.getUserById(id);
        }catch (UserNotFoundException e) {
            log.info("Cannot find user with id {}", id);
            throw e;
        }
    }

    @PostMapping("")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
        log.info("User {} was added.", user);
        messageProducer.sendMessage(new UserMessage(user, UserMessage.State.CREATED));
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id")Long id, @RequestBody User user) {
        service.updateUser(id, user);
        log.info("User {} was updated", user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        log.info("User with id {} was deleted.", id);
    }
}
