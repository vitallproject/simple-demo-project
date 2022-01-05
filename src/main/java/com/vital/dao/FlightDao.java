package com.vital.dao;

import lombok.SneakyThrows;

import com.vital.entity.FlightStatus;
import com.vital.entity.Flight;
import com.vital.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final String FIND_ALL = """
            SELECT id,
            flight_no,
            departure_date,
            departure_airport_code,
            arrival_date,
            arrival_airport_code,
            aircraft_id,
            status
            FROM flight
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL + " WHERE id = ?";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight ticket) {
        return null;
    }

    @Override
    public void update(Flight ticket) {
    }

    @Override
    @SneakyThrows
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    public Optional<Flight> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if (resultSet.next()) {
                flight = buildFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        }
    }

    @Override
    @SneakyThrows
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
            return flights;
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return Flight.builder()
                .id(resultSet.getObject("id", Long.class))
                .flightNo(resultSet.getObject("flight_no", String.class))
                .departureDate(resultSet.getObject("departure_date", Timestamp.class).toLocalDateTime())
                .departureAirportCode(resultSet.getObject("departure_airport_code", String.class))
                .arrivalDate(resultSet.getObject("arrival_date", Timestamp.class).toLocalDateTime())
                .arrivalAirportCode(resultSet.getObject("arrival_airport_code", String.class))
                .aircraftId(resultSet.getObject("aircraft_id", Integer.class))
                .status(FlightStatus.valueOf(resultSet.getObject("status", String.class)))
                .build();
    }
}
