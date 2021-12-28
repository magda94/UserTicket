package com.example.userTickets.services;

import com.example.userTickets.repository.TicketRepository;
import com.example.userTickets.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private UserService service;


    @Test
    public void getUserByIdDhouldThrowExceptionWhenUserDoesNotExist() {
        
    }
}