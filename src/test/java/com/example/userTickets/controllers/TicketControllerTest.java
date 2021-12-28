package com.example.userTickets.controllers;

import com.example.userTickets.entity.Ticket;
import com.example.userTickets.entity.TicketStatus;
import com.example.userTickets.exceptions.TicketNotFoundException;
import com.example.userTickets.helpers.JsonConverter;
import com.example.userTickets.helpers.TicketBuilder;
import com.example.userTickets.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService service;

    @InjectMocks
    private TicketController controller;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    @BeforeEach
    public void setup() {
       mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getTicketShouldReturnNotFoundCodeWhenTicketWithId1NotExists() throws Exception {
        when(service.getTicketById((long)1)).thenThrow(TicketNotFoundException.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResolvedException()).isInstanceOf(TicketNotFoundException.class);
    }

    @Test
    public void deleteTicketShouldReturnNotFoundCodeWhenTicketWithId1NotExists() throws Exception {
        doThrow(TicketNotFoundException.class).when(service).deleteTicket((long)1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResolvedException()).isInstanceOf(TicketNotFoundException.class);
    }

    @Test
    public void putTicketShouldReturnNotFoundCodeWhenTicketWithId1NotExists() throws Exception {
        doThrow(TicketNotFoundException.class).when(service).updateTicket(eq((long)1), isA(Ticket.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(new Ticket())))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResolvedException()).isInstanceOf(TicketNotFoundException.class);
    }



    @Test
    public void getTicketsShouldReturnEmptyListWhenNoTicketsSaved() throws Exception {
        when(service.getTickets()).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    public void getTicketsShouldReturnContent() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(getTicketWithParameters((long) 1, "Ticket1", TicketStatus.FREE));
        ticketList.add(getTicketWithParameters((long) 2, "Ticket2", TicketStatus.BUSY));
        when(service.getTickets()).thenReturn(ticketList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(JsonConverter.getContentAsString(ticketList));
    }

    @Test
    public void postTicketShouldAddTicketFromRequest() throws Exception {
        doNothing().when(service).addTicket(isA(Ticket.class));

        Ticket ticket = getTicketWithParameters((long) 1, "ticket", TicketStatus.FREE);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(ticket)))
                .andExpect(status().isOk());

        verify(service).addTicket(ticketCaptor.capture());

        assertThat(ticketCaptor.getValue())
                .isEqualTo(ticket);
    }

    @Test
    public void putTicketShouldUpdateTicketWithId() throws Exception {
        doNothing().when(service).updateTicket(eq((long) 1), isA(Ticket.class));

        Ticket ticket = getTicketWithParameters((long) 1, "ticket", TicketStatus.BUSY);

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.toJsonString(ticket)))
                .andExpect(status().isOk());

        verify(service).updateTicket(eq((long)1), ticketCaptor.capture());

        assertThat(ticketCaptor.getValue())
                .isEqualTo(ticket);
    }

    @Test
    public void deleteTicketShouldDeleteTicket() throws Exception {
        doNothing().when(service).deleteTicket((long) 1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Ticket getTicketWithParameters(Long id, String name, TicketStatus status) {
        return new TicketBuilder()
                .setId(id)
                .setName(name)
                .setStatus(status)
                .build();
    }

}
