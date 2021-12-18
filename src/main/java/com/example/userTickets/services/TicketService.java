package com.example.userTickets.services;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.exceptions.TicketNotFoundException;
import com.example.userTickets.loggers.ProjectLogger;
import com.example.userTickets.repository.TicketRepository;
import com.example.userTickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private ProjectLogger logger = ProjectLogger.getLogger(this.getClass().getName());

    private TicketRepository repository;
    private UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Ticket> getTickets() {
        return repository.findAll();
    }

    public void addTicket(Ticket ticket) {
        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.FREE);
            logger.debug("Set FREE status for ticket");
        }
        repository.save(ticket);
    }

    public void updateTicket(Long id, Ticket ticket) {
        Ticket foundTicket = findById(id);
        logger.debug("Found ticket {} for id {}", ticket, id);
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
