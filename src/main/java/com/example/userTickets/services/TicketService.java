package com.example.userTickets.services;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.exceptions.TicketNotFoundException;
import com.example.userTickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public List<Ticket> getTickets() {
        return repository.findAll();
    }

    public void addTicket(Ticket ticket) {
        repository.save(ticket);
    }

    public void updateTicket(Long id, Ticket ticket) {
        Ticket foundTicket = findById(id);
        ticket.setId(foundTicket.getId());
        repository.save(ticket);
    }

    public void deleteTicket(Long id) {
        repository.delete(findById(id));
    }

    public List<Ticket> findByStatus(TicketStatus status) {
        return repository.findAllByStatus(status);
    }

    private Ticket findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Cannot find ticket with id: " + id));
    }
}
