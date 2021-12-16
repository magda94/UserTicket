package com.example.userTickets.repository;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository <Ticket, Long> {
    List<Ticket> findAllByUserId(Long userId);
    List<Ticket> findAllByStatus(TicketStatus status);
}
