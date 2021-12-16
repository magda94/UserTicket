package com.example.userTickets.services;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.entity.User;
import com.example.userTickets.exceptions.UserNotFoundException;
import com.example.userTickets.repository.TicketRepository;
import com.example.userTickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository repository;
    private TicketRepository ticketrepository;

    @Autowired
    public UserService(UserRepository repository, TicketRepository ticketrepository) {
        this.repository = repository;
        this.ticketrepository = ticketrepository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public void addUser(User user) {
        repository.save(user);
        addTickets(user.getTickets(), user);
    }

    public void updateUser(Long id, User user) {
        User foundUser = findById(id);

        user.setId(foundUser.getId());
        addTickets(user.getTickets(), user);
        deleteTicketsNotIncluded(user);
        repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.delete(findById(id));
    }

    private User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + id));
    }

    private void addTickets(List<Ticket> ticketList, User user) {
        for (Ticket ticket: ticketList) {
            ticket.setUser(user);
            ticket.setStatus(TicketStatus.BUSY);
        }
        ticketrepository.saveAll(ticketList);
    }

    private void deleteTicketsNotIncluded(User user) {
        List<Ticket> foundTickets = ticketrepository.findAllByUserId(user.getId());
//
        List<Long> idsToRemove = findIdsToRemove(foundTickets, user.getTickets());

        for(Ticket ticket: foundTickets) {
            if (idsToRemove.contains(ticket.getId())) {
                ticket.setUser(null);
                ticket.setStatus(TicketStatus.FREE);
                ticketrepository.save(ticket);
            }
        }
    }

    private List<Long> findIdsToRemove(List<Ticket> foundTickets, List<Ticket> userTickets) {
        List<Long> userIds = getTicketsIds(userTickets);
        List<Long> ticketToRemoveIds = getTicketsIds(foundTickets);

        ticketToRemoveIds.removeAll(userIds);
        return ticketToRemoveIds;
    }

    private List<Long> getTicketsIds(List<Ticket> list) {
        return list.stream()
                .map(t -> t.getId())
                .collect(Collectors.toList());
    }

    private List<Ticket> setTicketsToBusyStatus(List<Ticket> tickets) {
        List<Ticket> result = new ArrayList<>();
        for(Ticket ticket: tickets) {
            ticket.setStatus(TicketStatus.BUSY);
            ticketrepository.save(ticket);
            result.add(ticket);
        }

        return result;
    }
}
