package com.example.userTickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Getter @Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @OneToMany (mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"user"})
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s with id: %d", name, lastName, id);
    }
}
