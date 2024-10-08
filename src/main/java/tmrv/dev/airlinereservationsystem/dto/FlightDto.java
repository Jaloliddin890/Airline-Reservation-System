package tmrv.dev.airlinereservationsystem.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.Flight}
 */
public record FlightDto(AirportDto departureAirport, AirportDto arrivalAirport, LocalDateTime departureTime,
                        LocalDateTime arrivalTime, double price, int seatsAvailable) implements Serializable {
}