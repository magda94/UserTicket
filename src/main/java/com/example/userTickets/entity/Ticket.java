package com.example.userTickets.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="Tickets")
@Getter @Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="ticketName")
    private String name;
}
