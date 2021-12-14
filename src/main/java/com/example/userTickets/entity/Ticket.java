package com.example.userTickets.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="Ticket")
@Getter @Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="ticketName")
    private String name;

    @ManyToOne (cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"tickets"})
    private User user;
}
