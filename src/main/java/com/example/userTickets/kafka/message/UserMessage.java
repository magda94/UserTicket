package com.example.userTickets.kafka.message;

import com.example.userTickets.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserMessage {

    private final User user;
    private final State state;

    public enum State {
        CREATED,
        UPDATED,
        DELETED;
    }
}
