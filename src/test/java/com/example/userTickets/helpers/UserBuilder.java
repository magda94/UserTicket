package com.example.userTickets.helpers;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.User;

import java.util.List;

public class UserBuilder {

    private Long id;
    private String name;
    private String lastName;
    private List<Ticket> ticketList;

    public User build() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setTickets(ticketList);
        return user;
    }
}
