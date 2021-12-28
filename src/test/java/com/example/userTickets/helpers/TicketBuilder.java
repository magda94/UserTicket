package com.example.userTickets.helpers;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.entity.User;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class TicketBuilder {

    private Long id;
    private String name;
    private TicketStatus status;
    private User user;


    public Ticket build() {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setName(name);
        ticket.setStatus(status);
        ticket.setUser(user);
        return ticket;
    }
}
