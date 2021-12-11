package com.example.userTickets.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Tickets")
@Getter @Setter
public class Ticket {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
}
