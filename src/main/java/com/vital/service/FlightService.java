package com.vital.service;

import com.vital.dao.FlightDao;
import com.vital.dto.FlightDto;
import com.vital.mapper.FlightMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;
@AllArgsConstructor
public class FlightService {

    private final FlightDao flightDao;
    private final FlightMapper flightMapper;

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream()
                .map(flightMapper::mapFrom)
                .collect(toList());
    }
}