package com.vital.service;


import com.vital.dao.FlightDao;
import com.vital.dto.FlightDto;
import com.vital.entity.Flight;
import com.vital.entity.FlightStatus;
import com.vital.mapper.FlightMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceUnitTest {

    @Mock
    private FlightDao flightDao;
    @Mock
    private FlightMapper flightMapper;
    @InjectMocks
    private FlightService flightService;

    private static final FlightDto flightDto = FlightDto.builder()
            .id(1L)
            .description("COD - " + "LDN - " + "ARRIVED")
            .build();

    private static final Flight flight = Flight.builder()
            .id(1L)
            .flightNo("FNN101")
            .departureDate(LocalDateTime.of(2020,1,1,1,1))
            .departureAirportCode("COD")
            .arrivalDate(LocalDateTime.of(2020,1,2,1,1))
            .arrivalAirportCode("LDN")
            .aircraftId(1)
            .status(FlightStatus.ARRIVED)
            .build();

    @Test
    void shouldReturnListOfFlightDto() {
        doReturn(List.of(flight)).when(flightDao).findAll();
        doReturn(flightDto).when(flightMapper).mapFrom(flight);

        var all = flightService.findAll();

        assertThat(all).isEqualTo(List.of(flightDto));
    }
}