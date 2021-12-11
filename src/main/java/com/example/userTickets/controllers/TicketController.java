package com.example.userTickets.controllers;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.repository.TicketRepository;
import com.example.userTickets.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService service;

    @Autowired
    TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Ticket> getTickets() {
        return service.getTickets();
    }

    @PostMapping("")
    public void addTicket(@RequestBody Ticket ticket) {
        service.addTicket(ticket);
    }

    @PutMapping("/{id}")
    public void updateTicket(@PathVariable("id")Long id, @RequestBody Ticket ticket) {
        service.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        service.deleteTicket(id);
    }
}
