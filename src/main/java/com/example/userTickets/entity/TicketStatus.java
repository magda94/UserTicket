package com.example.userTickets.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TicketStatus {
    FREE("free"),
    BUSY("busy"),
    UNKNOWN("unknown");

    private String name;

    private static Map<String, TicketStatus> nameToTicketMap =
            Arrays.stream(values()).collect(Collectors.toMap(TicketStatus::getStatusName, e->e));

    TicketStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getStatusName() {
        return name;
    }

    public static TicketStatus getTicketStatusByName(String name) {
        return nameToTicketMap.getOrDefault(name.toLowerCase(), UNKNOWN);
    }

    @JsonCreator
    public static TicketStatus fromString(String name) {
        return nameToTicketMap.getOrDefault(name.toLowerCase(), UNKNOWN);
    }
}
