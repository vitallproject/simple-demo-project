package com.vital.mapper;

import com.vital.dto.FlightDto;
import com.vital.entity.Flight;

public class FlightMapper implements Mapper<Flight, FlightDto> {

    @Override
    public FlightDto mapFrom(Flight object) {
        return FlightDto.builder()
                .id(object.getId())
                .description(
                        """
                                %s - %s - %s
                                """.formatted(
                                object.getDepartureAirportCode(), object.getArrivalAirportCode(), object.getStatus()))
                .build();
    }
}
