package com.example.userTickets.converter;

import com.example.userTickets.entity.TicketStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketStatusAttributeConverter implements AttributeConverter<TicketStatus, String> {
    @Override
    public String convertToDatabaseColumn(TicketStatus attribute) {
        if (attribute == null)
            return null;
        return attribute.getStatusName();
    }

    @Override
    public TicketStatus convertToEntityAttribute(String name) {
        return  name == null ? null : TicketStatus.getTicketStatusByName(name);
    }
}
