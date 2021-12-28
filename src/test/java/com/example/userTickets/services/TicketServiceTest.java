package com.example.userTickets.services;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.exceptions.TicketNotFoundException;
import com.example.userTickets.helpers.TicketBuilder;
import com.example.userTickets.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService service;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    @Test
    public void addTicketShouldAddTicketWithFreeStatus() {
        Ticket ticket = getTicketWithParameters((long) 1, "ticket", null);

        when(repository.save(isA(Ticket.class))).thenReturn(ticket);

        service.addTicket(ticket);

        verify(repository).save(ticketCaptor.capture());

        assertThat(ticketCaptor.getValue().getStatus())
                .isEqualTo(TicketStatus.FREE);
    }

    @Test
    public void addTicketShouldAddTicketWithBusyStatus() {
        Ticket ticket = getTicketWithParameters((long) 1, "ticket", TicketStatus.BUSY);

        when(repository.save(isA(Ticket.class))).thenReturn(ticket);

        service.addTicket(ticket);

        verify(repository).save(ticketCaptor.capture());

        assertThat(ticketCaptor.getValue().getStatus())
                .isEqualTo(TicketStatus.BUSY);
    }

    @Test
    public void updateTicketShouldThrowExceptionWhenTicketDoesNotExist() {
        Ticket ticket = getTicketWithParameters((long) 1, "ticket", TicketStatus.FREE);

        when(repository.findById((long) 1)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.updateTicket((long) 1, ticket));

        assertThat(thrown)
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage("Cannot find ticket with id: 1");
    }

    @Test
    public void updateTicketShouldChangeTicket() {
        Ticket ticket = getTicketWithParameters(null, "ticket", TicketStatus.FREE);
        Ticket foundTicket = getTicketWithParameters((long) 1, "ticket", TicketStatus.BUSY);

        when(repository.findById((long) 1)).thenReturn(Optional.of(foundTicket));
        when(repository.save(ticket)).thenReturn(ticket);

        service.updateTicket((long) 1, ticket);

        assertThat(ticket.getId()).isEqualTo(1);
    }

    @Test
    public void deleteTicketShouldThrowExceptionWhenTicketDoesNotExist() {
        when(repository.findById((long) 2)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.deleteTicket((long) 2));

        assertThat(thrown)
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage("Cannot find ticket with id: 2");

    }

    private Ticket getTicketWithParameters(Long id, String name, TicketStatus status) {
        return new TicketBuilder()
                .setId(id)
                .setName(name)
                .setStatus(status)
                .build();
    }
}