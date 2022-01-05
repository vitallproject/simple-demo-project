package com.vital.service;

import com.vital.dao.TicketDao;
import com.vital.dto.TicketDto;
import com.vital.mapper.TicketMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class TicketService {

    private final TicketDao ticketDao;
    private final TicketMapper ticketMapper;

    public List<TicketDto> findAllByFlightId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId).stream()
                .map(ticketMapper::mapFrom)
                .collect(toList());
    }
}