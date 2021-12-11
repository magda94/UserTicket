package com.example.userTickets.services;

import com.example.userTickets.entity.User;
import com.example.userTickets.exceptions.UserNotFoundException;
import com.example.userTickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public void addUser(User user) {
        repository.save(user);
    }

    public void updateUser(Long id, User user) {
        User foundUser = findById(id);

        user.setId(foundUser.getId());
        repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.delete(findById(id));
    }

    private User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + id));
    }
}
