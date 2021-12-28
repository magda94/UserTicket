package com.example.userTickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name="Ticket")
@Getter @Setter
@EqualsAndHashCode
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="ticketName")
    private String name;

    @Column
    private TicketStatus status;

    @ManyToOne (cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"tickets"})
    private User user;

    @Override
    public String toString() {
        return String.format("Ticket: %s with id: %d", name, id);
    }
}
