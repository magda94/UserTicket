package com.example.userTickets.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonConverter {

    public static <T> String toJsonString(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public static <T> String getContentAsString(List<T> content) {
        StringBuilder builder = new StringBuilder("[");
        for(T obj : content) {
            if(builder.length() != 1) {
                builder.append(",");
            }
            builder.append(toJsonString(obj));
        }
        builder.append("]");
        return builder.toString();
    }
}
