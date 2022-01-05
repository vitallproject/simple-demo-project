package com.vital.mapper;

import com.vital.dto.TicketDto;
import com.vital.entity.Ticket;

public class TicketMapper implements Mapper<Ticket, TicketDto> {

    @Override
    public TicketDto mapFrom(Ticket object) {
        return TicketDto.builder()
                .id(object.getId())
                .flightId(object.getFlight().getId())
                .seatNo(object.getSeatNo())
                .build();
    }
}
