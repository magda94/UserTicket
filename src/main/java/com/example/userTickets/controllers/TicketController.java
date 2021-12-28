package com.example.userTickets.controllers;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.exceptions.TicketNotFoundException;
import com.example.userTickets.loggers.ProjectLogger;
import com.example.userTickets.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private ProjectLogger logger = ProjectLogger.getLogger(this.getClass().getName());

    private TicketService service;

    @Autowired
    TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Ticket> getTickets() {
        List<Ticket> result = service.getTickets();
        logger.info("Made request to get tickets");
        return result;
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id) {
        try {
            return service.getTicketById(id);
        } catch (TicketNotFoundException e) {
            logger.info("Cannot find ticket with id: {}", id);
            throw e;
        }
    }

    @PostMapping("")
    public void addTicket(@RequestBody Ticket ticket) {
        service.addTicket(ticket);
        logger.info("Ticket {} was added.", ticket);
    }

    @PutMapping("/{id}")
    public void updateTicket(@PathVariable("id")Long id, @RequestBody Ticket ticket) {
        service.updateTicket(id, ticket);
        logger.info("Ticket {} was updated.", ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        service.deleteTicket(id);
        logger.info("Ticket with id {} was deleted.", id);
    }

    @GetMapping("/search/byStatus")
    public List<Ticket> getTicketWithStatus(@RequestParam("status") String status) {
        List<Ticket> result = service.findByStatus(TicketStatus.getTicketStatusByName(status));
        logger.info("Made request to get all tickets with status: {}.", status);
        return result;
    }
}
